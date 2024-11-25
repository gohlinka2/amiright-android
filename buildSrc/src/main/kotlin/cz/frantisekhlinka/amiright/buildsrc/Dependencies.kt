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
    const val coreFront = ":coreFront"
    const val coreUi = ":coreUi"
    const val coreData = ":coreData"

    const val backAuth = ":backAuth"
    const val backPost = ":backPost"

    const val frontAuth = ":frontAuth"
    const val frontHome = ":frontHome"
    const val frontCreatePost = ":frontCreatePost"
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
    const val navigation = "2.8.4"
    const val credentialsManager = "1.3.0"
    const val googleId = "1.1.1"
    const val mockK = "1.13.13"
}

object Libraries {
    const val androidCoreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val lifecycleKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtx}"
    const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"
    const val koinCore = "io.insert-koin:koin-core:${Versions.koin}"
    const val koinAndroidCompose = "io.insert-koin:koin-androidx-compose:${Versions.koin}"
    const val koinAndroidComposeNavigation = "io.insert-koin:koin-androidx-compose-navigation:${Versions.koin}"
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCore}"
    const val composeNavigation = "androidx.navigation:navigation-compose:${Versions.navigation}"
    const val credentialsManager = "androidx.credentials:credentials:${Versions.credentialsManager}"
    const val credentialsPlayServicesAuth = "androidx.credentials:credentials-play-services-auth:${Versions.credentialsManager}"
    const val googleId = "com.google.android.libraries.identity.googleid:googleid:${Versions.googleId}"

    object Compose {
        const val bom = "androidx.compose:compose-bom:${Versions.composeBom}"
        const val compose = "androidx.compose.ui:ui"
        const val graphics = "androidx.compose.ui:ui-graphics"
        const val material = "androidx.compose.material3:material3"

        const val tooling = "androidx.compose.ui:ui-tooling"
        const val toolingPreview = "androidx.compose.ui:ui-tooling-preview"

        object Test {
            const val testJunit = "androidx.compose.ui:ui-test-junit4"
            const val testManifest = "androidx.compose.ui:ui-test-manifest"
        }
    }

    object Firebase {
        const val bom = "com.google.firebase:firebase-bom:${Versions.firebaseBom}"
        const val firestore = "com.google.firebase:firebase-firestore"
        const val auth = "com.google.firebase:firebase-auth"
        const val functions = "com.google.firebase:firebase-functions"
    }

    object Test {
        const val junit = "junit:junit:${Versions.junit}"
        const val mocckK = "io.mockk:mockk:${Versions.mockK}"
        const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesCore}"

        object Android {
            const val androidXJunit = "androidx.test.ext:junit:${Versions.junitVersion}"
            const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
        }
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
    const val appFrontFeatureModule = "app-front-feature-module" // has to match with occurrence in build.gradle.kts of buildSrc
    const val appBackFeatureModule = "app-back-feature-module" // has to match with occurrence in build.gradle.kts of buildSrc
}
