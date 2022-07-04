import cv2

cap = cv2.VideoCapture(1)

num = 1

while cap.isOpened():

    # stereo image capture
    succes1, img = cap.read()

    # crop from the middle and then resize
    imgL = img[0:480, :320]
    imgR = img[0:480, 320:]
    imgL = cv2.resize(imgL, (640,480), interpolation = cv2.INTER_AREA)
    imgR = cv2.resize(imgR, (640,480), interpolation = cv2.INTER_AREA)

    k = cv2.waitKey(5)

    if k == 27:
        break
    elif k == ord('s'): # wait for 's' key to save and exit
        if succes1:
            cv2.imwrite('mypic/bottleL/imageL' + str(num) + '.png', imgL)
            cv2.imwrite('mypic/bottleR/imageR' + str(num) + '.png', imgR)
            print("images saved!")
            num += 1

    cv2.imshow('Img 1',img)

# Release and destroy all windows before termination
cap.release()
cv2.destroyAllWindows()