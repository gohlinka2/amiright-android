plugins {
    id(Plugins.appCoreAndroid)
}

android {
    namespace = "${ApplicationId.id}.coreback"
}

dependencies {
    api(project(Modules.coreData))

    firebase {
        implementation(Libraries.Firebase.firestore)
    }
}