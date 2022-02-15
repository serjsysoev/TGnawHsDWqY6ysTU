package org.jetbrains.reflektTask.plugin.utils

import org.jetbrains.reflektTask.util.Util.PLUGIN_ID
import org.jetbrains.kotlin.config.CompilerConfigurationKey

/**
 * Kotlin compiler configuration keys for ReflektTask plugin.
 *
 * @property ENABLED indicates if the plugin is enabled
 */
internal object Keys {
    val ENABLED = CompilerConfigurationKey<Boolean>("$PLUGIN_ID.enabled")
}
