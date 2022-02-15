package org.jetbrains.reflektTask.plugin

import org.gradle.api.Project

/**
 * Users can configure this extension in their build.gradle like this:
 * reflektTask {
 *   enabled = false/true
 * }
 */
internal val Project.reflektTask: ReflektTaskGradleExtension
    get() = project.extensions.findByType(ReflektTaskGradleExtension::class.java) ?: run {
        extensions.create("reflektTask", ReflektTaskGradleExtension::class.java)
    }

/**
 * Gradle extension class containing the configuration information for the plugin
 */
open class ReflektTaskGradleExtension {
    /** If false, this plugin won't actually be applied */
    var enabled: Boolean = true
}

/**
 * ReflektTask Generator configuration extension.
 *
 * @param configure
 */
@Suppress("unused")
fun Project.reflektTask(configure: ReflektTaskGradleExtension.() -> Unit) {
    reflektTask.apply(configure)
}
