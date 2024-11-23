plugins {
    id(Plugins.appCoreAndroid)
}

android {
    namespace = "${ApplicationId.id}.backauth"
}

dependencies {
    implementation(project(Modules.coreBack))
    implementation(project(Modules.coreData))

    implementation(Libraries.koinAndroidCompose)
    implementation(Libraries.credentialsManager)
    implementation(Libraries.credentialsPlayServicesAuth)
    implementation(Libraries.googleId)

    firebase {
        implementation(Libraries.Firebase.auth)
    }
}