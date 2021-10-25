package io.reflekt.test

import io.reflekt.Reflekt

fun main() {
    val classes = Reflekt.classes().withSuperTypes(BInterfaceTest::class).withAnnotations<BInterfaceTest>(SecondAnnotationTest::class)
}
