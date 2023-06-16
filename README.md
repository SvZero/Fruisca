# Fruisca
Bangkit CAPSTONE project:  Fruisca: App for Checking Fruit Freshness
Capstone Team ID : C23 - PR549

## Description
Model can be use to classify 2 things:
  1. classify type of fruits (currently apple, orange, banana)
  2. classify level of freshsness on fruit

## Method
 ![Architecture of a CNN](https://miro.medium.com/v2/resize:fit:720/format:webp/1*kkyW7BR5FZJq4_oBTx3OPQ.png)
 
our team using simple CNN (Convolutional Neural Networks) because CNN designed for image analysis tasks. They excel at learning and extracting meaningful features from images, making them a natural choice for tasks like fruit classification based on visual appearance, also CNN is good to handle high large scale dataset

## Tools
tools our team using to create Machine Learning Model:
- [Python](https://www.python.org)
- [TensorFlow](https://www.tensorflow.org)
- [Keras](https://keras.io)
- [NumPy](https://numpy.org)
- [Pandas](https://pandas.pydata.org)
- [Matplotlib](https://matplotlib.org)
- [Google Collab](https://colab.research.google.com)
- [Visual Studio Code](https://code.visualstudio.com)
- [FastAPI](https://fastapi.tiangolo.com)
- [Docker](https://www.docker.com)
- [Albumentations](https://albumentations.ai/docs/)

## DATASETS
all dataset we use is collected from [Kaggle](https://www.kaggle.com) and dataset from [Google Dataset search](https://datasetsearch.research.google.com)

and here our final dataset: [Fruisca-Dataset](https://www.kaggle.com/datasets/kuuha2768/fruisca-dataset)

## Model Architecture

Model          : "sequential"

Type Model     : Convolutional Neural Networks

Total params   : 22,178,342

model Accuracy : 17s 124ms/step - loss: 0.0635 - accuracy: 0.9799


## Model Deployment
We use TensorFlow Lite to deploy a trained Machine Learning model on Android

## Reference
| Title                              | Author | link                   | 
| ---------------------------------- |  ---------------------- | ---------------------- |
| Convolutional Neural Network (CNN) |  Tensorflow | [Tensorflow Core](https://www.tensorflow.org/tutorials/images/cnn) |
| Fruits Fresh and Rotten Detection Using CNN & Transfer Learning | G Anitha and P Thiruvannamalai Shivashankar | [easychair](https://easychair.org/publications/preprint_open/wZ2Fv) |
| Convolutional Neural Networks, Explained | Mayank Mishra | [Towards Data Science](https://towardsdatascience.com/convolutional-neural-networks-explained-9cc5188c4939) |


