import numpy as np
import cv2 as cv2
import time
import os

# census transform on a input window
def CensusTransform(neighbor_pixels, center_value, block_size):
    compare_matrix = np.full((block_size, block_size), center_value, dtype=np.int64)
    # =1 if < center, else =  0
    binarized_values = np.where((neighbor_pixels - compare_matrix) > 0, 0, 1).reshape(-1, 1)
    # remove center pixel
    binarized_values = np.delete(binarized_values, int(block_size * block_size / 2))
    result = np.int64(0)
    for data in binarized_values:
        result = result << 1
        result = result | data

    return result

# compute the census value of the left images
# with regard to the right image
def ComputeCost_Census(imgL, imgR, block_size, max_disparity, path):
    img_height = imgL.shape[0]
    img_width = imgL.shape[1]
    half_block_size = block_size // 2

    censusL = np.zeros(shape=(img_height, img_width), dtype=np.int64)
    censusR = np.zeros(shape=(img_height, img_width), dtype=np.int64)

    start = time.time()
    # apply Census Transform to the Left/Right images
    for y in range(half_block_size, img_height-half_block_size):
        for x in range(half_block_size, img_width-half_block_size):
            center_pixel_value = imgL[y, x]
            neighbor_pixels = imgL[y-half_block_size:y+half_block_size+1,
                                    x-half_block_size:x+half_block_size+1]
            censusL[y,x] = CensusTransform(neighbor_pixels, center_pixel_value, block_size)

            center_pixel_value = imgR[y, x]
            neighbor_pixels = imgR[y - half_block_size:y + half_block_size + 1,
                              x - half_block_size:x + half_block_size + 1]
            censusR[y, x] = CensusTransform(neighbor_pixels, center_pixel_value, block_size)

    end = time.time()
    print('Census transform \t(done in {:.2f}s)'.format(end - start))
    cv2.imwrite(path + '/left_census.png', np.uint8(censusL))
    cv2.imwrite(path + '/right_census.png', np.uint8(censusR))

    costL = np.zeros(shape=(img_height, img_width, max_disparity), dtype=np.int64)
    censusR_shifted = np.zeros(shape=(img_height, img_width), dtype=np.int64)
    # calculate the cost by hamming distance
    for d in range(max_disparity):
        censusR_shifted[:, half_block_size+d:img_width-half_block_size] = censusR[:, half_block_size:img_width-d-half_block_size]
        # calculate hamming distance
        xor_output = np.int64(np.bitwise_xor(censusL, censusR_shifted))
        cost = np.zeros(shape=(img_height, img_width), dtype=np.uint32)
        for y in range(img_height):
            for x in range(img_width):
                cost[y,x] = bin(xor_output[y,x]).count("1")
        costL[:,:,d] = cost
    return costL

# find the minimum cost along one direction
def findMinCost(path, p1, p2):
    pixel_length = path.shape[0]
    disp_dim = path.shape[1]

    # set up the disparity index matrix
    disp_range = np.arange(0, disp_dim, 1, dtype=path.dtype)
    disp_matrix = np.repeat(disp_range, repeats=disp_dim).reshape(disp_dim, disp_dim)

    # set up the penalty matrix
    penalties = np.zeros(shape=(disp_dim, disp_dim), dtype=path.dtype)
    penalties[np.abs(disp_matrix - disp_matrix.T) == 1] = p1
    penalties[np.abs(disp_matrix - disp_matrix.T) > 1] = p2

    min_cost = np.zeros(shape=(pixel_length, disp_dim), dtype=path.dtype)
    min_cost[0, :] = path[0, :]

    # implementation of equation (14) in the original SGM paper
    for p in range(1, pixel_length):
        previous_pixel_cost = min_cost[p-1, :]
        current_pixel_cost = path[p,:]
        pervisou_cost_matrix = np.repeat(previous_pixel_cost, repeats=disp_dim, axis=0).reshape(disp_dim, disp_dim)
        min_prev_cost_withPenalties = np.amin(pervisou_cost_matrix+penalties, axis=0)
        min_cost[p,:] = current_pixel_cost + min_prev_cost_withPenalties - np.amin(previous_pixel_cost)

    return min_cost

# aggregate the costs in all directions
def CostAggregation(costs, p1, p2):
    img_height = costs.shape[0]
    img_width = costs.shape[1]
    disparity = costs.shape[2]

    total_costs = np.zeros(shape=(img_height, img_width, disparity), dtype=costs.dtype)

    start = time.time()
    # aggregate costs in horizontal directions
    for y in range(img_height):
        left2right = costs[y, 0:img_width, :]
        right2left = np.flip(left2right, axis=0)
        total_costs[y,:,:] += findMinCost(left2right, p1, p2)
        total_costs[y,:,:] += findMinCost(right2left, p1, p2)

    # aggregate costs in vertical directions
    for x in range(img_width):
        up2down = costs[0:img_height, x, :]
        down2up = np.flip(up2down, axis=0)
        total_costs[:,x,:] += findMinCost(up2down, p1, p2)
        total_costs[:,x,:] += findMinCost(down2up, p1, p2)

    end = time.time()
    print('Cost Aggregation \t(done in {:.2f}s)'.format(end - start))
    return total_costs

