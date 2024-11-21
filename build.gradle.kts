import cz.frantisekhlinka.amiright.buildsrc.Plugins
import cz.frantisekhlinka.amiright.buildsrc.Versions

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // cannot use alias here when using AGP in buildSrc (see https://github.com/gradle/gradle/issues/20084)
    id(Plugins.androidApplication) apply false version Versions.agp
    id(Plugins.kotlinAndroid) apply false version Versions.kotlin
    id(Plugins.kotlinCompose) apply false version Versions.kotlin
}