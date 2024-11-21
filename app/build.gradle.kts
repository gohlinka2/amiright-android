import cz.frantisekhlinka.amiright.buildsrc.AndroidSDKVersions
import cz.frantisekhlinka.amiright.buildsrc.ApplicationId
import cz.frantisekhlinka.amiright.buildsrc.BuildTypes
import cz.frantisekhlinka.amiright.buildsrc.Libraries
import cz.frantisekhlinka.amiright.buildsrc.Plugins
import cz.frantisekhlinka.amiright.buildsrc.ProguardFiles
import cz.frantisekhlinka.amiright.buildsrc.Releases

plugins {
    // cannot use alias here when using AGP in buildSrc (see https://github.com/gradle/gradle/issues/20084)
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