import org.gradle.kotlin.dsl.DependencyHandlerScope

/**
 * DSL for including dependencies of Firebase libraries.
 * Improves structuring of the build script and automatically adds the dependency on the BOM.
 */
fun DependencyHandlerScope.firebase(configure: PlatformDependencyHandlerScope.() -> Unit) =
    platform(Libraries.Firebase.bom, configure)

/**
 * DSL for including dependencies of Compose libraries.
 * Improves structuring of the build script and automatically adds the dependency on the BOM.
 */
fun DependencyHandlerScope.compose(configure: PlatformDependencyHandlerScope.() -> Unit) =
    platform(Libraries.Compose.bom, configure)