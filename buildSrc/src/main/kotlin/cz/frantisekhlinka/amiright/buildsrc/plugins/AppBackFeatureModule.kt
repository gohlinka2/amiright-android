package cz.frantisekhlinka.amiright.buildsrc.plugins

import Libraries
import Modules
import implementation
import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.project

/**
 * A plugin for a feature module that does not contain UI. Typically used for implementation
 * of the data layer or encapsulation of specific functionality.
 */
open class AppBackFeatureModule : AppCoreAndroidPlugin() {
    override fun configureDependencies(scope: DependencyHandlerScope, target: Project) {
        super.configureDependencies(scope, target)
        scope.run {
            implementation(project(Modules.coreBack))

            implementation(Libraries.koinCore)
        }
    }
}