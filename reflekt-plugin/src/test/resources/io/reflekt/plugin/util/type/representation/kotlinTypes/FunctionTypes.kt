package io.reflekt.plugin.util.type.representation.kotlinTypes

fun main() {
    fooWithType<() -> Unit>("function0_unit_test")
    fooWithType<() -> Int>("function0_simple_type_test")
    fooWithType<() -> MyClass>("function0_user_type_test")
    fooWithType<() -> List<Any>>("function0_list_test")
    fooWithType<() -> MyTypeAlias<Any>>("function0_user_alias_type_test")
    fooWithType<() -> MyGenericType<String>>("function0_generic_simple_type_test")
    fooWithType<() -> MyGenericType<in String>>("function0_generic_with_in_test")
    fooWithType<() -> MyGenericType<out String>>("function0_generic_with_out_test")
    fooWithType<() -> MyObject>("function0_user_object_test")
    fooWithType<() -> MyInheritedType>("function0_inherited_type_test")
    fooWithType<() -> List<*>>("function0_star_type_test")
    fooWithType<() -> Any?>("function0_same_type_with_bound_test")
    fooWithType<() -> MyGenericType<*>>("function0_generic_star_type_test")

    // TODO: does not work now (bugs in Kotlin)
    fooWithType<() -> Nothing>("function0_nothing_test")
    fooWithType<() -> MutableCollection<List<Array<Any>>>>("function0_complex_type_test")

    fooWithType<() -> Unit?>("function0_unit_nullable_test")
    fooWithType<() -> Int?>("function0_simple_type_nullable_test")
    fooWithType<() -> MyClass?>("function0_user_type_nullable_test")
    fooWithType<() -> List<Any?>?>("function0_list_nullable_all_test")
    fooWithType<() -> List<Any>?>("function0_list_nullable_test")
    fooWithType<() -> List<Any?>>("function0_list_nullable_argument_test")
    fooWithType<() -> MyTypeAlias<Any?>?>("function0_user_alias_type_nullable_test")
    fooWithType<() -> MyGenericType<String>?>("function0_generic_simple_type_nullable_test")
    fooWithType<() -> MyGenericType<in String>?>("function0_generic_with_in_nullable_test")
    fooWithType<() -> MyGenericType<in String>?>("function0_generic_simple_type_nullable_test")
    fooWithType<() -> MyGenericType<out String>?>("function0_generic_with_out_nullable_test")
    fooWithType<() -> MyObject?>("function0_user_object_nullable_test")
    fooWithType<() -> MyInheritedType?>("function0_inherited_type_nullable_test")
    fooWithType<() -> List<*>?>("function0_star_type_nullable_test")
    fooWithType<() -> MyGenericType<*>?>("function0_generic_star_type_nullable_test")

    fooWithType<(List<Set<Any?>>?) -> List<*>>("function1_test")
    fooWithType<(Int, Iterable<*>) -> Unit>("function2_test")
}
