// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Plugins.androidApplication) apply false
    id(Plugins.kotlinAndroid) apply false
    id(Plugins.kotlinCompose) apply false version Versions.kotlin
    id(Plugins.googleServices) apply false version Versions.googleServices
}