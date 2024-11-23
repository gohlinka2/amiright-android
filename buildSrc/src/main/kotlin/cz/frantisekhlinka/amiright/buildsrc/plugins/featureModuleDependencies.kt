import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.project

/**
 * Dependencies that are common for all feature modules + are shared with the
 * coreFront module, which provides utilities for the feature modules.
 *
 * It does not include testing libraries, as those are only needed for the feature modules.
 */
fun DependencyHandlerScope.featureModuleDependencies() {
    implementation(project(Modules.coreBack))
    implementation(project(Modules.coreUi))

    implementation(Libraries.androidCoreKtx)
    implementation(Libraries.lifecycleKtx)
    implementation(Libraries.activityCompose)
    implementation(Libraries.koinAndroidCompose)
    implementation(Libraries.koinAndroidComposeNavigation)
    implementation(Libraries.composeNavigation)

    compose {
        implementation(Libraries.Compose.compose)
        implementation(Libraries.Compose.graphics)
        implementation(Libraries.Compose.toolingPreview)
        implementation(Libraries.Compose.material)
    }

    debugImplementation(Libraries.Compose.tooling)
}