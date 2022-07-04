import os
import torch
import torchvision
from sklearn.decomposition import PCA
import torchvision.transforms as transforms
from sklearn import metrics
from sklearn.model_selection import KFold
from tqdm import tqdm
import warnings
warnings.filterwarnings('ignore')

# Constants
from torch.utils.data import ConcatDataset, SubsetRandomSampler

IMAGE_WIDTH = 32
IMAGE_HEIGHT = 32
COLOR_CHANNELS = 3
EPOCHS = 100
LEARNING_RATES = [ 0.001, 0.005, 0.01, 0.015]
PCA_NUMBERS = [1, 2, 4, 8, 16]
HIDDEN_NODES = [50, 100, 150, 200, 300,400]
CLASSES = ['plane', 'car', 'bird', 'cat', 'deer', 'dog', 'frog', 'horse', 'ship', 'truck']
train_results = {}
val_results = {}
test_results = {}
APPLY_PCA = False
pca_num_comp = 1
logfilePath = ''
model_path = ''

# structure of my MlP model
class Net(torch.nn.Module):
    def __init__(self, n_hidden_nodes):
        super(Net, self).__init__()
        self.n_hidden_nodes = n_hidden_nodes

        # Set up perceptron layers and add dropout
        if APPLY_PCA:
            self.fc1 = torch.nn.Linear(pca_num_comp, n_hidden_nodes)
        else:
            self.fc1 = torch.nn.Linear(IMAGE_WIDTH * IMAGE_WIDTH * COLOR_CHANNELS,
                                       n_hidden_nodes)
        self.fc1_drop = torch.nn.Dropout(0.5)
        self.fc2 = torch.nn.Linear(n_hidden_nodes, n_hidden_nodes)
        self.fc2_drop = torch.nn.Dropout(0.5)

        self.out = torch.nn.Linear(n_hidden_nodes, len(CLASSES))

    def forward(self, x):
        if APPLY_PCA:
            x = x.view(-1, pca_num_comp)
        else:
            x = x.view(-1, IMAGE_WIDTH * IMAGE_WIDTH * COLOR_CHANNELS)
        x = torch.nn.functional.relu(self.fc1(x))
        x = self.fc1_drop(x)
        x = torch.nn.functional.relu(self.fc2(x))
        x = self.fc2_drop(x)
        return torch.nn.functional.log_softmax(self.out(x), dim=1)

# train the MLP model
def train(model, train_loader, optimizer, pca, fold_id, cuda=None):
    model.train()
    for epoch in range(EPOCHS):
        correct = 0
        with tqdm(train_loader, unit="batch") as tepoch:
            for data in tepoch:
                # get the inputs; data is a list of [inputs, labels]
                tepoch.set_description(f"Epoch {epoch}")
                inputs, labels = data
                if APPLY_PCA:
                    inputs_reshaped = inputs.reshape(-1, IMAGE_WIDTH * IMAGE_HEIGHT * COLOR_CHANNELS)
                    inputs_reduced = pca.transform(inputs_reshaped)
                    inputs = inputs_reduced
                if cuda:
                    if APPLY_PCA:
                        inputs, labels = torch.from_numpy(inputs).cuda(), labels.cuda()
                    else:
                        inputs, labels = inputs.cuda(), labels.cuda()

                # zero the parameter gradients
                optimizer.zero_grad()

                # forward + backward + optimize
                outputs = model(inputs.float())
                pred = outputs.data.max(1)[1]  # get the index of the max log-probability
                correct += pred.eq(labels.data).cpu().sum()
                accuracy = 100. * correct / len(train_loader.sampler)
                loss = torch.nn.functional.nll_loss(outputs, labels)
                loss.backward()
                optimizer.step()

                train_results[fold_id] = accuracy

                # print statistics
                tepoch.set_postfix(loss=loss.item(), accuracy=accuracy.item())

    torch.save(model.state_dict(), model_path)

