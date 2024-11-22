plugins {
    id(Plugins.appCoreAndroid)
}

android {
    namespace = "${ApplicationId.id}.backauth"
}

dependencies {
    implementation(project(Modules.coreBack))

    firebase {
        implementation(Libraries.Firebase.auth)
    }
}