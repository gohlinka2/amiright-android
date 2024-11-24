plugins {
    id(Plugins.javaLibrary)
    id(Plugins.kotlinLibrary)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dependencies {
    api(project(Modules.coreData))

    implementation(Libraries.coroutinesCore)
    implementation(Libraries.koinCore)
}