# validate the MLP model
def validate(model, valid_loader, pca, fold_id, cuda=None):
    model.load_state_dict(torch.load(model_path))
    model.eval()
    val_loss, correct, f1_score = 0, 0, 0
    loss_vector, acc_vector = [], []
    with torch.no_grad():
        with tqdm(valid_loader, unit="batch") as tepoch:
            tepoch.set_description(f"Val")
            for inputs, labels in tepoch:
                if APPLY_PCA:
                    # apply PCA
                    inputs_reshaped = inputs.reshape(-1, IMAGE_WIDTH * IMAGE_HEIGHT * COLOR_CHANNELS)
                    inputs_reduced = pca.transform(inputs_reshaped)
                    inputs = inputs_reduced
                if cuda:
                    if APPLY_PCA:
                        inputs, labels = torch.from_numpy(inputs).cuda(), labels.cuda()
                    else:
                        inputs, labels = inputs.cuda(), labels.cuda()
                # predict
                output = model(inputs.float())
                val_loss += torch.nn.functional.nll_loss(output, labels).item()
                _, pred = torch.max(output.data, 1)
                # record f1 scores
                f1_score += metrics.f1_score(labels.cpu(), pred.cpu(), average="macro")
                correct += pred.eq(labels.data).cpu().sum()

            accuracy = 100. * correct / len(valid_loader.sampler)
            val_loss /= len(valid_loader.sampler)
            f1_score /= len(valid_loader.sampler)
            # tepoch.set_postfix(loss=val_loss, accuracy=accuracy, f1_score=f1_score)

    loss_vector.append(val_loss)
    acc_vector.append(accuracy)

    results = {'f1_score': f1_score, 'accuracy': accuracy}
    val_results[fold_id] = results

    # write the validation results to log file
    # create one if it does not exit
    if os.path.exists(logfilePath):
        with open(logfilePath, "a") as file:
            file.write(
                '\nFold {:d} Validation set: Average loss: {:.4f}, Accuracy: {}/{} ({:.0f}%), F1 score {:.4f}\n'.format(
                    fold_id,
                    val_loss, correct, len(valid_loader.sampler), accuracy, f1_score))
    else:
        with open(logfilePath, "w") as file:
            file.write(
                '\nFold {:d} Validation set: Average loss: {:.4f}, Accuracy: {}/{} ({:.0f}%), F1 score {:.4f}\n'.format(
                    fold_id,
                    val_loss, correct, len(valid_loader.sampler), accuracy, f1_score))

# test the MLP model
def test(model, test_loader, pca, fold_id, cuda=None):
    model.load_state_dict(torch.load(model_path))
    model.eval()
    val_loss, correct, f1_score = 0, 0, 0
    loss_vector, acc_vector = [], []
    with torch.no_grad():
        with tqdm(test_loader, unit="batch") as tepoch:
            tepoch.set_description(f"Val")
            for inputs, labels in tepoch:
                if APPLY_PCA:
                    # apply PCA
                    inputs_reshaped = inputs.reshape(-1, IMAGE_WIDTH * IMAGE_HEIGHT * COLOR_CHANNELS)
                    inputs_reduced = pca.transform(inputs_reshaped)
                    inputs = inputs_reduced
                if cuda:
                    if APPLY_PCA:
                        inputs, labels = torch.from_numpy(inputs).cuda(), labels.cuda()
                    else:
                        inputs, labels = inputs.cuda(), labels.cuda()

                # predict
                output = model(inputs.float())
                # record loss
                val_loss += torch.nn.functional.nll_loss(output, labels).item()
                _, pred = torch.max(output.data, 1)
                # record f1 scores
                f1_score += metrics.f1_score(labels.cpu(), pred.cpu(), average="macro")
                correct += pred.eq(labels.data).cpu().sum()
                with open(logfilePath, "a") as file:
                    # write the precision,recall and f1 scores for each class  to the log file
                    file.write(metrics.classification_report(labels.cpu(), pred.cpu(), target_names=CLASSES))

    val_loss /= len(test_loader)
    f1_score /= len(test_loader)
    loss_vector.append(val_loss)

    accuracy = 100. * correct / len(test_loader.dataset)
    acc_vector.append(accuracy)

    results = {'f1_score': f1_score, 'accuracy': accuracy}
    test_results[fold_id] = results

    # write percentage of information loss and test results to the log file
    with open(logfilePath, "a") as file:
        file.write("information kept {:.4f}%\n".format(sum(pca.explained_variance_ratio_) * 100))
        file.write('\nTest set: Average loss: {:.4f}, Accuracy: {}/{} ({:.0f}%), F1 score {:.4f}\n'.format(
            val_loss, correct, len(test_loader.dataset), accuracy, f1_score))


