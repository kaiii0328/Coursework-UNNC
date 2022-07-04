% This function is designed to calculate the saliency values for each
% pixel.It sum the product of probability of each color multiplied by the
% distance betwee the chosen pixel with other pixel with distinctive
% colors.
% 
% parameters: pixel_val is the values for one pixel in lab color space,
% color_vector is a matrix contains all the colors existed in the image,
% num_color_vector is a vector stores the number of the occurrences of each
% color
% return: the saliency value for the pixel

function sum = SumDistance(pixel_val,color_vector,num_color_vector)
sum = 0;
[~, length] = size(color_vector);
for i = 1:length
    % sum the value 
   sum = sum + num_color_vector(i) * distancefunc(pixel_val, color_vector(:,i));
end
end

