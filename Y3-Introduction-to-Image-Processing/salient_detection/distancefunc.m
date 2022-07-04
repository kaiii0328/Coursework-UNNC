% This function is designed for calculating the distance betwee two pixels
% in Lab color space
% parameters: two pixels in lab color space
% return; the distance betwee two pixels

function dist = distancefunc(pixel_1,pixel_2)
dist = 0;
for i=1:3
    dist = dist + (pixel_1(i)-pixel_2(i))^2;
end
dist = sqrt(dist);
end

