% This function is a 2D anisotropical diffusion filter,which uses a
% similarity function (D-d)/D where D is the max possible distance in the
% kernel and d is the distance between the center pixel with its neighbors
%
% parameters:  img is the input image, hsize is the size of the kernel
% return: the image filtered by anisotropic diffusion filter


function new_image = AniDiffFilter(img,hsize)
[m,n] = size(img);
new_image = zeros(m,n);
D = 0;
% half width of the kernel
w = floor(hsize/2);

for i=1:m
    for j=1:n
        
        % extract local region
        iMin = max(i-w,1);
        iMax = min(i+w,m);
        jMin = max(j-w,1);
        jMax = min(j+w,n);
        neighbor_pixels = img(iMin:iMax,jMin:jMax);
        max_val = max(max(neighbor_pixels));
        min_val = min(min(neighbor_pixels));
        
        % the max distance we can use in the kernel
        D = sqrt((max_val - min_val)^2 * 3) + 1e-10;
               
        % apply similarity function 
        d = sqrt((neighbor_pixels - img(i,j)) .^ 2 *3);
        similarity_matrix = 1- d/D;
        new_image(i,j) = (sum(sum(neighbor_pixels .* similarity_matrix)))/(sum(sum(similarity_matrix))); 
    end
    
end




end

