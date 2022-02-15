package org.jetbrains.reflekt.plugin.resolve

import org.jetbrains.kotlin.descriptors.*
import org.jetbrains.kotlin.descriptors.annotations.Annotations
import org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.descriptorUtil.builtIns
import org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension

open class ReflektResolveExtension : SyntheticResolveExtension {
    override fun getSyntheticFunctionNames(thisDescriptor: ClassDescriptor): List<Name> =
        if (thisDescriptor.isData) listOf(Name.identifier("shallowSize")) else emptyList()


    override fun generateSyntheticMethods(
        thisDescriptor: ClassDescriptor,
        name: Name,
        bindingContext: BindingContext,
        fromSupertypes: List<SimpleFunctionDescriptor>,
        result: MutableCollection<SimpleFunctionDescriptor>
    ) {
        if (thisDescriptor.isData && name.identifier == "shallowSize") {
            val function = doCreateSerializerFunction(thisDescriptor, Name.identifier("shallowSize"))
            result.add(function)
        }
    }

    private fun doCreateSerializerFunction(
        companionDescriptor: ClassDescriptor,
        name: Name
    ): SimpleFunctionDescriptor {
        val functionDescriptor = SimpleFunctionDescriptorImpl.create(
            companionDescriptor, Annotations.EMPTY, name, CallableMemberDescriptor.Kind.SYNTHESIZED, companionDescriptor.source
        )

        functionDescriptor.initialize(
            null,
            companionDescriptor.thisAsReceiverParameter,
            emptyList(),
            emptyList(),
            companionDescriptor.builtIns.intType,
            Modality.FINAL,
            DescriptorVisibilities.PUBLIC
        )

        return functionDescriptor
    }
}
