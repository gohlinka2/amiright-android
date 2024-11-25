plugins {
    id(Plugins.appBackFeatureModule)
}

android {
    namespace = "${ApplicationId.id}.backauth"
}

dependencies {
    implementation(Libraries.credentialsManager)
    implementation(Libraries.credentialsPlayServicesAuth)
    implementation(Libraries.googleId)

    firebase {
        implementation(Libraries.Firebase.auth)
    }
}