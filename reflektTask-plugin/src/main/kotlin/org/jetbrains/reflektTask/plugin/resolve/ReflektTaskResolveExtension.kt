package org.jetbrains.reflektTask.plugin.resolve

import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.descriptors.*
import org.jetbrains.kotlin.descriptors.annotations.Annotations
import org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.descriptorUtil.builtIns
import org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension
import org.jetbrains.reflektTask.plugin.utils.Util.log

/**
 * Generates synthetic `shallowSize` method for each data class
 */
open class ReflektTaskResolveExtension(
    private val messageCollector: MessageCollector? = null,
) : SyntheticResolveExtension {

    override fun getSyntheticFunctionNames(thisDescriptor: ClassDescriptor): List<Name> =
        if (thisDescriptor.isData) listOf(shallowSizeFunctionName) else emptyList()


    override fun generateSyntheticMethods(
        thisDescriptor: ClassDescriptor,
        name: Name,
        bindingContext: BindingContext,
        fromSupertypes: List<SimpleFunctionDescriptor>,
        result: MutableCollection<SimpleFunctionDescriptor>
    ) {
        if (thisDescriptor.isData && name == shallowSizeFunctionName) {
            messageCollector?.log(
                "REFLEKT_TASK generateSyntheticMethods: generating shallowSize for ${thisDescriptor.name.asString()};"
            )
            val function = generateShallowSizeFunction(thisDescriptor, shallowSizeFunctionName)
            result.add(function)
        }
    }

    private fun generateShallowSizeFunction(
        classDescriptor: ClassDescriptor,
        name: Name
    ) = SimpleFunctionDescriptorImpl.create(
        classDescriptor,
        Annotations.EMPTY,
        name,
        CallableMemberDescriptor.Kind.SYNTHESIZED,
        classDescriptor.source
    ).apply {
        initialize(
            null,
            classDescriptor.thisAsReceiverParameter,
            emptyList(),
            emptyList(),
            classDescriptor.builtIns.intType,
            Modality.FINAL,
            DescriptorVisibilities.PUBLIC
        )
    }

    companion object {
        val shallowSizeFunctionName = Name.identifier("shallowSize")
    }
}
