// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // cannot use alias here when using AGP in buildSrc (see https://github.com/gradle/gradle/issues/20084)
    id(libs.plugins.androidApplication.get().pluginId) apply false
    id(libs.plugins.kotlinAndroid.get().pluginId) apply false
    alias(libs.plugins.kotlinCompose) apply false
}