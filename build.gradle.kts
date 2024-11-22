// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Plugins.androidApplication) apply false
    id(Plugins.kotlinAndroid) apply false
    id(Plugins.kotlinCompose) version Versions.kotlin apply false
    id(Plugins.googleServices) version Versions.googleServices apply false
    id(Plugins.kotlinLibrary) apply false
}