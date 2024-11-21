package cz.frantisekhlinka.amiright.buildsrc.plugins.abstr

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.dependencies

/**
 * Base scaffold for creating a gradle plugin for a module.
 */
abstract class BaseGradlePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            configurePlugins(plugins)
            dependencies { configureDependencies(this, target) }
        }
    }

    /**
     * Use the [container] to configure the plugins block.
     */
    protected open fun configurePlugins(container: PluginContainer) {
        // empty
    }

    /**
     * Use the [scope] to configure the dependencies block, and [target] if you need to access the target project.
     */
    protected open fun configureDependencies(scope: DependencyHandlerScope, target: Project) {
        // empty
    }
}
