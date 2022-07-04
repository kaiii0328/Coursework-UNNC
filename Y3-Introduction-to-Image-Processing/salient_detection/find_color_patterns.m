% This function is designed to calculate the number and values of
% distinctive colors occured in the input image, and stores them into two
% seperate vectors. the index of number of occurrence of a coloris is the 
% same as the index of the values of that color in the color_vector index.
% 
% paratmers: imag_lab is the image in Lab color space
% returns: color_vector contains the distince color values in the image, 
% the, num_color_vector contains the number of occurrence for each color.

function [num_color_vector,color_vector] = find_color_patterns(img_lab)
color_vector = zeros(3,1);
num_color_vector = [0];
[m,n,d] = size(img_lab);
for j=1:m
    for k=1:n
        found = false;
        pix_l = img_lab(j,k,1);
        pix_a = img_lab(j,k,2);
        pix_b = img_lab(j,k,3);
        
        % find color index 
        pix_l_index = find(color_vector(1,:)== pix_l);        
        if ((isempty(pix_l_index)~=1)) % if the color has been found
            [~,length] = size(color_vector);
            for counter =1:length
                if (color_vector(2,pix_l_index) == pix_a && color_vector(3,pix_l_index) == pix_b)
                    found = true;
                    break;
                end
            end
        end
        if (found) % add one on the occurrence number
            num_color_vector(pix_l_index) = num_color_vector(pix_l_index) + 1;
        else % add new color values in the color_vector
            new_pixel_color = zeros(3,1);
            new_pixel_color(1,1) = pix_l;
            new_pixel_color(2,1) = pix_a;
            new_pixel_color(3,1) = pix_b;
            
            color_vector = [color_vector, new_pixel_color];
            num_color_vector = [num_color_vector, 1];
        end
    end
end
end

