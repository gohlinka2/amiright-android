plugins {
    // cannot use alias here when using AGP in buildSrc (see https://github.com/gradle/gradle/issues/20084)
    id(libs.plugins.androidApplication.get().pluginId)
    id(libs.plugins.kotlinAndroid.get().pluginId)
    alias(libs.plugins.kotlinCompose)
}

android {
    namespace = "cz.frantisekhlinka.amiright"
    compileSdk = 35

    defaultConfig {
        applicationId = "cz.frantisekhlinka.amiright"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
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

    implementation(libs.androidCoreKtx)
    implementation(libs.lifecycleKtx)
    implementation(libs.activityCompose)
    implementation(platform(libs.composeBom))
    implementation(libs.compose)
    implementation(libs.composeGraphics)
    implementation(libs.composeToolingPreview)
    implementation(libs.composeMaterial)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidXJunit)
    androidTestImplementation(libs.espresso)
    androidTestImplementation(platform(libs.composeBom))
    androidTestImplementation(libs.composeTestJunit)
    debugImplementation(libs.composeTooling)
    debugImplementation(libs.composeTestManifest)
}