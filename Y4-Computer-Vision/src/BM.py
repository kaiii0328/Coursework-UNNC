import numpy as np
import cv2 as cv2
import time
from matplotlib import pyplot as plt
import os

# the Block Matching algorithm for disparity
def BlockMatching(imgL, imgR, block_size, max_disparity):
    height, width = imgR.shape
    disparity_matrix = np.zeros((height, width), dtype=np.int16)
    half_block = block_size // 2

    for j in range(half_block, height - half_block):
        for i in range(half_block, width - half_block):
            # retrieve the left block
            left_block = imgL[j - half_block:j+half_block+1, i - half_block:i + half_block+1]
            min_sad = 32767
            disp = 0

            for d in range(0,  max_disparity):
                if d > i - half_block-1:
                    break
                # retrieve the right block
                right_block = imgR[j - half_block:j+half_block+1, i-half_block-d:i+half_block-d+1]
                # compute the SAD
                sad = np.sum(abs(np.int64(right_block) - np.int64(left_block)))

                if sad < min_sad:
                    min_sad = sad
                    disp = d

            disparity_matrix[j-half_block, i-half_block] = disp

    return disparity_matrix

if __name__ == '__main__':
    # read image pairs
    imgL = cv2.imread("cones/im2.png", 0)
    imgR = cv2.imread("cones/im6.png", 0)

    # BM parameters
    SAD_block_size = 11
    max_disparity = 64
    obj = "cone_BM"
    path = "reports_fig/" + obj + "_results/bm_{}_disp_{}".format(SAD_block_size, max_disparity)
    if not os.path.exists(path):
        os.makedirs(path)
    cv2.imwrite(path + '/imgL.png', imgL)
    cv2.imwrite(path + '/imgR.png', imgR)

    # block matching
    disparityL = BlockMatching(imgL, imgR, SAD_block_size, max_disparity)
    disparityL = np.uint8(255 * disparityL/max_disparity)
    disparityL = cv2.medianBlur(np.uint8(disparityL), 3)

    cv2.imwrite(path+"/left_disparity_map_bm_{}.png".format(SAD_block_size), disparityL)

