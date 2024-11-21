package cz.frantisekhlinka.amiright.buildsrc.plugins

import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope

/**
 * A plugin for a feature module that contains UI.
 */
open class AppFeatureModule : AppCoreAndroidPlugin() {
    override fun configureDependencies(scope: DependencyHandlerScope, target: Project) {
        super.configureDependencies(scope, target)
        scope.run {
            // any libraries that should be included in all feature modules here
        }
    }
}