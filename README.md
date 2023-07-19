
# Electronic Component Classifier 

## Overview 
This is a demo project to showcase the possibility of identifying electronic components by the use of an android app. For identification, the app uses the **Tensorflow library.** 

## User Interface Design 
The User Interface makes up the visible parts of the application software. It is a term used to describe all the points of human-computer interaction and communication in the application, or generally in any machine. Accompanying the User Interface, is the User Experience. Based on the given User Interface, the overall experience the user derives from interacting with the software, including the aesthetic appearance, the response times, content presentation, all fall under the User Experience. Hence, a good UI leads to a good UX.

### Screens 
In this application, the UI was designed using the Figma design tool. Below are screenshots showing the various screens as embedded in the application.

[Screens here!]

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

The app consists of the HistoryRepository, attach to this repository is the HistoryEntity model. Based on the repository design pattern, the HistoryRepository is the public API for the UI. It provides the only way the UI can access the HistoryEntity from the database. It offers several methods for reading and writing data.


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




![image](screenshots/Screenshot_20230421-225826.png)
![image](screenshots/Screenshot_20230421-225837.png)
![image](screenshots/Screenshot_20230421-225901.png)

