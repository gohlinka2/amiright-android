plugins {
    id(Plugins.appCoreAndroid)
}

android {
    namespace = "${ApplicationId.id}.coretest"
}

dependencies {
    implementation(Libraries.Test.coroutinesTest)
    implementation(Libraries.Test.junit)
}