plugins {
    id(Plugins.appCoreAndroid)
}

android {
    namespace = "${ApplicationId.id}.coreback"
}

dependencies {
    api(project(Modules.coreData))
}