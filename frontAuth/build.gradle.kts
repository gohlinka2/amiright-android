plugins {
    id(Plugins.appFrontFeatureModule)
}

android {
    namespace = "${ApplicationId.id}.frontauth"
}

dependencies {
    implementation(project(Modules.backAuth))

    implementation(Libraries.credentialsManager)
}