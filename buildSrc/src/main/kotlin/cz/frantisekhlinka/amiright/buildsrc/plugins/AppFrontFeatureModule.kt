package cz.frantisekhlinka.amiright.buildsrc.plugins

import featureModuleDependencies
import implementation
import testImplementation
import androidTestImplementation
import com.android.build.gradle.BaseExtension
import debugImplementation
import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.project

/**
 * A plugin for a feature module that contains UI.
 */
open class AppFrontFeatureModule : AppCoreAndroidPlugin() {

    override fun configurePlugins(container: PluginContainer) {
        super.configurePlugins(container)
        container.run {
            apply(Plugins.kotlinCompose)
        }
    }

    override fun configureAndroid(androidExtension: BaseExtension) {
        super.configureAndroid(androidExtension)
        androidExtension.run {
            buildFeatures.compose = true
        }
    }

    override fun configureDependencies(scope: DependencyHandlerScope, target: Project) {
        super.configureDependencies(scope, target)
        scope.run {
            implementation(project(Modules.coreFront))
            featureModuleDependencies()

            testImplementation(Libraries.Test.junit)
            testImplementation(Libraries.Test.mocckK)
            testImplementation(Libraries.Test.coroutinesTest)
            testImplementation(project(Modules.coreTest))

            androidTestImplementation(Libraries.Test.Android.androidXJunit)
            androidTestImplementation(Libraries.Test.Android.espresso)
            androidTestImplementation(platform(Libraries.Compose.bom))
            androidTestImplementation(Libraries.Compose.Test.testJunit)

            debugImplementation(Libraries.Compose.Test.testManifest)
        }
    }
}