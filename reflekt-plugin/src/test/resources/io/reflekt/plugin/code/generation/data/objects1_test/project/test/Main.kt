package io.reflekt.codegen.test

import io.reflekt.Reflekt

fun main() {
    val objects = Reflekt.objects().withSuperTypes(AInterfaceTest::class)
}
