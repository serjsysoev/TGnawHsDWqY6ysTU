package org.jetbrains.reflekt.plugin.generation.ir

import org.jetbrains.reflekt.plugin.utils.Util.log

import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.backend.common.lower.*
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.ir.IrStatement
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI
import org.jetbrains.kotlin.ir.builders.*
import org.jetbrains.kotlin.ir.declarations.*
import org.jetbrains.kotlin.ir.util.*
import org.jetbrains.kotlin.js.descriptorUtils.nameIfStandardType
import org.jetbrains.kotlin.types.KotlinType
import org.jetbrains.kotlin.types.isNullable

/* Replaces Reflekt invoke calls with their results */
class ReflektIrTransformer(
    private val pluginContext: IrPluginContext,
    private val messageCollector: MessageCollector? = null,
) : BaseReflektIrTransformer(messageCollector) {

    @ObsoleteDescriptorBasedAPI
    override fun visitClassNew(declaration: IrClass): IrStatement {
        if (declaration.isData) {
            var shallowSize = 0
            messageCollector?.log("[IR] REFLEKT CALL: ;") // TODO
            declaration.declarations.forEach { innerDeclaration ->
                if (innerDeclaration is IrProperty) {
                    val type = innerDeclaration.descriptor.type
                    shallowSize += type.getSize()
                }
            }

            val functions = declaration.declarations.filterIsInstance<IrFunction>()
            val shallowSizeFunction = functions.first { !it.name.isSpecial && it.name.identifier == "shallowSize" }
            shallowSizeFunction.body = DeclarationIrBuilder(pluginContext, shallowSizeFunction.symbol).irBlockBody {
                val returnValue = irInt(shallowSize)
                +irReturn(returnValue)
            }
        }
        val test = super.visitClassNew(declaration)
        println(test.dump())
        return test
    }
}

// https://kotlinlang.org/docs/basic-types.html
// Those types (if non-nullable) compile to primitives.
// Values taken from https://www.baeldung.com/jvm-measuring-object-sizes for HotSpot 64-bit running with heap size <= 32gb.
private fun KotlinType.getSize(): Int = when (nameIfStandardType?.takeIf { !isNullable() }?.identifier) { // if nullable type won't be converted to primitive
    "Byte" -> 1
    "Boolean" -> 1
    "Short" -> 2
    "Char" -> 2
    "Int" -> 4
    "Float" -> 4
    "Long" -> 8
    "Double" -> 8
    else -> 4 // reference size for 32-bit JVM or 64-bit JVM with compressed oops
}
