package io.reflekt.test

import io.reflekt.Reflekt

fun main() {
    val classes = Reflekt.classes().withAnnotations<B3>(FirstAnnotationTest::class).withSupertype<B3>()
}
