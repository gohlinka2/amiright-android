plugins {
    id(Plugins.appCoreAndroid)
}

android {
    namespace = "${ApplicationId.id}.backauth"
}

dependencies {
    firebase {
        implementation(Libraries.Firebase.auth)
    }
}