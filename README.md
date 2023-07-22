
# Electronic Component Classifier 

## Overview 
This is a demo project to showcase the possibility of identifying electronic components by the use of an android app. For identification, the app uses the **Tensorflow library.** 

## Installation 
The debug apk version of this app can be downloaded and installed from [Android app](https://drive.google.com/file/d/1xZFplGztMXcjZGsMUMBtUNZ1LxHj8AZn/view?usp=sharing)

## Android Part

### User Interface Design 
The User Interface makes up the visible parts of the application software. It is a term used to describe all the points of human-computer interaction and communication in the application, or generally in any machine. Accompanying the User Interface, is the User Experience. Based on the given User Interface, the overall experience the user derives from interacting with the software, including the aesthetic appearance, the response times, content presentation, all fall under the User Experience. Hence, a good UI leads to a good UX.

#### Screens 
In this application, the UI was designed using the Figma design tool. Below are screenshots showing the various screens as embedded in the application.


##### Mobile - Light
![image](screens/Screenshot%20from%202023-07-19%2014-59-38.png)
![image](screens/Screenshot%20from%202023-07-19%2014-57-17.png)
![image](screens/Screenshot%20from%202023-07-19%2014-57-36.png)
![image](screens/Screenshot%20from%202023-07-19%2014-57-50.png)
![image](screens/Screenshot%20from%202023-07-19%2014-58-09.png)
![image](screens/Screenshot%20from%202023-07-19%2014-58-25.png)
![image](screens/Screenshot%20from%202023-07-19%2014-58-37.png)
![image](screens/Screenshot%20from%202023-07-19%2014-59-05.png)

##### Mobile - Dark
![image](screens/Screenshot%20from%202023-07-19%2014-59-38.png)
![image](screens/Screenshot%20from%202023-07-19%2014-59-51.png)
![image](screens/Screenshot%20from%202023-07-19%2015-00-03.png)
![image](screens/Screenshot%20from%202023-07-19%2015-00-03.png)
![image](screens/Screenshot%20from%202023-07-19%2015-00-15.png)
![image](screens/Screenshot%20from%202023-07-19%2015-00-31.png)
![image](screens/Screenshot%20from%202023-07-19%2015-00-40.png)
![image](screens/Screenshot%20from%202023-07-19%2015-00-55.png)
![image](screens/Screenshot%20from%202023-07-19%2015-01-06.png)

##### Medium - Light 
![image](screens/Screenshot%20from%202023-07-19%2015-03-33.png)
![image](screens/Screenshot%20from%202023-07-19%2015-03-44.png)
![image](screens/Screenshot%20from%202023-07-19%2015-03-53.png)
![image](screens/Screenshot%20from%202023-07-19%2015-04-02.png)
![image](screens/Screenshot%20from%202023-07-19%2015-04-14.png)
![image](screens/Screenshot%20from%202023-07-19%2015-04-23.png)
![image](screens/Screenshot%20from%202023-07-19%2015-04-34.png)

##### Medium - Dark 
![image](screens/Screenshot%20from%202023-07-19%2015-04-57.png)
![image](screens/Screenshot%20from%202023-07-19%2015-05-08.png)
![image](screens/Screenshot%20from%202023-07-19%2015-05-21.png)
![image](screens/Screenshot%20from%202023-07-19%2015-05-32.png)
![image](screens/Screenshot%20from%202023-07-19%2015-05-41.png)
![image](screens/Screenshot%20from%202023-07-19%2015-05-56.png)
![image](screens/Screenshot%20from%202023-07-19%2015-06-08.png)


##### Expanded - Light 
![image](screens/Screenshot%20from%202023-07-19%2015-06-31.png)
![image](screens/Screenshot%20from%202023-07-19%2015-06-44.png)
![image](screens/Screenshot%20from%202023-07-19%2015-06-50.png)
![image](screens/Screenshot%20from%202023-07-19%2015-06-56.png)
![image](screens/Screenshot%20from%202023-07-19%2015-07-20.png)
![image](screens/Screenshot%20from%202023-07-19%2015-07-33.png)
![image](screens/Screenshot%20from%202023-07-19%2015-07-46.png)


##### Expanded - Dark 
![image](screens/Screenshot%20from%202023-07-19%2015-08-09.png)
![image](screens/Screenshot%20from%202023-07-19%2015-08-18.png)
![image](screens/Screenshot%20from%202023-07-19%2015-08-24.png)
![image](screens/Screenshot%20from%202023-07-19%2015-08-29.png)
![image](screens/Screenshot%20from%202023-07-19%2015-08-39.png)
![image](screens/Screenshot%20from%202023-07-19%2015-08-50.png)
![image](screens/Screenshot%20from%202023-07-19%2015-09-01.png)




The application UI was designed with a mobile-first design approach. The screens have also been optimized to run smoothly on medium and larger screens,  such as tablets, folds, and desktops. The screens are also optimized for dark mode as shown above. 


### Application Software Architecture 
This application consists of a single activity, the MainActivity. An activity is one of the android app components, the rest of which are services, content providers, and broadcast receivers. The activity is declared in the app manifest. The app manifest is an xml script that the android OS uses to decide how to integrate this app into the device’s overall user experience. 
Mobile devices are generally resource-constrained, so at any time, the OS might kill certain app processes running in the background  in order to make room available to newly launched apps. These OS memory management events are totally out of the control of the user, thus it is important that every android app be well architected to optimize its productivity and stability.

 #### Architecture of this app 
 This app consists of the UI, domain and the data layers respectively. 
 [Image here!]

 The architecture follows a reactive programming model with a unidirectional data flow. The higher layers react to changes in lower layers. All events flow down, while all data flow up. The data flow is achieved using streams, implemented using Kotlin Flows.


##### a. Data Layer 
The data layer is implemented as an offline-first source of app data and business logic. It is the source of truth for all data in the app.

[Image here!]

The app consists of the `HistoryRepository`, attach to this repository is the `HistoryEntity` model. Based on the repository design pattern, the `HistoryRepository` is the public API for the UI. It provides the only way the UI can access the `HistoryEntity` from the database. It offers several methods for reading and writing data.


###### Reading data 
Data is exposed as data streams. This means that every client of the repository must be prepared to react to data changes. Because there is no guarantee that a particular data would be valid at the time it is used, the data is not exposed as a snapshot. 
Reads are performed from local storage i.e. Room database and the Proto DataStore, therefore errors are not expected when reading from the Repository. 
Example: reading a list of history items from the database 
A list of history items can be obtained by subscribing to `HistoryRepository::showHistory` flow which emits `List<HistoryEntity>`.


###### Writing data 
Suspend functions are provided by the repository to write data. It is up to the caller to ensure that their execution is suitably scoped.
Example: Register an image taken by the camera
Simply call `HistoryRepository::registerHistory` and pass the `historyEntity` which models the component name that the image represents, the image url, and the timestamp. And of course, every `historyEntity` is allocated a unique ID in the database.

###### Data sources 
The `OfflineFirstHistoryRepository` depends on the `HistoryDao` and the `HistoryPagingSource` data sources. 

[table here!]

##### b. Domain Layer 
The domain layer in this app houses all the relevant business logic. It consists of use cases, repository interfaces, and other helper classes. Use cases are classes having a single invocable method `(operator fun invoke)` containing business logic.

In this layer, we have the interface `ComponentClassifier` which describes how the processing of the image caught by the camera would be classified to produce the label (component name) and probability.

We also have the `HistoryRepository` interface which describes how the reading and the writing of the history data would be performed. This domain layer contains three use cases; `CameraPreviewUseCase`, `ImageAnalysisUseCase`, and the `ImageCaptureUseCase`.

The `CameraPreviewUseCase` houses the logic that provides a camera preview stream for displaying on-screen. The preview screen is connected to the `Surface` provided via `Preview.SurfaceProvider`. The application decides how the `Surface` is shown, and is responsible for managing the `Surface` lifecycle after providing it.

To display the preview with the correct orientation, the app needs to take different actions based on the source of the `Surface`. There are several sources from which the app could derive the preview `Surface`, e.g `SurfaceView`, `PreviewView`,  `ImageReader`, `MediaCodec`, `SurfaceTexture`,  `TextureView`, etc. 

In this app, we used the `PreviewView` to provide the camera preview Surface, this is so that the `Surface` produced would always be in the device’s display orientation, saving us the extra boilerplate code to manually configure the Surface to be in sync with the device as it performs rotation. 

The `ImageAnalysisUseCase` houses the logic that provides the CPU with accessible images on which the app will perform the image analysis. It returns an `ImageAnalysis` object which acquires images from the camera via an `ImageReader`. Each image is provided to an `ImageAnalysis.Analyzer` function which has been implemented on the UI layer, from here the app accesses the image data for image analysis via an `ImageProxy`. This proxy has to be closed after usage, lest future images be stalled or dropped depending on the backpressure strategy.

The `ImageCaptureUseCase` houses the logic for taking a picture. It returns an `ImageCapture` object which provides the `takePicture()` function to take a picture to memory or save to a file, while providing image metadata.


##### c. UI Layer 
It comprises UI elements built using Jetpack Compose, coupled with a ViewModel class, `MainViewModel`. 

###### ViewModel
A ViewModel is a business logic or screen level state holder. The primary function of the ViewModel is to expose state to the UI and encapsulate related business logic. It offers the added advantage of caching state and persisting it through configuration changes. The UI layer doesn’t need to fetch data again after a configuration change or when navigating between activities. The ViewModel is the right place to handle business logic that lives in the UI layer. It also is in charge of handling events and delegating them to other layers of the hierarchy when business logic needs to be applied to modify application data.


The `MainViewModel` class in this app receive streams of data from use cases and the repository, and transforms into UI state. The UI composables reflect this state, and provide ways for the user to interact with the app. These interactions are passed as events to the `MainViewModel` where they are processed.


[image here!]

###### UI state 
In this app, the states are modeled using separate transforms of data streams. Each state represents the underlying app data, considering the app data as the source-of-truth. And the UI composables handle all possible states.



#### The Architectural Pattern 
##### Overview 
An architecture represents the organizational structure and behavioural components of a software system. It is the system’s core ideas and characteristics with respect to its relationships, environment, and other design principles. A great and well-structured architecture yields a highly performant, fault-tolerant and scalable system.
This architectural pattern used in this app is the MVVM. Others include MVC, MVP, Viper, etc. MVVM is the short form for Model View ViewModel. In this pattern, views can bind themselves to data streams which are exposed by the viewmodel. The View has a reference to the viewmodel, while the viewmodel has no information about the view.


##### Model 
This holds the data of the application. It cannot directly talk to the View. Generally, it is recommended to expose the data to the ViewModel through Observables and Streams such as LiveData, Flows, etc.


##### View 
This represents the UI of the application devoid of any Application Logic. It observes the ViewModel.


##### ViewModel 
It acts as a link between the Model and the View.  It is responsible for transforming the data from the Model. It provides data streams to the View. It also uses hooks or callbacks to update the View. It requests the data from the Model.
The MVVM pattern is the officially recommended pattern for building android, as described by Google.



### Feature Implementation 
The primary purpose for this app is to analyze and classify electronic components. It contains several other features that work together to deliver a great user experience, while optimizing the device resources. These features are listed below;


#### a. Image Analysis and Classification 
The following are the libraries used in the image analysis; 

```
1. androidx.camera:camera-view:[version]
2. androidx.camera:camera-lifecycle:[version]
3. androidx.camera:camera-extensions:[version]
4. org.tensorflow:tensorflow-lite-support:[version]
5. org.tensorflow:tensorflow-lite-metadata:[version]
6. org.tensorflow:tensorflow-lite:[version]
7. org.tensorflow:tensorflow-lite-gpu:[version]
8. org.tensorflow:tensorflow-lite-gpu-api:[version]

```

Firstly, the `.tflite` model file is loaded into the system using the `loadModelFile(assetManager, filename)` function. This file contains all the resources and training required to classify the different electrical components. For our use case, a `.txt` label file is also loaded with all the desired labels for the electrical components.


Next, the algorithm checks the device whether it is GPU enabled by calling `CompatibilityList::isDelegateSupportedOnThisDevice`. If the device is GPU supported, then a GPU delegate is invoked to carry out the image processing workloads in parallel. 


Using GPUs to run your machine learning models drastically increases the performance of the model, speed of processing, and the user experience in the app. For that, the Tensorflow library has enabled the use of GPUs and other specialized processors through a hardware driver called delegates. But if the device doesn’t support GPU acceleration, then the processing defaults to the CPU cores. 


Next, an `Interpreter` object is created from the `.tflite` model and initialized with the GPU delegate options. A default shape is generated by the `Interpreter` from the default tensor image.


Fourthly, when the camera is exposed to view the component, the `PreviewView` provides the surface for the camera to observe images from the surrounding area. During this observation, the camera with the help of the `ImageAnalysis` feature analyzes the images. The camera actually just provides each image it comes in contact with to the `ImageAnalysis.Analyzer` function through an `ImageReader`. This `ImageAnalysis.Analyzer` function can then access the image data via an `ImageProxy`. 


Next, a bitmap image is generated from the `ImageProxy` object and then passed into the Classifier algorithm under the `classifyAndProduceHighestProbabilityLabel()` function. The bitmap image is immediately converted into a tensor image.  The `TensorImage` can be regarded as an image formatted in such a way that it contains the value of each pixel of the original bitmap image that it encodes.


Sixthly, an `Interpreter` object from the `Tensorflow` library runs some computations on this `TensorImage`, thereby generating a list of probable labels representing the electrical component being identified, with each component having a different level of probability.


Next, a function `getMaxResultFromFloatArray()` sorts all the probabilities in a descending order, with the highest probability coming first, and then returns the indices of these probabilities in another `IntArray`. Using these indices, only the label with the highest probability is returned alongside its corresponding probability value as a `Float`.

Lastly, a `HashMap` holding the label, the Wikipedia link and a snapshot of the details populates the bottom sheet with the highest probability label and information. 


#### b. Take Photo 
In addition to the Camera Preview use case already enabled, this app uses the Image Capture use case. The capture mode is set to maximum photo quality. The flashlight setting is also available to be toggled by the user. 

The `ImageCapture` use case is bound to the `Camera` object. And to  enable us to take a photo, an extension function `ImageCapture.takeSnapshot(Executor)` was created. This wraps around the CameraX API function `takePicture(ImageCapture.OutFileOptions, Executor, ImageCapture.OnImageSavedCallback)`. By invoking this function, the photo is taken and the file is immediately saved by the `onSavePhotoFile(File)` function. The URL of the photo file is persisted using the Room database. 


#### c. Photo History 
Since all the saved photos are persisted using the Room database, the user is able to retrieve the photos at any time and offline. Streams are used to observe the changes in the database and update the UI accordingly. The list of photos is also paged using the Paging3 library in order to mitigate against janks while loading a large dataset. Therefore, thousands of photos can be saved without worries of dragging the UI flow. 


#### d. Toggle Flashlight 
A `StateFlow` of `ImageCaptureFlashMode`  is used to hold the state of the flashlight. The flashlight has three states: ***Auto, Off, and On***. A user can toggle between these states by simply tapping on the flashlight button on the top-right corner on the `MainPreview` screen.

#### e. Toggle Camera 
The camera state is held by the `StateFlow` of `CameraSelector`. The camera has two states: `DEFAULT_BACK_CAMER` and `DEFAULT_FRONT_CAMERA`. The user can toggle the camera state by tapping on the camera button on the bottom-right corner.


#### f. Device Rotation Sensing 
The state of device rotation position is hoisted into the `MainPreviewScreen` using a `MutableState` of `Float`. As the device is rotated, an `OrientationEventListener` observes the angle of rotation and updates the UI state accordingly. The `ImageCapture` and `ImageAnalysis` use cases  also respond to these rotation changes. On the UI, the icons are fed with the `Modifier.rotate(Float)` function so they respond to the device rotation. 


#### g. Bottom Sheet Management
The width and height of the bottom sheet are calculated based on the screen size of the device. We basically have three categories of screen sizes; ***Compact, Medium and Expanded***. For a compact device, the height of the sheet is forty percent of the total height, while for the others, the height corresponds to the overall height of the device. The width of the bottom sheet for a compact device is equal to the overall width of the device, but for others, it is thirty percent. 


Initially, the bottom sheet starts in the minimized state stored in the field `bottomSheetMinimized` as true. Meanwhile the `classificationState` is also set to true. This means that the application is actively taking in readings from the camera and buffering them. After at least three seconds of exposure, the component identified with the highest probability is returned and displayed on the bottom sheet. While the bottom sheet is visible, `bottomSheetMinimized` is set to false and the `classificationState` is also set to false. 


By clicking on the minimize button on the top-right corner of the bottom sheet, the entire process is reversed and repeated. `bottomSheetMinimized` and `classificationState` are both set to false.


#### h. Web Content Preview
On clicking on the “read more” button on the bottom sheet, a web page is shown. The web page shown is a Wikipedia page carrying more information about the identified component. The application knows which url to open for each component as registered in a `HashMap` carrying the linking information. This `HashMap` is stored in the `network` package.


#### i. Dark Theme 
There is a `DarkThemeConfig` enum class that holds the theming states. By default, the theme is set to follow the device theme. To change this setting, the options menu on the `HistoryScreen`  provides the dialog on which the desired setting can be checked. The theming information is cached using the Proto DataStore library. The relevant files are located under the `datastore` package.


### More resources 
For more information about this project checkout [Nkoyo Android app documentation](https://docs.google.com/document/d/16u-AEkb0Qr7EbX4RrrqyInAOh2i23XTeYYJo_XbAqUw/edit?usp=sharing)


## Computer Vision Part
### Overview
The development of a CNN model for the identification of electronic components, using a deep learning framework.
![image](https://github.com/khalidtouch/electronic-component-classifier/assets/63509339/f02cdd5b-63a9-4361-bf90-05efadf5e5ce)
The dataset for this project was gotten by scraping images from Google based on specified search keys using the Google image scraper library. After getting the photos, various preprocessing techniques would be carried out such as conversion to jpeg, data augmentation and resizing to improve the model’s accuracy and robustness. Thereafter, training, validation, and test sets are created from the dataset. Then, using transfer learning, the MobileNetV2 model was used. An Adam optimizer is used to develop the best possible performance of our task. The accuracy of the retrained model is used to assess its performance. A mobile app is then created that can take input images of electronic components and provide the classification results using the trained CNN model. The app will be deployed on the app store or other relevant platforms.

### MODEL DEVELOPMENT 
A popular architecture for computer vision tasks on mobile devices is MobileNet. Developed by Google, MobileNet is specifically designed to be lightweight, efficient, and suitable for resource-constrained environments. This section outlines the various steps involved in developing a computer vision model using MobileNet. 
![image](https://github.com/khalidtouch/electronic-component-classifier/assets/63509339/fa82b9e4-577d-46da-9b6a-a933c57dbd3a)

#### DATA ACQUISITION 
The data acquisition stage was done by getting images from Google, using Google Image Scraper (the Code that was used is in this project folder) which is a Python library that facilitates data acquisition by automating the process of searching and downloading images from Google based on specific search criteria, and images from a Kaggle dataset. A total of 11,016 images were obtained and the classes contained in this dataset include capacitor, cartridge fuse, circuit breaker, filament bulb, LED, resistors, pulse generators, transistors, and Battery.
![image](https://github.com/khalidtouch/electronic-component-classifier/assets/63509339/d504efbc-8898-46dc-89d3-cccc619a2021)

#### DATA PREPROCESSING 
After the data acquisition stage, there were some undesirable image formats (. webp and .gif), this format was converted to the desired format (.jpg, .jpeg) by using shell scripts. To increase the size and diversity of the dataset, the data set was augmented using techniques such as scaling and resizing, flipping, and rotating. Data augmentation introduces variations in the training data, helping the model to learn more robust and invariant features. This preprocessing step helps to prevent model overfitting, hence improving its performance. It was necessary to rename the images with representative names, so a shell script was written to rename them for better image classification labelling and invariable better model performance. The data set was split into training, testing and validation sets. The training set is used to train the models. The validation set is used to adjust the hyperparameters while the testing set is utilized to evaluate the performance of the models.

#### Model SELECTION 
After collecting and preprocessing the data, a model to be used for transfer learning was selected. For this project, a pretrained MobileNetV2 was the base model.
MobileNet is a popular convolutional neural network (CNN) architecture designed for efficient deep learning on mobile devices such as smartphones, tablets, and IoT devices. Conventional convolutional neural networks (CNNs) have high memory and  computational requirements, rendering them unsuitable for execution on mobile and embedded devices. However, MobileNet addresses this limitation by significantly reducing the model's parameter count and computational workload, even though there may be a slight reduction in accuracy. [32].
 It achieves computational efficiency by utilizing depth-wise separable convolutions, which significantly reduce the number of parameters and operations compared to traditional convolutional layers. Depth-wise separable convolution is a technique that decomposes the standard convolution into two distinct operations:
•	Depth-wise Convolution: The depthwise convolution applies a single convolutional filter to each input channel
•	Point-wise Convolution: The depth-wise output is then convolved with a 1x1 kernel to perform dimensionality reduction and combine the feature maps.
![image](https://github.com/khalidtouch/electronic-component-classifier/assets/63509339/29bbb4e4-aa92-44e3-a0a3-16e815af88bc)
![image](https://github.com/khalidtouch/electronic-component-classifier/assets/63509339/32052380-2cc3-4282-8155-61a0dc2a21cb)

#### 	MODEL TRAINING
To perform the transfer learning task, the process starts with importing the necessary libraries, including TensorFlow, and setting up the required parameters and variables. The dataset is stored in the 'Dataset' directory, and the images are preprocessed and split into training, testing and validation sets. The dataset is divided into training and validation sets using a validation split of 0.3.
Next, batches of augmented images and their corresponding labels are generated. The model architecture is defined using the Sequential API from TensorFlow. In this case, a pre-trained MobileNetV2 model is used as the base model, with its fully connected layers removed. Random data augmentation techniques, such as horizontal flipping and random rotation, are applied as preprocessing layers. 
By using the MobileNetV2 model as the base model, the provided code leverages the powerful feature extraction capabilities of the pre-trained model The pre-trained weights of MobileNetV2 are initialized with knowledge learned from a large dataset, such as ImageNet. This initialization helps the model to extract meaningful features from the input images and achieve higher accuracy with less training data. These pre-trained weights, and lower layers are frozen. The fully connected layers of the MobileNetV2 model are removed, and additional layers are added to adapt the model to the specific electronic component classification task. These additional layers include a GlobalAveragePooling2D layer, a Dropout layer, and a Dense layer with softmax activation for the final classification.
The model is compiled with the Adam optimizer and categorical cross-entropy loss. The summary of the model's architecture and the number of trainable variables are printed. Initially, the model is evaluated on the validation set to obtain the baseline performance.
Then, the model is trained for a fixed number of epochs using the fit method. The training is performed using and the validation is performed.The training progress is tracked, and at the end of each epoch, the model's performance metrics, such as loss and accuracy, are calculated and stored.
After the initial training, the model's base layers are unfrozen, and fine-tuning is performed on these layers. The number of trainable variables increases, and the model is recompiled with a lower learning rate. The model is trained for additional epochs, combining the previous training history with the new training data.
Finally, the training and validation accuracies and losses are plotted using Matplotlib to visualize the model's performance over the epochs. This plot helps to analyze whether the model is overfitting or underfitting and to identify the optimal number of epochs.

#### SOFTMAX ACTIVATION FUNCTION
 The softmax activation function is a widely used mathematical function in the field of deep learning, particularly for multi-class classification tasks. It transforms the raw output of a neural network into a probability distribution over multiple classes, enabling the model to make predictions with confidence. Mathematically, the softmax function computes the exponentiated value of each input element divided by the sum of exponentiated values across all elements. The Softmax activation is commonly applied to the output layer of a neural network. The class with the highest probability is often considered the predicted class for a given input.
The softmax equation is as follows:
f( y_(i  )) = 〖e 〗^yi/Ƹ_(k e^yk ) 

Where 〖e 〗^yi = exponential of a particular class
And  Ƹ_(k e^yk ) = sum of exponential of each class
![image](https://github.com/khalidtouch/electronic-component-classifier/assets/63509339/41a89f79-0a4f-494f-acba-fba251df6e3a)
We feed the trained image into the network. The network is trained to predict the input among these 10 classes. The softmax function calculates the probability for each class. It is used as an output layer for each neural network. 
P1 + P2 +P3 +P4 +P5+P6 +P7 +P8 +P9+P10= 1

Ƹ_(k e^yk ) = e0 +e1 +e2 + e3 +e4 + e5 +e6 +e7 +e8 +e9 = 12763.71

For class 1: e^0/12763.71 = 0.0000783
For class 2: e^1/12763.71 = 0.000213
For class 3: e^2/12763.71 = 0.000580
For class 4: e^3/12763.71 = 0.00157
For class 5: e^4/12763.71 = 0.00427
For class 6: e^5/12763.71 = 0.0116
For class 7: e^6/12763.71 = 0.0316
For class 8: e^7/12763.71 = 0.0859
For class 9: e^8/12763.71 = 0.2335
For class 10: e^9/12763.71 = 0.634

From the results below we can see class 10 has the highest probability, so we can see the trained image falls under class 10.
	RECTIFIED LINEAR UNIT 
ReLU activation is a simple and efficient function used in deep learning that helps neural networks learn complex patterns,by introducing non-linearity. It is commonly used in hidden layers of deep neural networks, it "rectifies" negative values by setting them to zero.
In mathematical terms, it can be represented as follows:
If x is less than zero: f(x) equals zero
If x is greater than or equal to zero: f(x) equals x. i.e.,
f(x) = 0 if x < 0 
f(x) = x if x >= 0

#### TESTING PHASE 
In the testing phase, the trained model is used to make predictions on unseen data to evaluate its performance further. The CNN inputs a test set of images (or other forms of data) and generates predictions for each image during testing. The model’s accuracy was then assessed by contrasting the test set’s actual labels with those predicted by the model. The testing dataset should be prepared similarly to the training and validation datasets, with appropriate pre-processing and directory structure.
The model's predictions on the testing dataset can be evaluated using metrics such as accuracy, precision, recall, and F1 score.
![image](https://github.com/khalidtouch/electronic-component-classifier/assets/63509339/154b8b02-4866-409d-9dcc-400bbd4d0d8a)
![image](https://github.com/khalidtouch/electronic-component-classifier/assets/63509339/ef0cafe6-394f-43f5-aa03-b18e6c62761c)
![image](https://github.com/khalidtouch/electronic-component-classifier/assets/63509339/a53566c7-584c-4743-aa6a-c3a1dcc13537)

#### MODEL DEPLOYMENT 
Deployment refers to the process of making a machine-learning model accessible and usable in a production environment. It involves converting the trained model into a format that can be deployed on various platforms. The model was deployed in a mobilenet_v2_fine_tuned2.tflite for Android development integration.
After training and fine-tuning the MobileNetV2 model, we need to save it for future use. To deploy the model on mobile devices, it needs to be converted to the TensorFlow Lite format. The generated TensorFlow Lite model is saved to a file, This file will be used for integration into an Android Studio project. To retrieve the TensorFlow Lite model and labels file, we utilize the files.download function from the google.colab library. The model file (mobilenet_v2_fine_tuned2.tflite) and the corresponding labels file (labels.txt) can be downloaded to the local machine. These files will be used in the Android Studio environment for model integration and class label mapping. In an Android Studio project, the mobilenet_v2_fine_tuned2.tflite file was placed in the "assets" folder. This allows the model to be accessed and loaded by the Android application.
By using a smartphone app that was developed, students can use the model to classify various electronic components. Computer software created specifically for mobile platforms like cellphones, tablet devices, and smartwatches is known as a mobile app. Kotlin is an Android mobile app development technology that will be used to create my model. It will then be released to the app store for user input. 


