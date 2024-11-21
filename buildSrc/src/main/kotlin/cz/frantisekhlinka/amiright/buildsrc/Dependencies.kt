object ApplicationId {
    const val id = "cz.frantisekhlinka.amiright"
}

object AndroidSDKVersions {
    const val minSdk = 24
    const val targetSdk = 35
}

object Releases {
    const val versionCode = 1
    const val versionName = "1.0"
}

object BuildTypes {
    const val DEBUG = "debug"
    const val RELEASE = "release"
}

object ProguardFiles {
    const val androidOptimize = "proguard-android-optimize.txt"
    const val rules = "proguard-rules.pro"
}

object Versions {
    const val agp = "8.7.1" // has to match with occurrence in build.gradle.kts of buildSrc
    const val kotlin = "2.0.0" // has to match with occurrence in build.gradle.kts of buildSrc
    const val coreKtx = "1.15.0"
    const val junit = "4.13.2"
    const val junitVersion = "1.2.1"
    const val espressoCore = "3.6.1"
    const val lifecycleRuntimeKtx = "2.8.7"
    const val activityCompose = "1.9.3"
    const val composeBom = "2024.04.01"
}

object Libraries {
    const val androidCoreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val junit = "junit:junit:${Versions.junit}"
    const val androidXJunit = "androidx.test.ext:junit:${Versions.junitVersion}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    const val lifecycleKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtx}"
    const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"
    const val composeBom = "androidx.compose:compose-bom:${Versions.composeBom}"
    const val compose = "androidx.compose.ui:ui"
    const val composeGraphics = "androidx.compose.ui:ui-graphics"
    const val composeTooling = "androidx.compose.ui:ui-tooling"
    const val composeToolingPreview = "androidx.compose.ui:ui-tooling-preview"
    const val composeTestManifest = "androidx.compose.ui:ui-test-manifest"
    const val composeTestJunit = "androidx.compose.ui:ui-test-junit4"
    const val composeMaterial = "androidx.compose.material3:material3"
}

object Plugins {
    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val kotlinAndroid = "org.jetbrains.kotlin.android"
    const val kotlinCompose = "org.jetbrains.kotlin.plugin.compose"

    // custom plugins
    const val appCoreAndroid = "app-core-android-plugin" // has to match with occurrence in build.gradle.kts of buildSrc
    const val appFeatureModule = "app-feature-module" // has to match with occurrence in build.gradle.kts of buildSrc
}
