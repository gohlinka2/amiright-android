package cz.frantisekhlinka.amiright.buildsrc.plugins.abstr

import cz.frantisekhlinka.amiright.buildsrc.*
import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.getByType

/**
 * Base gradle plugin for simplifying build scripts of android library modules.
 */
abstract class BaseAndroidGradlePlugin : BaseGradlePlugin() {

    override fun apply(target: Project) {
        super.apply(target)
        configureAndroid(target.extensions.getByType())
    }

    override fun configurePlugins(container: PluginContainer) {
        super.configurePlugins(container)
        container.run {
            apply(Plugins.androidLibrary)
            apply(Plugins.kotlinAndroid)
        }
    }

    /**
     * Use the [extension] to configure the android extension block.
     * NOTE: Don't forget to call super in overriding methods.
     */
    protected open fun configureAndroid(extension: BaseExtension) {
        extension.run {
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
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }
    }
}
