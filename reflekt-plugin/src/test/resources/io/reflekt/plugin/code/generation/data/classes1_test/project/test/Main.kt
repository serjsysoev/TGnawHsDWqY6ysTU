package io.reflekt.codegen.test

import io.reflekt.Reflekt

fun main() {
    val classes = Reflekt.classes().withSupertypes(BInterfaceTest::class)
}
