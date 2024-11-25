# AMIRIGHT? Android app

An example Android app, using Compose UI, Koin, Compose Navigation, MVVM and Firebase. The app is modularized and features custom Gradle Plugins.

# App preview

The app is a minimalist social network, where users can post their bold statements and then have other users vote (agree/disagree) on those statements.

![App preview GIF](./screens/preview.gif?raw=true "App preview")

# Modularization & Architecture

The app uses MVVM.

Even though modularization is probably overkill for this app, it is here mostly for demonstration purposes.
The app is modularized to separate responsibilities, organize code and improve build times.

Modules are separated by purpose and in some cases by layer as well.
There are 3 kinds of modules:

- **backX**: UI-less feature modules that encompass a specific functionality, such as authentication or the data layer for a specific data type (Here, that is only logic related to posts. We could also f.e. add a user module if we wanted to add user profiles).
- **frontX**: UI feature modules - they implement a specific UI feature, such as creating a post or authenticating. They only export their screens, so they can be included in the NavHost - but all other implementation details are internal.
- **coreX**: A few modules that all other modules build upon:
  - **coreUi**: provides common UI logic.
  - **coreData**: hosts the app's models.
  - **coreBack**/**coreFront**: hold common logic that all front/back modules can depend on.
  - **coreTest**: provides common test utilities.

The **app** module ties it all together, depending on all other modules and owning the navigation and DI.

On top of the build stack is **buildSrc** which provides dependency notations for other modules and also implements custom Gradle Plugins, so that the build scripts of other modules can remain nice and clean. Check [AppFrontFeatureModule](./buildSrc/src/main/kotlin/cz/frantisekhlinka/amiright/buildsrc/plugins/AppFrontFeatureModule.kt) to see an example of this.

# Backend

The Firebase Firestore database is the single source of truth for all data. All reads are done directly on the DB, while all writes are done with Cloud Functions to ensure data integrity and enable custom logic on the backend. 
In a more complex app, we could add f.e. the Room database as the single source of truth if we needed more control over it.

# Navigation

The app uses Compose Navigation, hosted by a single activity. All UI feature modules export their entry-point screens. The navigation controller is hoisted to the app level and child screens can only navigate by getting passed navigation callbacks in their arguments.

# Building

To build, you need to create your own Firebase project and add its `google-services.json`. The source code for the Cloud Functions is in [this repo](https://github.com/gohlinka2/amiright-firebase/).
