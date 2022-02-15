package org.jetbrains.reflektTask.plugin

import org.jetbrains.reflektTask.plugin.generation.ir.ReflektTaskIrGenerationExtension
import org.jetbrains.reflektTask.plugin.utils.PluginConfig
import org.jetbrains.reflektTask.plugin.utils.Util.log
import org.jetbrains.reflektTask.plugin.utils.Util.messageCollector

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.com.intellij.mock.MockProject
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension
import org.jetbrains.reflektTask.plugin.resolve.ReflektTaskResolveExtension

/**
 * Registers the plugin and applies it to the project.
 *
 * @property isTestConfiguration indicates if the plugin is used in tests
 */
@Suppress("unused")
@AutoService(ComponentRegistrar::class)
class ReflektTaskComponentRegistrar(private val isTestConfiguration: Boolean = false) : ComponentRegistrar {
    /**
     * Tne main plugin's function that parses all compiler arguments and runs all Kotlin compiler's extensions.
     *
     * @param project current project
     * @param configuration current compiler configuration, also stores all parsed options form the [ReflektTaskCommandLineProcessor]
     */
    override fun registerProjectComponents(
        project: MockProject,
        configuration: CompilerConfiguration,
    ) {
        val config = PluginConfig(configuration, isTestConfiguration = isTestConfiguration)
        configuration.messageCollector.log("PROJECT FILE PATH: ${project.projectFilePath}")

        if (!config.enabled) return

        SyntheticResolveExtension.registerExtension(
            project,
            ReflektTaskResolveExtension(messageCollector = config.messageCollector)
        )
        IrGenerationExtension.registerExtension(
            project,
            ReflektTaskIrGenerationExtension(messageCollector = config.messageCollector)
        )
    }
}