def main():
    cuda = torch.cuda.is_available()
    print('Using PyTorch version:', torch.__version__, 'CUDA:', cuda)

    torch.manual_seed(33)

    batch_size = 64
    k_folds = 10
    # hidden_nodes = 100
    lr = 0.005

    transform = transforms.Compose(
        [transforms.ToTensor(),
         transforms.Normalize((0.5, 0.5, 0.5), (0.5, 0.5, 0.5))])

    trainset = torchvision.datasets.CIFAR10(root='./data', train=True, download=True, transform=transform)
    trainloader = torch.utils.data.DataLoader(trainset, batch_size=batch_size,
                                              shuffle=True, num_workers=0)
    # obtain one batch of input data for PCA fitting
    # unused if PCA is disabled
    traindata = iter(trainloader)
    train_images, train_labels = traindata.next()
    train_images_flat = train_images.reshape(-1, 3072)
    kfold_split = KFold(n_splits=k_folds, shuffle=True)

    # uncomment one line of for loop statement for ablation study
    # make sure set the tow other variables fixed {lr, hidden_nodes, pca_num_comp}
    # for index in range(1, len(LEARNING_RATES) + 1):
    # for index in range(1, len(PCA_NUMBERS)+1):
    for index in range(1,len(HIDDEN_NODES) + 1):
        global pca_num_comp
        # pca_num_comp = PCA_NUMBERS[index-1]
        hidden_nodes = HIDDEN_NODES[index - 1]
        # lr = LEARNING_RATES[index -1]
        pca_num_comp = 10
        pca = PCA(n_components=pca_num_comp)
        example_pca_reduced = pca.fit_transform(train_images_flat)

        # 10-fold cross-validation
        for fold, (train_idx, val_idx) in enumerate(kfold_split.split(trainset)):
            global logfilePath, model_path
            # logfilePath = f'lr{lr}-pca{pca_num_comp}-new.log'

            model_dir = f'./lr{lr}-pca{pca_num_comp}-models'
            logdir = f'lr{lr}-hn{hidden_nodes}logs'
            logfilePath = logdir + f'/hn{hidden_nodes}.log'
            if not os.path.isdir(model_dir):
                os.makedirs(model_dir)
            if not os.path.isdir(logdir):
                os.makedirs(logdir)
            model_path = f'./{model_dir}/model-fold-{fold}.pth'
            # print(f'---------------- fold {fold}, PCA {lr} --------------------')
            # print(f'---------------- fold {fold}, PCA {pca_num_comp} --------------------')
            print(f'---------------- fold {fold}, Hidden Nodes {hidden_nodes} --------------------')
            trainSampler = SubsetRandomSampler(train_idx)
            valSampler = SubsetRandomSampler(val_idx)

            trainloader = torch.utils.data.DataLoader(trainset, batch_size=batch_size, sampler=trainSampler)
            valloader = torch.utils.data.DataLoader(trainset, batch_size=batch_size, sampler=valSampler)

            # init model
            model = Net(hidden_nodes)
            if cuda:
                model.cuda()
            # init optimizer
            optimizer = torch.optim.SGD(model.parameters(), lr=lr)
            train(model, trainloader, optimizer, pca, fold_id=fold, cuda=cuda)
            validate(model, valloader, pca, fold_id=fold, cuda=cuda)

            print(f' ----------------- Fold {fold} End Training and Validation ------------------')

            print(' --------------------  Start Testing --------------------')
            testset = torchvision.datasets.CIFAR10(root='./data', train=False, download=True, transform=transform)
            testloader = torch.utils.data.DataLoader(testset, batch_size=10000)

            test_model = Net(hidden_nodes)
            if cuda:
                test_model.cuda()

            # test the trained model for each folder
            test(test_model, testloader, pca, fold_id=fold, cuda=cuda)

        # calculate the average training accuracy
        avg_train_acc = sum(train_results.values()) / len(train_results)

        # calculate the average val f1 scores and accuracy
        sum_val_acc, sum_val_f1 = 0, 0
        for idx in val_results:
            sum_val_f1 += val_results[idx].get('f1_score')
            sum_val_acc += val_results[idx].get('accuracy')

        avg_val_acc = sum_val_acc / len(val_results)
        avg_val_f1 = sum_val_f1 / len(val_results)

        # calculate the average test f1 scores and accuracy
        sum_test_acc, sum_test_f1 = 0, 0
        for idx in test_results:
            sum_test_f1 += test_results[idx].get('f1_score')
            sum_test_acc += test_results[idx].get('accuracy')
        avg_test_acc = sum_test_acc / len(test_results)
        avg_test_f1 = sum_test_f1 / len(test_results)

        # write the results to the log file
        with open(logfilePath, "a") as file:
            file.write("Avg Train Acc: {:.2f}%, Avg Val Acc:{:.2f}%, Avg Val f1: {:.4f}\n".format(avg_train_acc, avg_val_acc,
                                                                                                avg_val_f1))
            file.write("Avg Test Acc:{:.2f}%, Avg Test f1: {:.4f}\n".format(avg_test_acc, avg_test_f1))
            file.write("information kept {:.4f}%\n".format(sum(pca.explained_variance_ratio_) * 100))


if __name__ == '__main__':
    main()
