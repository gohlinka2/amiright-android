plugins {
    id(Plugins.appFeatureModule)
}

android {
    namespace = "${ApplicationId.id}.frontauth"
}

dependencies {
    implementation(project(Modules.backAuth))
}