plugins {
    id(Plugins.appCoreAndroid)
}

android {
    namespace = "${ApplicationId.id}.backpost"
}

dependencies {
    implementation(project(Modules.coreBack))

    implementation(Libraries.koinCore)

    firebase {
        implementation(Libraries.Firebase.firestore)
        implementation(Libraries.Firebase.functions)
    }
}