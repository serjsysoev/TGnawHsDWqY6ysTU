package org.jetbrains.reflekt.plugin

import org.jetbrains.reflekt.plugin.generation.ir.ReflektIrGenerationExtension
import org.jetbrains.reflekt.plugin.utils.PluginConfig
import org.jetbrains.reflekt.plugin.utils.Util.log
import org.jetbrains.reflekt.plugin.utils.Util.messageCollector

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.com.intellij.mock.MockProject
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension
import org.jetbrains.reflekt.plugin.resolve.ReflektResolveExtension

/**
 * Registers the plugin and applies it to the project.
 *
 * @property isTestConfiguration indicates if the plugin is used in tests
 */
@AutoService(ComponentRegistrar::class)
class ReflektComponentRegistrar(private val isTestConfiguration: Boolean = false) : ComponentRegistrar {
    /**
     * Tne main plugin's function that parses all compiler arguments and runs all Kotlin compiler's extensions.
     *
     * @param project current project
     * @param configuration current compiler configuration, also stores all parsed options form the [ReflektCommandLineProcessor]
     */
    override fun registerProjectComponents(
        project: MockProject,
        configuration: CompilerConfiguration,
    ) {
        val config = PluginConfig(configuration, isTestConfiguration = isTestConfiguration)
        configuration.messageCollector.log("PROJECT FILE PATH: ${project.projectFilePath}")

        IrGenerationExtension.registerExtension(
            project,
            ReflektIrGenerationExtension(messageCollector = config.messageCollector)
        )
        SyntheticResolveExtension.registerExtension(
            project,
            ReflektResolveExtension()
        )
    }
}
