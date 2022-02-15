package org.jetbrains.reflektTask.plugin.utils

import org.jetbrains.reflektTask.plugin.utils.Util.initMessageCollector
import org.jetbrains.reflektTask.plugin.utils.Util.log
import org.jetbrains.reflektTask.plugin.utils.Util.messageCollector

import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.config.CompilerConfiguration

/**
 * Parses and stores the command line arguments from the plugin.
 *
 * @param configuration the current Kotlin compiler configuration or null
 * @param logFilePath path to the file with logs. By default is pathToKotlin/daemon/reflektTask-log.log
 * @param isTestConfiguration indicates if the plugin is used in tests
 *
 * @property enabled indicates if the plugin is enabled
 * @property messageCollector [MessageCollector] for logs or null
 */
class PluginConfig(
    configuration: CompilerConfiguration?,
    logFilePath: String = "reflektTask-log.log",
    isTestConfiguration: Boolean = false,
) {
    var enabled = isTestConfiguration
    var messageCollector: MessageCollector? = null

    init {
        configuration?.let {
            configuration.initMessageCollector(logFilePath)
            messageCollector = configuration.messageCollector

            enabled = configuration[Keys.ENABLED] ?: false

            messageCollector?.logConfiguration()
        }
    }

    /**
     * Builds the pretty string of the current configuration.
     *
     * @return [StringBuilder]
     */
    private fun prettyString() = buildString {
        append("REFLEKT_TASK CONFIGURATION:\n")
        append("ENABLED: $enabled\n")
        append("_____________________________________________\n")
    }

    /**
     * Builds and logs pretty string of the current configuration.
     */
    private fun MessageCollector.logConfiguration() = this.log(prettyString())
}
