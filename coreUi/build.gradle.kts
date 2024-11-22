plugins {
    id(Plugins.appCoreAndroid)
    id(Plugins.kotlinCompose)
}

android {
    namespace = "${ApplicationId.id}.coreui"

    buildFeatures {
        compose = true
    }
}

dependencies {
    compose {
        implementation(Libraries.Compose.compose)
        implementation(Libraries.Compose.graphics)
        implementation(Libraries.Compose.toolingPreview)
        implementation(Libraries.Compose.material)
    }

    androidTestImplementation(platform(Libraries.Compose.bom))
    androidTestImplementation(Libraries.Compose.testJunit)

    debugImplementation(Libraries.Compose.tooling)
}