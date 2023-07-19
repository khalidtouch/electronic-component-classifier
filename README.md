
# Electronic Component Classifier 

## Overview 
This is a demo project to showcase the possibility of identifying electronic components by the use of an android app. For identification, the app uses the **Tensorflow library.** 

## Installation 
The debug apk version of this app can be downloaded and installed from [Android app](https://drive.google.com/file/d/1xZFplGztMXcjZGsMUMBtUNZ1LxHj8AZn/view?usp=sharing)

## User Interface Design 
The User Interface makes up the visible parts of the application software. It is a term used to describe all the points of human-computer interaction and communication in the application, or generally in any machine. Accompanying the User Interface, is the User Experience. Based on the given User Interface, the overall experience the user derives from interacting with the software, including the aesthetic appearance, the response times, content presentation, all fall under the User Experience. Hence, a good UI leads to a good UX.

### Screens 
In this application, the UI was designed using the Figma design tool. Below are screenshots showing the various screens as embedded in the application.


#### Mobile - Light
![image](screens/Screenshot%20from%202023-07-19%2014-55-21.png)
![image](screens/Screenshot%20from%202023-07-19%2014-57-17.png)
![image](screens/Screenshot%20from%202023-07-19%2014-57-36.png)
![image](screens/Screenshot%20from%202023-07-19%2014-57-50.png)
![image](screens/Screenshot%20from%202023-07-19%2014-58-09.png)
![image](screens/Screenshot%20from%202023-07-19%2014-58-25.png)
![image](screens/Screenshot%20from%202023-07-19%2014-58-37.png)
![image](screens/Screenshot%20from%202023-07-19%2014-59-05.png)

#### Mobile - Dark
![image](screens/Screenshot%20from%202023-07-19%2014-59-38.png)
![image](screens/Screenshot%20from%202023-07-19%2014-59-51.png)
![image](screens/Screenshot%20from%202023-07-19%2015-00-03.png)
![image](screens/Screenshot%20from%202023-07-19%2015-00-03.png)
![image](screens/Screenshot%20from%202023-07-19%2015-00-15.png)
![image](screens/Screenshot%20from%202023-07-19%2015-00-31.png)
![image](screens/Screenshot%20from%202023-07-19%2015-00-40.png)
![image](screens/Screenshot%20from%202023-07-19%2015-00-55.png)
![image](screens/Screenshot%20from%202023-07-19%2015-01-06.png)

#### Medium - Light 
![image](screens/Screenshot%20from%202023-07-19%2015-03-33.png)
![image](screens/Screenshot%20from%202023-07-19%2015-03-44.png)


The application UI was designed with a mobile-first design approach. The screens have also been optimized to run smoothly on medium and larger screens,  such as tablets, folds, and desktops. The screens are also optimized for dark mode as shown above. 


## Application Software Architecture 
This application consists of a single activity, the MainActivity. An activity is one of the android app components, the rest of which are services, content providers, and broadcast receivers. The activity is declared in the app manifest. The app manifest is an xml script that the android OS uses to decide how to integrate this app into the device’s overall user experience. 
Mobile devices are generally resource-constrained, so at any time, the OS might kill certain app processes running in the background  in order to make room available to newly launched apps. These OS memory management events are totally out of the control of the user, thus it is important that every android app be well architected to optimize its productivity and stability.

 ### Architecture of this app 
 This app consists of the UI, domain and the data layers respectively. 
 [Image here!]

 The architecture follows a reactive programming model with a unidirectional data flow. The higher layers react to changes in lower layers. All events flow down, while all data flow up. The data flow is achieved using streams, implemented using Kotlin Flows.


#### a. Data Layer 
The data layer is implemented as an offline-first source of app data and business logic. It is the source of truth for all data in the app.

[Image here!]

The app consists of the `HistoryRepository`, attach to this repository is the `HistoryEntity` model. Based on the repository design pattern, the `HistoryRepository` is the public API for the UI. It provides the only way the UI can access the `HistoryEntity` from the database. It offers several methods for reading and writing data.


##### Reading data 
Data is exposed as data streams. This means that every client of the repository must be prepared to react to data changes. Because there is no guarantee that a particular data would be valid at the time it is used, the data is not exposed as a snapshot. 
Reads are performed from local storage i.e. Room database and the Proto DataStore, therefore errors are not expected when reading from the Repository. 
Example: reading a list of history items from the database 
A list of history items can be obtained by subscribing to `HistoryRepository::showHistory` flow which emits `List<HistoryEntity>`.


##### Writing data 
Suspend functions are provided by the repository to write data. It is up to the caller to ensure that their execution is suitably scoped.
Example: Register an image taken by the camera
Simply call `HistoryRepository::registerHistory` and pass the `historyEntity` which models the component name that the image represents, the image url, and the timestamp. And of course, every `historyEntity` is allocated a unique ID in the database.

##### Data sources 
The `OfflineFirstHistoryRepository` depends on the `HistoryDao` and the `HistoryPagingSource` data sources. 

[table here!]

#### b. Domain Layer 
The domain layer in this app houses all the relevant business logic. It consists of use cases, repository interfaces, and other helper classes. Use cases are classes having a single invocable method `(operator fun invoke)` containing business logic.

In this layer, we have the interface `ComponentClassifier` which describes how the processing of the image caught by the camera would be classified to produce the label (component name) and probability.

We also have the `HistoryRepository` interface which describes how the reading and the writing of the history data would be performed. This domain layer contains three use cases; `CameraPreviewUseCase`, `ImageAnalysisUseCase`, and the `ImageCaptureUseCase`.

The `CameraPreviewUseCase` houses the logic that provides a camera preview stream for displaying on-screen. The preview screen is connected to the `Surface` provided via `Preview.SurfaceProvider`. The application decides how the `Surface` is shown, and is responsible for managing the `Surface` lifecycle after providing it.

To display the preview with the correct orientation, the app needs to take different actions based on the source of the `Surface`. There are several sources from which the app could derive the preview `Surface`, e.g `SurfaceView`, `PreviewView`,  `ImageReader`, `MediaCodec`, `SurfaceTexture`,  `TextureView`, etc. 

In this app, we used the `PreviewView` to provide the camera preview Surface, this is so that the `Surface` produced would always be in the device’s display orientation, saving us the extra boilerplate code to manually configure the Surface to be in sync with the device as it performs rotation. 

The `ImageAnalysisUseCase` houses the logic that provides the CPU with accessible images on which the app will perform the image analysis. It returns an `ImageAnalysis` object which acquires images from the camera via an `ImageReader`. Each image is provided to an `ImageAnalysis.Analyzer` function which has been implemented on the UI layer, from here the app accesses the image data for image analysis via an `ImageProxy`. This proxy has to be closed after usage, lest future images be stalled or dropped depending on the backpressure strategy.

The `ImageCaptureUseCase` houses the logic for taking a picture. It returns an `ImageCapture` object which provides the `takePicture()` function to take a picture to memory or save to a file, while providing image metadata.


#### c. UI Layer 
It comprises UI elements built using Jetpack Compose, coupled with a ViewModel class, `MainViewModel`. 

##### ViewModel
A ViewModel is a business logic or screen level state holder. The primary function of the ViewModel is to expose state to the UI and encapsulate related business logic. It offers the added advantage of caching state and persisting it through configuration changes. The UI layer doesn’t need to fetch data again after a configuration change or when navigating between activities. The ViewModel is the right place to handle business logic that lives in the UI layer. It also is in charge of handling events and delegating them to other layers of the hierarchy when business logic needs to be applied to modify application data.


The `MainViewModel` class in this app receive streams of data from use cases and the repository, and transforms into UI state. The UI composables reflect this state, and provide ways for the user to interact with the app. These interactions are passed as events to the `MainViewModel` where they are processed.


[image here!]

##### UI state 
In this app, the states are modeled using separate transforms of data streams. Each state represents the underlying app data, considering the app data as the source-of-truth. And the UI composables handle all possible states.



### The Architectural Pattern 
#### Overview 
An architecture represents the organizational structure and behavioural components of a software system. It is the system’s core ideas and characteristics with respect to its relationships, environment, and other design principles. A great and well-structured architecture yields a highly performant, fault-tolerant and scalable system.
This architectural pattern used in this app is the MVVM. Others include MVC, MVP, Viper, etc. MVVM is the short form for Model View ViewModel. In this pattern, views can bind themselves to data streams which are exposed by the viewmodel. The View has a reference to the viewmodel, while the viewmodel has no information about the view.


#### Model 
This holds the data of the application. It cannot directly talk to the View. Generally, it is recommended to expose the data to the ViewModel through Observables and Streams such as LiveData, Flows, etc.


#### View 
This represents the UI of the application devoid of any Application Logic. It observes the ViewModel.


#### ViewModel 
It acts as a link between the Model and the View.  It is responsible for transforming the data from the Model. It provides data streams to the View. It also uses hooks or callbacks to update the View. It requests the data from the Model.
The MVVM pattern is the officially recommended pattern for building android, as described by Google.



## Feature Implementation 
The primary purpose for this app is to analyze and classify electronic components. It contains several other features that work together to deliver a great user experience, while optimizing the device resources. These features are listed below;


### a. Image Analysis and Classification 
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


### b. Take Photo 
In addition to the Camera Preview use case already enabled, this app uses the Image Capture use case. The capture mode is set to maximum photo quality. The flashlight setting is also available to be toggled by the user. 

The `ImageCapture` use case is bound to the `Camera` object. And to  enable us to take a photo, an extension function `ImageCapture.takeSnapshot(Executor)` was created. This wraps around the CameraX API function `takePicture(ImageCapture.OutFileOptions, Executor, ImageCapture.OnImageSavedCallback)`. By invoking this function, the photo is taken and the file is immediately saved by the `onSavePhotoFile(File)` function. The URL of the photo file is persisted using the Room database. 


### c. Photo History 
Since all the saved photos are persisted using the Room database, the user is able to retrieve the photos at any time and offline. Streams are used to observe the changes in the database and update the UI accordingly. The list of photos is also paged using the Paging3 library in order to mitigate against janks while loading a large dataset. Therefore, thousands of photos can be saved without worries of dragging the UI flow. 


### d. Toggle Flashlight 
A `StateFlow` of `ImageCaptureFlashMode`  is used to hold the state of the flashlight. The flashlight has three states: ***Auto, Off, and On***. A user can toggle between these states by simply tapping on the flashlight button on the top-right corner on the `MainPreview` screen.

### e. Toggle Camera 
The camera state is held by the `StateFlow` of `CameraSelector`. The camera has two states: `DEFAULT_BACK_CAMER` and `DEFAULT_FRONT_CAMERA`. The user can toggle the camera state by tapping on the camera button on the bottom-right corner.


### f. Device Rotation Sensing 
The state of device rotation position is hoisted into the `MainPreviewScreen` using a `MutableState` of `Float`. As the device is rotated, an `OrientationEventListener` observes the angle of rotation and updates the UI state accordingly. The `ImageCapture` and `ImageAnalysis` use cases  also respond to these rotation changes. On the UI, the icons are fed with the `Modifier.rotate(Float)` function so they respond to the device rotation. 


### g. Bottom Sheet Management
The width and height of the bottom sheet are calculated based on the screen size of the device. We basically have three categories of screen sizes; ***Compact, Medium and Expanded***. For a compact device, the height of the sheet is forty percent of the total height, while for the others, the height corresponds to the overall height of the device. The width of the bottom sheet for a compact device is equal to the overall width of the device, but for others, it is thirty percent. 


Initially, the bottom sheet starts in the minimized state stored in the field `bottomSheetMinimized` as true. Meanwhile the `classificationState` is also set to true. This means that the application is actively taking in readings from the camera and buffering them. After at least three seconds of exposure, the component identified with the highest probability is returned and displayed on the bottom sheet. While the bottom sheet is visible, `bottomSheetMinimized` is set to false and the `classificationState` is also set to false. 


By clicking on the minimize button on the top-right corner of the bottom sheet, the entire process is reversed and repeated. `bottomSheetMinimized` and `classificationState` are both set to false.


### h. Web Content Preview
On clicking on the “read more” button on the bottom sheet, a web page is shown. The web page shown is a wikipedia page carrying more information about the identified component. The application knows which url to open for each component as registered in a `HashMap` carrying the linking information. This `HashMap` is stored in the `network` package.


### i. Dark Theme 
There is a `DarkThemeConfig` enum class that holds the theming states. By default the theme is set to follow the device theme. To change this setting, the options menu on the `HistoryScreen`  provides the dialog on which the desired setting can be checked. The theming information is cached using the Proto DataStore library. The relevant files are located under the `datastore` package.



![image](screenshots/Screenshot_20230421-225826.png)
![image](screenshots/Screenshot_20230421-225837.png)
![image](screenshots/Screenshot_20230421-225901.png)

