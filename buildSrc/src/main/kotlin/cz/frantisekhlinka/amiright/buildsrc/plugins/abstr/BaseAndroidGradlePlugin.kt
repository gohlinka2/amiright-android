package cz.frantisekhlinka.amiright.buildsrc.plugins.abstr

import AndroidSDKVersions
import BuildTypes
import Plugins
import ProguardFiles
import Releases
import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

/**
 * Base gradle plugin for simplifying build scripts of android library modules.
 */
abstract class BaseAndroidGradlePlugin : BaseGradlePlugin() {

    override fun apply(target: Project) {
        super.apply(target)
        configureAndroid(target.extensions.getByType())

        target.tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java) {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_11)
            }
        }
    }

    override fun configurePlugins(container: PluginContainer) {
        super.configurePlugins(container)
        container.run {
            apply(Plugins.androidLibrary)
            apply(Plugins.kotlinAndroid)
        }
    }

    /**
     * Use the [androidExtension] to configure the android extension block.
     * NOTE: Don't forget to call super in overriding methods.
     */
    protected open fun configureAndroid(androidExtension: BaseExtension) {
        androidExtension.run {
            compileSdkVersion(AndroidSDKVersions.targetSdk)
            defaultConfig {
                minSdk = AndroidSDKVersions.minSdk
                targetSdk = AndroidSDKVersions.targetSdk
                versionCode = Releases.versionCode
                versionName = Releases.versionName
            }

            buildTypes {
                getByName(BuildTypes.RELEASE) {
                    proguardFiles(
                        getDefaultProguardFile(ProguardFiles.androidOptimize),
                        ProguardFiles.rules
                    )
                }
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_11
                targetCompatibility = JavaVersion.VERSION_11
            }
        }
    }
}
