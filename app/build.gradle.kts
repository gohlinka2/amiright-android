plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinCompose)
    id(Plugins.googleServices)
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
    implementation(project(Modules.coreUi))
    implementation(project(Modules.backAuth))
    implementation(project(Modules.frontAuth))
    implementation(project(Modules.coreBack))
    implementation(project(Modules.coreData))

    implementation(Libraries.androidCoreKtx)
    implementation(Libraries.lifecycleKtx)
    implementation(Libraries.activityCompose)
    implementation(Libraries.koinAndroidCompose)
    implementation(Libraries.koinAndroidComposeNavigation)
    implementation(Libraries.composeNavigation)

    compose {
        implementation(Libraries.Compose.compose)
        implementation(Libraries.Compose.graphics)
        implementation(Libraries.Compose.toolingPreview)
        implementation(Libraries.Compose.material)
    }

    firebase {
        implementation(Libraries.Firebase.auth)
        implementation(Libraries.Firebase.firestore)
        implementation(Libraries.Firebase.functions)
    }

    testImplementation(Libraries.junit)

    androidTestImplementation(Libraries.androidXJunit)
    androidTestImplementation(Libraries.espresso)
    androidTestImplementation(platform(Libraries.Compose.bom))
    androidTestImplementation(Libraries.Compose.testJunit)

    debugImplementation(Libraries.Compose.tooling)
    debugImplementation(Libraries.Compose.testManifest)
}