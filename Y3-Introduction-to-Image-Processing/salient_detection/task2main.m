% This function is designed for Task2, saliency detection
% The function reads all the images in image folder and
% all the ground truth in the mask folder. It computes the saliency values
% by using a histogram-based contrast method.
% During execution, it will print out the Intersection of Union for each
% image and print the average IoU at the end. Also, it will show the
% figures containing raw image, saliency image and ground trugh
%

rng('default');

clear all;
clc;

% specify file paths
imagesPath = 'C:\Users\79993\Desktop\Y3 Course\大三下\IIP\CW\salient_detection\image\';
groundTruthPath = 'C:\Users\79993\Desktop\Y3 Course\大三下\IIP\CW\salient_detection\mask\';
images = dir(fullfile(imagesPath,'*.jpg'));
groudTruth = dir(fullfile(groundTruthPath,'*.png'));
lengthofFiles = length(images);
totalIoU =0;
for i = 1:lengthofFiles
    % read images and ground truth
    img = imread(strcat(imagesPath,images(i).name));
    mask = imread(strcat(groundTruthPath,groudTruth(i).name));
    %     disp(strcat(imagesPath,images(i).name));
    
    % reduce to 12 colors, converting RGB to indexed images with color map
    % without using dithering.
    [img_indexed,map] = rgb2ind(img,12,'nodither');
    % convert the reduced color into RGB color space
    img_reducted = ind2rgb(img_indexed,map);
    
    % convert into Lab to calculate distance
    img_lab = rgb2lab(img_reducted);
    [m,n,d] = size(img_lab);
    
    img_sal = zeros(m,n);
    % calculate number of colors and the values of each color
    [num_color_vector,color_vector] = find_color_patterns(img_lab);
    
    total_color_num = sum(num_color_vector);
    num_color_vector = num_color_vector / total_color_num;
    
    for j=1:m
        for k=1:n
            % calculate pixel values for saliency images
            img_sal(j,k) = SumDistance(img_lab(j,k,:),color_vector,num_color_vector);
        end
    end
    
    % smooth the saliency image
    img_sal = medfilt2(img_sal);
    
    % normalize using min-max normalization
    img_sal_norm = img_sal - min(img_sal(:));
    img_sal_norm = img_sal_norm / (max(img_sal_norm(:))- min(img_sal_norm(:)));
    
    % using threshold to binarize
    level = graythresh(img_sal_norm);
    img_output = imbinarize(img_sal_norm,0.8*level);
    
    % calculate Intersection of Union
    mask = double(mask)/255;
    mask = imbinarize(mask,0.5);
    intersection = mask .* img_output;
    union = mask + img_output - intersection;
    IoU = sum(sum(intersection)) / sum(sum(union));
    totalIoU = totalIoU + IoU;
    fprintf('Image %d IoU %.2f\n',i,IoU);
    
    % display the raw image, saliency image and the ground truth
    figure;
    subplot(1,3,1);
    imshow(img);
    title('Raw Image');
    subplot(1,3,2);
    imshow(img_output);
    title(' Saliency Image');
    subplot(1,3,3);
    imshow(mask);
    title('Ground Truth');
end

avgIoU = totalIoU / lengthofFiles;
fprintf('average IoU %.2f\n',avgIoU);




