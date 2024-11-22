package cz.frantisekhlinka.amiright.buildsrc.plugins

import compose
import implementation
import testImplementation
import androidTestImplementation
import debugImplementation
import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.project

/**
 * A plugin for a feature module that contains UI.
 */
open class AppFeatureModule : AppCoreAndroidPlugin() {
    override fun configureDependencies(scope: DependencyHandlerScope, target: Project) {
        super.configureDependencies(scope, target)
        scope.run {
            implementation(project(Modules.coreBack))

            implementation(Libraries.androidCoreKtx)
            implementation(Libraries.lifecycleKtx)
            implementation(Libraries.activityCompose)
            implementation(Libraries.koinAndroid)

            compose {
                implementation(Libraries.Compose.compose)
                implementation(Libraries.Compose.graphics)
                implementation(Libraries.Compose.toolingPreview)
                implementation(Libraries.Compose.material)
            }

            testImplementation(Libraries.junit)

            androidTestImplementation(Libraries.androidXJunit)
            androidTestImplementation(Libraries.espresso)
            androidTestImplementation(platform(Libraries.Compose.bom))
            androidTestImplementation(Libraries.Compose.testJunit)

            debugImplementation(Libraries.Compose.tooling)
            debugImplementation(Libraries.Compose.testManifest)
        }
    }
}