plugins {
    id(Plugins.appCoreAndroid)
}

android {
    namespace = "${ApplicationId.id}.corefront"
}

dependencies {
    featureModuleDependencies()
}