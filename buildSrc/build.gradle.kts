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