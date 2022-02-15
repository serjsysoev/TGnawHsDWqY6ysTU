package org.jetbrains.reflektTask.util

object Util {
    /** Global constant with plugin identifier */
    const val PLUGIN_ID = "org.jetbrains.reflektTask"
    const val GRADLE_GROUP_ID = "org.jetbrains.reflektTask"

    /**
     * Just needs to be consistent with the artifactId in reflektTask-plugin build.gradle.kts#publishJar
     */
    const val GRADLE_ARTIFACT_ID = "reflektTask-plugin"
    const val VERSION = "1.5.31"
    val ENABLED_OPTION_INFO = MyCliOption(
        name = "enabled",
        valueDescription = "<true|false>",
        description = "Whether to enable the ReflektTask plugin or not",
    )

    /**
     * @property name
     * @property valueDescription
     * @property description
     */
    data class MyCliOption(
        val name: String,
        val valueDescription: String,
        val description: String,
    )
}
