@file:Suppress("KDOC_WITHOUT_RETURN_TAG")

package org.jetbrains.reflektTask.plugin.utils

import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageLocation
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.cli.common.messages.MessageRenderer
import org.jetbrains.kotlin.cli.common.messages.PrintingMessageCollector
import org.jetbrains.kotlin.config.CompilerConfiguration

import java.io.File
import java.io.PrintStream

/**
 * Common functions for the plugin.
 *
 * @property messageCollector get [MessageCollector] from the [CompilerConfiguration]
 */
object Util {
    val CompilerConfiguration.messageCollector: MessageCollector
        get() = this.get(
            CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY,
            MessageCollector.NONE,
        )

    /**
     * Creates new empty file for the new instance of [MessageCollector].
     *
     * @param filePath
     */
    fun CompilerConfiguration.initMessageCollector(filePath: String) {
        val file = File(filePath)
        file.createNewFile()
        this.put(
            CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY,
            PrintingMessageCollector(PrintStream(file.outputStream()), MessageRenderer.PLAIN_FULL_PATHS, true),
        )
    }

    /**
     * Logs new message by [MessageCollector].
     *
     * @param message
     */
    fun MessageCollector.log(message: String) {
        this.report(
            CompilerMessageSeverity.LOGGING,
            "ReflektTask: $message",
            CompilerMessageLocation.create(null),
        )
    }
}
