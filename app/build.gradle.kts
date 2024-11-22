import cz.frantisekhlinka.amiright.buildsrc.implementation

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
    implementation(Libraries.androidCoreKtx)
    implementation(Libraries.lifecycleKtx)
    implementation(Libraries.activityCompose)
    implementation(Libraries.koinAndroid)

    implementation(platform(Libraries.Compose.bom))
    implementation(Libraries.Compose.compose)
    implementation(Libraries.Compose.graphics)
    implementation(Libraries.Compose.toolingPreview)
    implementation(Libraries.Compose.material)

    implementation(platform(Libraries.Firebase.bom))
    implementation(Libraries.Firebase.auth)
    implementation(Libraries.Firebase.firestore)
    implementation(Libraries.Firebase.functions)

    testImplementation(Libraries.junit)

    androidTestImplementation(Libraries.androidXJunit)
    androidTestImplementation(Libraries.espresso)
    androidTestImplementation(platform(Libraries.Compose.bom))
    androidTestImplementation(Libraries.Compose.testJunit)

    debugImplementation(Libraries.Compose.tooling)
    debugImplementation(Libraries.Compose.testManifest)
}