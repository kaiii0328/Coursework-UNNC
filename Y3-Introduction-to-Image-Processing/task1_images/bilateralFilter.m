% This funciton is a 2D bilateral fitler for greyscale images.
% 
% parameters:img is the input image, w is the half size of the filter,
% sigma is a vector that contains the spatial sigam and range sigma
% return: image filtered by bilateral filter

function B = bilateralFilter(img,w,sigma)
sigma_d = sigma(1);
sigma_r = sigma(2);

% initialize range weight
[X,Y] = meshgrid(-w:w,-w:w);
rang_weight = exp(-(X.^2+Y.^2)/(2*sigma_d^2));

% Apply bilateral filter.
dim = size(img);
B = zeros(dim);
for i = 1:dim(1)
   for j = 1:dim(2)
      
         % Extract local region.
         iMin = max(i-w,1);
         iMax = min(i+w,dim(1));
         jMin = max(j-w,1);
         jMax = min(j+w,dim(2));
         local_pixles = img(iMin:iMax,jMin:jMax);
      
         % Compute spatial gaussian weights.         
         spatial_weight = exp(-(local_pixles-img(i,j)).^2/(2*sigma_r^2));
      
         % Calculate spatial weight * range weight.
         combined_weight = spatial_weight.* rang_weight((iMin:iMax)-i+w+1,(jMin:jMax)-j+w+1);
         B(i,j) = sum(combined_weight(:).*local_pixles(:))/sum(combined_weight(:));
               
   end
end

end




