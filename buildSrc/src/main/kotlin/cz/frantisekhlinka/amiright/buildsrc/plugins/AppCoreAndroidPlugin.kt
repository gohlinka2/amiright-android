package cz.frantisekhlinka.amiright.buildsrc.plugins

import cz.frantisekhlinka.amiright.buildsrc.plugins.abstr.BaseAndroidGradlePlugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope

/**
 * Core plugin for an android module in our app.
 */
open class AppCoreAndroidPlugin : BaseAndroidGradlePlugin() {
    override fun configureDependencies(scope: DependencyHandlerScope, target: Project) {
        super.configureDependencies(scope, target)
        scope.run {
            // any libraries that should be included in all modules (such as logging)
        }
    }
}