import org.gradle.api.Plugin
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.add

fun PluginContainer.kotlin(module: String): Plugin<Any> = apply("org.jetbrains.kotlin.$module")

/**
 * Adds a dependency to the 'implementation' configuration.
 *
 * @param dependencyNotation notation for the dependency to be added.
 * @return The dependency.
 *
 * @see [DependencyHandler.add]
 */
fun DependencyHandlerScope.implementation(dependencyNotation: Any): Dependency? =
    add("implementation", dependencyNotation)

/**
 * Adds a dependency to the 'api' configuration.
 *
 * @param dependencyNotation notation for the dependency to be added.
 * @return The dependency.
 *
 * @see [DependencyHandler.add]
 */
fun DependencyHandler.api(dependencyNotation: Any): Dependency? =
    add("api", dependencyNotation)

/**
 * DSL for including dependencies of libraries that are part of a version platform, such as Firebase or Compose.
 * Improves structuring of the build script and automatically adds the dependency on the BOM.
 */
fun DependencyHandlerScope.platform(bomDependencyNotation: String, configure: PlatformDependencyHandlerScope.() -> Unit) {
    val scope = PlatformDependencyHandlerScope(bomDependencyNotation)
    scope.configure()
    scope.applyTo(this)
}

/**
 * Scope for adding dependencies to the [platform] DSL.
 */
data class PlatformDependencyHandlerScope(
    val bomDependencyNotation: String
) {

    private val implementations = mutableListOf<String>()
    private val apis = mutableListOf<String>()

    fun implementation(dependencyNotation: String) {
        implementations.add(dependencyNotation)
    }

    fun api(dependencyNotation: String) {
        apis.add(dependencyNotation)
    }

    fun applyTo(dependencyHandlerScope: DependencyHandlerScope) {
        dependencyHandlerScope.run {
            if (implementations.isNotEmpty() || apis.isNotEmpty()) {
                // include the bom if any dependencies have been added
                if (apis.isNotEmpty()) {
                    api(platform(bomDependencyNotation))
                } else {
                    implementation(platform(bomDependencyNotation))
                }

                implementations.forEach { implementation(it) }
                apis.forEach { api(it) }
            }
        }
    }
}