plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("com.android.tools.build:gradle:8.7.1") // IMPORTANT: this has to match the agp version in Dependencies.kt
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.0") // IMPORTANT: this has to match the kotlin version in Dependencies.kt
}

gradlePlugin {
    plugins {
        create("app-front-feature-module-plugin") {
            id = "app-front-feature-module" // this has to match with appFeatureModule constant in Dependencies.kt
            implementationClass = "cz.frantisekhlinka.amiright.buildsrc.plugins.AppFrontFeatureModule"
        }
        create("app-back-feature-module-plugin") {
            id = "app-back-feature-module" // this has to match with appFeatureModule constant in Dependencies.kt
            implementationClass = "cz.frantisekhlinka.amiright.buildsrc.plugins.AppBackFeatureModule"
        }
        create("app-core-android-plugin") {
            id = "app-core-android-plugin" // this has to match with appCoreAndroid constant in Dependencies.kt
            implementationClass = "cz.frantisekhlinka.amiright.buildsrc.plugins.AppCoreAndroidPlugin"
        }
    }
}