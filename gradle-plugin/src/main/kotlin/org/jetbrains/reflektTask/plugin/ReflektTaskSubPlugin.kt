package org.jetbrains.reflektTask.plugin

import org.jetbrains.reflektTask.util.Util.ENABLED_OPTION_INFO
import org.jetbrains.reflektTask.util.Util.GRADLE_ARTIFACT_ID
import org.jetbrains.reflektTask.util.Util.GRADLE_GROUP_ID
import org.jetbrains.reflektTask.util.Util.PLUGIN_ID
import org.jetbrains.reflektTask.util.Util.VERSION

import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.plugin.*

@Suppress("unused")
class ReflektTaskSubPlugin : KotlinCompilerPluginSupportPlugin {
    override fun applyToCompilation(kotlinCompilation: KotlinCompilation<*>): Provider<List<SubpluginOption>> {
        println("ReflektTaskSubPlugin loaded")
        val project = kotlinCompilation.target.project
        val extension = project.reflektTask

        return project.provider {
            listOf(
                SubpluginOption(key = ENABLED_OPTION_INFO.name, value = extension.enabled.toString())
            )
        }
    }

    override fun isApplicable(kotlinCompilation: KotlinCompilation<*>) = true

    /**
     * Just needs to be consistent with the key for ReflektTaskCommandLineProcessor#pluginId
     */
    override fun getCompilerPluginId(): String = PLUGIN_ID

    override fun getPluginArtifact(): SubpluginArtifact = SubpluginArtifact(
        groupId = GRADLE_GROUP_ID,
        artifactId = GRADLE_ARTIFACT_ID,
        version = VERSION,
    )
}
