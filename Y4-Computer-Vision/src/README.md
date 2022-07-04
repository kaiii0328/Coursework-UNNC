## Structure

- cameraPara/ : Folder that stores stereo camera intrinsic and extrinsic
- cones/ : Folder that stores images and groundtruth from the MiddelBurry dataset
- mypic/ : Folder that stores my captured images via my stereo camera
- reports_fig/ : Folder that stores the output figures of my implemented Block Matching and Semi-Global Matching algorithm
- BM.py: implementations of Block Matching Algorithm
- capture_images.py: program for capturing stereo images
- SGM_my_stereo_images.py: the Semi-global Matching algorithm on my captured stereo images (with rectification and post processing)
- SGM_public_images.py: the Semi-global Matching algorithm on public images form the MiddleBurry dataset
- stereo_calibration.py: the calibration program for my stereo camera