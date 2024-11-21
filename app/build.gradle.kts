plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinCompose)
}

android {
    namespace = ApplicationId.id
    compileSdk = AndroidSDKVersions.targetSdk

    defaultConfig {
        applicationId = ApplicationId.id
        minSdk = AndroidSDKVersions.minSdk
        targetSdk = AndroidSDKVersions.targetSdk
        versionCode = Releases.versionCode
        versionName = Releases.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName(BuildTypes.RELEASE) {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(ProguardFiles.androidOptimize),
                ProguardFiles.rules
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(Libraries.androidCoreKtx)
    implementation(Libraries.lifecycleKtx)
    implementation(Libraries.activityCompose)
    implementation(platform(Libraries.composeBom))
    implementation(Libraries.compose)
    implementation(Libraries.composeGraphics)
    implementation(Libraries.composeToolingPreview)
    implementation(Libraries.composeMaterial)
    testImplementation(Libraries.junit)
    androidTestImplementation(Libraries.androidXJunit)
    androidTestImplementation(Libraries.espresso)
    androidTestImplementation(platform(Libraries.composeBom))
    androidTestImplementation(Libraries.composeTestJunit)
    debugImplementation(Libraries.composeTooling)
    debugImplementation(Libraries.composeTestManifest)
}