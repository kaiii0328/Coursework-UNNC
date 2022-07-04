import torch
import torchvision
import torchvision.transforms as transforms
from sklearn.decomposition import PCA

transform = transforms.Compose(
    [transforms.ToTensor(),
     transforms.Normalize((0.5, 0.5, 0.5), (0.5, 0.5, 0.5))])

batch_size = 64
trainset = torchvision.datasets.CIFAR10(root='./data', train=True,
                                        download=True, transform=transform)
trainloader = torch.utils.data.DataLoader(trainset, batch_size=batch_size,
                                          shuffle=True, num_workers=0)
testset = torchvision.datasets.CIFAR10(root='./data', train=False,
                                       download=True, transform=transform)
testloader = torch.utils.data.DataLoader(testset, batch_size=batch_size,
                                         shuffle=False, num_workers=0)

# obtain one batch of train data and corresponding labels
traindata = iter(trainloader)
train_images, train_labels = traindata.next()
train_images_flat = train_images.reshape(-1,3072)

# obtain one batch of test data and corresponding labels
testdata = iter(testloader)
test_images, test_labels = testdata.next()
test_images_flat = test_images.reshape(-1,3072)

# init PCA class for dimension reduction
# change the `n_components` parameter for reproduction, `n_components` is in {1, 2, 4, 8, 16}
pca = PCA(n_components=1)  # you can try different value here
x_train_trans = pca.fit_transform(train_images_flat[:64])
x_test_trans = pca.transform(test_images_flat[:64])
print('Information kept: ', sum(pca.explained_variance_ratio_)*100, '%')
print('Noise variance: ', pca.noise_variance_)