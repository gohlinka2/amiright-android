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

object Modules {
    const val coreBack = ":coreBack"

    const val backAuth = ":backAuth"

    const val frontAuth = ":frontAuth"
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
    const val googleServices = "4.4.2"
    const val firebaseBom = "33.6.0"
    const val koin = "4.0.0"
    const val coroutinesCore = "1.9.0"
}

object Libraries {
    const val androidCoreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val junit = "junit:junit:${Versions.junit}"
    const val androidXJunit = "androidx.test.ext:junit:${Versions.junitVersion}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    const val lifecycleKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtx}"
    const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"
    const val koinAndroid = "io.insert-koin:koin-android:${Versions.koin}"
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCore}"

    object Compose {
        const val bom = "androidx.compose:compose-bom:${Versions.composeBom}"
        const val compose = "androidx.compose.ui:ui"
        const val graphics = "androidx.compose.ui:ui-graphics"
        const val tooling = "androidx.compose.ui:ui-tooling"
        const val toolingPreview = "androidx.compose.ui:ui-tooling-preview"
        const val testManifest = "androidx.compose.ui:ui-test-manifest"
        const val testJunit = "androidx.compose.ui:ui-test-junit4"
        const val material = "androidx.compose.material3:material3"
    }

    object Firebase {
        const val bom = "com.google.firebase:firebase-bom:${Versions.firebaseBom}"
        const val firestore = "com.google.firebase:firebase-firestore"
        const val auth = "com.google.firebase:firebase-auth"
        const val functions = "com.google.firebase:firebase-functions"
    }
}

object Plugins {
    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val kotlinAndroid = "org.jetbrains.kotlin.android"
    const val kotlinCompose = "org.jetbrains.kotlin.plugin.compose"
    const val googleServices = "com.google.gms.google-services"
    const val kotlinLibrary = "org.jetbrains.kotlin.jvm"
    const val javaLibrary = "java-library"

    // custom plugins
    const val appCoreAndroid = "app-core-android-plugin" // has to match with occurrence in build.gradle.kts of buildSrc
    const val appFeatureModule = "app-feature-module" // has to match with occurrence in build.gradle.kts of buildSrc
}
