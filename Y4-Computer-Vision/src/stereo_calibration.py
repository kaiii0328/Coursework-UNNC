import numpy as np
import cv2 as cv
import glob

# declare chessboard size
chessboardSize = (9,6)

def stereo_calibration():
    # termination criteria
    criteria = (cv.TERM_CRITERIA_EPS + cv.TERM_CRITERIA_MAX_ITER, 30, 0.001)

    # assign the object points of chessboard with grid values
    objp = np.zeros((chessboardSize[0] * chessboardSize[1], 3), np.float32)
    objp[:,:2] = np.mgrid[0:chessboardSize[0],0:chessboardSize[1]].T.reshape(-1,2)

    size_of_chessboard_squares_mm = 20
    objp = objp * size_of_chessboard_squares_mm

    # Arrays to store object points and image points from all the images.
    objpoints = [] # 3d point in real world space
    imgpointsL = [] # 2d points in image plane.
    imgpointsR = [] # 2d points in image plane.


    imagesLeft = glob.glob('mypic/chessboardL/*.png')
    imagesRight = glob.glob('mypic/chessboardR/*.png')

    for imgLeft, imgRight in zip(imagesLeft, imagesRight):
        grayL = cv.imread(imgLeft, 0)
        grayR = cv.imread(imgRight,0)

        # find chessboard corners
        retL, cornersL = cv.findChessboardCorners(grayL, chessboardSize, None)
        retR, cornersR = cv.findChessboardCorners(grayR, chessboardSize, None)

        # add object/image points if corners are found
        if retL and retR == True:
            objpoints.append(objp)
            # detect images points bu sub pixels
            cornersL = cv.cornerSubPix(grayL, cornersL, (11,11), (-1,-1), criteria)
            imgpointsL.append(cornersL)
            cornersR = cv.cornerSubPix(grayR, cornersR, (11,11), (-1,-1), criteria)
            imgpointsR.append(cornersR)
    cv.destroyAllWindows()

    weight_height = grayL.shape[::-1]
    # compute the intrinsic of the left camera
    retL, cameraMatrixL, distL, _, _ = cv.calibrateCamera(objpoints, imgpointsL, weight_height, None, None)
    K1, _ = cv.getOptimalNewCameraMatrix(cameraMatrixL, distL, weight_height, 1, weight_height)

    # compute the intrinsic of the right camera
    retR, cameraMatrixR, distR, _, _ = cv.calibrateCamera(objpoints, imgpointsR, weight_height, None, None)
    K2, _ = cv.getOptimalNewCameraMatrix(cameraMatrixR, distR, weight_height, 1, weight_height)

    criteria_stereo= (cv.TERM_CRITERIA_EPS + cv.TERM_CRITERIA_MAX_ITER, 30, 0.001)

    # fix the instrinsic and compute the rotation/translation vectors
    # for the stereo camera
    retStereo, K1, distL, K2, \
    distR, R, T, _, _ = cv.stereoCalibrate(objpoints, imgpointsL, imgpointsR, K1, distL, K2,
                                            distR, weight_height, criteria_stereo, cv.CALIB_FIX_INTRINSIC)
    # save intrinsic and extrinsic
    np.save("camearPara/K1", K1)
    np.save("camearPara/K2", K2)
    np.save("camearPara/distL", distL)
    np.save("camearPara/distR", distR)
    np.save("camearPara/rotation", R)
    np.save("camearPara/translation", T)

if __name__ == '__main__':
    stereo_calibration()



