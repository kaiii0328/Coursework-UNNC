% This function is designed for task1, apply different filters (Gauassian,
% Median, Bilateral, Anisotropic Diffusion fitler) to three images
rng('default');
clear all;

prefix = 'Image';
suffix = '.bmp';
img_clean = double(imread('lena_gray.jpg'));

for i=1:3
    % read images
    img_path = sprintf('%s%d%s',prefix,i,suffix);
    img = double(imread(img_path));
    
    %% Gaussian filter
    % apply Gaussian Filter
    sigma = 1; % Gaussian sigma
    if (i==1)
        sigma=1.3;
    elseif (i==2)
        sigma = 1.2;
    elseif (i==3)
        sigma =1.5;
    end
    img_Gau = imgaussfilt(img,sigma,'FilterSize',7);
    figure;
    subplot(1,2,1);
    imshow(uint8(img));
    title([img_path, ' Before Gaussian filter']);
    subplot(1,2,2);
    imshow(uint8(img_Gau));
    title([img_path, ' After Gaussian filter']);
    Gua_SNR = snr(double(img_Gau),img_Gau-img_clean);
    fprintf('Image %d: gua snr %.2f\n',i,Gua_SNR);
    
    %% Median filter
    % apply Median Filter
    img_Med = medfilt2(img);
    figure;
    subplot(1,2,1);
    imshow(uint8(img));
    title([img_path, ' Before median filter']);
    subplot(1,2,2);
    imshow(uint8(img_Med));
    title([img_path, ' After median filter']);
    Med_SNR = snr(double(img_Med),img_Med-img_clean);
    fprintf('Image %d: med snr %.2f\n',i,Med_SNR);
    
    %% Bilateral filter
    % apply Bilateral Filter
    % pre-processing
    img_pre = double(img)/255+0.03*randn(size(img));
    img_pre(img_pre<0) = 0; img_pre(img_pre>1) = 1;
    
    w = 5;       % bilateral filter half-width
    sigmas = [2 0.9]; % bilateral filter standard deviations
    if (i==1)
        sigmas=[2,0.6];
    elseif (i==2)
        sigmas=[2,0.4];
    elseif (i==3)
        sigmas=[2,0.9];
    end
    
    img_Bil = bilateralFilter(img_pre,w,sigmas);
    figure;
    subplot(1,2,1);
    imshow(uint8(img));
    title([img_path, ' Before bilateral filter']);
    subplot(1,2,2);
    imshow(img_Bil);
    title([img_path, ' After bilateral filter']);
    Bil_SNR = snr(img_Bil,img_Bil-double(img_clean)/255);
    fprintf('Image %d: bil snr %.2f\n',i,Bil_SNR);
    
    %% Anisotropic Diffusion Filter
    % apply Anisotropic Diffusion Filter
    hsize = 8;
    if (i==2)
        hsize = 4;
    elseif (i==3)
        hsize = 6;
    end
    img_Ani = AniDiffFilter(img,hsize);
    figure;
    subplot(1,2,1);
    imshow(uint8(img));
    title([img_path, ' Before anisotropic filter']);
    subplot(1,2,2);
    imshow(uint8(img_Ani));
    title([img_path, ' After anisotropic filter']);
    img_1_Ani_SNR = snr(img_Ani,img_Ani-img_clean);
    fprintf('Image %d: ani snr %.2f\n',i,img_1_Ani_SNR);
    
    if (i==3)
        % apply Median Filter & Bilateral Filter for Image3
        w = 7;
        sigmas = [2 0.2];
        img_pre = double(img_Med)/255+0.03*randn(size(img_Med));
        img_pre(img_pre<0) = 0; img_pre(img_pre>1) = 1;
        img_combine = bilateralFilter(img_pre,w,sigmas);
        img3_combine_SNR = snr(img_combine,img_combine-double(img_clean)/255);
        figure;
        subplot(1,2,1);
        imshow(uint8(img));
        title([img_path, ' Before combined filter']);
        subplot(1,2,2);
        imshow(img_combine);
        title([img_path, ' After combined filter ']);
        fprintf('Image 3: combine snr %.2f\n',img3_combine_SNR);
    end
end


