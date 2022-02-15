package org.jetbrains.reflektTask.plugin

import org.jetbrains.reflektTask.plugin.utils.Keys
import org.jetbrains.reflektTask.util.Util.ENABLED_OPTION_INFO
import org.jetbrains.reflektTask.util.Util.PLUGIN_ID

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.compiler.plugin.*
import org.jetbrains.kotlin.config.CompilerConfiguration

/**
 * Handles command line arguments and transfers them into the kotlin compiler plugin.
 *
 * @property pluginId the compiler plugin id. Just needs to be consistent
 *  with the key for ReflektTaskSubPlugin.getCompilerPluginId from the gradle-plugin module.
 * @property pluginOptions the collection of the command line options for the kotlin compiler plugin.
 *  Should match up with the options returned from the ReflektTaskSubPlugin.applyToCompilation in the gradle-plugin module.
 *  Should also have matching 'when'-branches for each option in the [processOption] function
 */
@AutoService(CommandLineProcessor::class)
class ReflektTaskCommandLineProcessor : CommandLineProcessor {
    override val pluginId: String = PLUGIN_ID
    override val pluginOptions: Collection<CliOption> = listOf(ENABLED_OPTION)

    /**
     * Processes the compiler plugin command line options and puts them to the [CompilerConfiguration].
     *
     * @param option the current command line option that should be handled
     * @param value of the current option, that will be converted into the right type required by [configuration]
     * @param configuration the kotlin compiler configuration that should be updated,
     *  the option will be added to the configuration
     */
    override fun processOption(
        option: AbstractCliOption,
        value: String,
        configuration: CompilerConfiguration,
    ) = when (option) {
        ENABLED_OPTION -> configuration.put(Keys.ENABLED, value.toBoolean())
        else -> error("Unexpected config option ${option.optionName}")
    }

    /**
     * Possible kotlin compiler command line options.
     *
     * @property ENABLED_OPTION indicates if the plugin is enabled
     */
    companion object {
        val ENABLED_OPTION =
            CliOption(
                optionName = ENABLED_OPTION_INFO.name,
                valueDescription = ENABLED_OPTION_INFO.valueDescription,
                description = ENABLED_OPTION_INFO.description,
            )
    }
}