def WTA(costs):
    img_height = costs.shape[0]
    img_width = costs.shape[1]
    disp_dim = costs.shape[2]

    # calculate the disparity
    disp_map = np.argmin(costs, axis=2)

    uniq_ratio = 0.95
    # uniqueness check
    for y in range(img_height):
        for x  in range(img_width):
            min_cost = disp_map[y,x]
            costs[y,x,disp_map[y,x]] = 1000000
            sec_min_cost = np.amin(costs[y,x,:])
            if (sec_min_cost-min_cost) <= min_cost*(1-uniq_ratio):
                costs[y, x, disp_map[y, x]] = min_cost
                disp_map[y, x] = 0
                continue
            costs[y, x, disp_map[y, x]] = min_cost

    # subpixel interpolation
    subpixl_dis_map = np.copy(disp_map).astype("float32")
    for y in range(img_height):
        for x in range(img_width):
            if disp_map[y,x] == disp_dim-1 or disp_map[y,x] == 0:
                continue
            pre_dis_cost = costs[y,x, disp_map[y,x]-1]
            post_dis_cost = costs[y,x, disp_map[y,x]+1]
            cur_cost = costs[y,x, disp_map[y,x]]
            demon = max(1, pre_dis_cost + post_dis_cost - 2*cur_cost)
            subpixl_dis_map[y, x] += (np.int64(pre_dis_cost) - np.int64(post_dis_cost)) / np.int64(demon * 2)
    subpixl_dis_map = 255 * subpixl_dis_map/ disp_dim
    return subpixl_dis_map.astype("int16")
    # return np.uint8(255 * disp_map/disp_dim)

def ComputeCostRight(costL):
    img_heigth = costL.shape[0]
    img_width = costL.shape[1]
    disp_dim =  costL.shape[2]
    costR = np.zeros(shape=(img_heigth, img_width, disp_dim), dtype=costL.dtype)

    # estimate the cost of right image with the costs
    # of the left image
    for d in range(disp_dim):
        costR[:, :img_width-d, d] = costL[:, d:img_width, d]

    disparityR = WTA(costR)
    return disparityR

def LRcheck(dispL, dispR):
    height = dispL.shape[0]
    width = dispL.shape[1]
    threshold = 1

    output_dispairty = np.copy(dispL)
    for y_left in range(1,height):
        for x_left in range(1,width):
            mismatch = False
            occlusion = False
            # the current left disparity and corresponding
            # x offset in the right
            disp_l = dispL[y_left, x_left]
            x_right = x_left-disp_l
            # if it is in the right image
            if 0 <= x_right < width:
                # obtain the estimated right disparity
                disp_r = dispR[y_left, x_right]
                # compare whether the difference is within the threshold
                if np.abs(np.int16(disp_l)-np.int16(disp_r)) > threshold:
                    # obtain the disparity of left pixel
                    # from the right image view
                    x_left_esti = x_right+disp_r
                    if 0 <= x_left_esti < width:
                        # determine whether it is occlusion
                        # or mismatch
                        disp_l_esti = dispL[y_left, x_left_esti]
                        if disp_l_esti > disp_l:
                            occlusion = True
                        else:
                            mismatch = True
                    else:
                        mismatch = True
            else:
                mismatch = True

            neighbors = dispL[y_left - 1:y_left + 1, x_left - 1:x_left + 1].reshape(-1, 1)
            if mismatch:
                # fill the mismatch points with the median value in the neighbors
                filled_value = np.median(neighbors)
                output_dispairty[y_left,x_left] = filled_value
            if occlusion:
                # fill the occlusion points with the second min value in the neighbors
                min_idx = np.argmin(neighbors)
                sec_min = np.amin(np.delete(neighbors, min_idx))
                output_dispairty[y_left, x_left] = sec_min

    return output_dispairty

if __name__ == '__main__':
    # read image pairs
    imgL = cv2.imread("cones/im2.png", 0)
    imgR = cv2.imread("cones/im6.png", 0)

    # SGM parameters
    p1 = 10
    p2 = 120
    census_block_size = 7
    max_disparity = 64
    obj = "cones_SGM"
    path = "reports_fig/" + obj + "_results/p1_{}_p2_{}_disp_{}".format(p1, p2, max_disparity)
    if not os.path.exists(path):
        os.makedirs(path)
    cv2.imwrite(path + '/imgL.png', imgL)
    cv2.imwrite(path + '/imgR.png', imgR)

    # compute cost
    cost_left = ComputeCost_Census(imgL, imgR, census_block_size, max_disparity, path)
    disparityL = np.uint8(255 * cost_left/max_disparity)

    # cost aggregation
    total_costs = CostAggregation(cost_left, p1, p2)

    # WTA + subpixel interpolation
    disparityL = WTA(total_costs)

    # LR-check
    disparityR = ComputeCostRight(cost_left)
    output_disparity = LRcheck(disparityL, disparityR)

    disparityL = cv2.medianBlur(np.uint8(disparityL), 3)
    disparityR = cv2.medianBlur(np.uint8(disparityR), 3)
    output_disparity = cv2.medianBlur(np.uint8(output_disparity), 3)

    cv2.imwrite(path+"/left_disparity_map_{}_{}.png".format(p1, p2), disparityL)
    # cv2.imwrite(path+"/right_disparity_map_{}_{}.png".format(p1,p2), disparityR)
    cv2.imwrite(path+"/disparity_map_{}_{}.png".format(p1,p2), output_disparity)


