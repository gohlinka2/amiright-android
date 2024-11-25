plugins {
    id(Plugins.appBackFeatureModule)
}

android {
    namespace = "${ApplicationId.id}.backpost"
}

dependencies {
    firebase {
        implementation(Libraries.Firebase.firestore)
        implementation(Libraries.Firebase.functions)
    }
}