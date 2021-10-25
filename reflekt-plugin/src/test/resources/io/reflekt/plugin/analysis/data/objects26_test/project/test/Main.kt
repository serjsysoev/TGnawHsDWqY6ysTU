package io.reflekt.test

import io.reflekt.Reflekt

fun main() {
    val objects = Reflekt.objects().withSuperTypes(A1::class).withAnnotations<A1>(FirstAnnotationTest::class, SecondAnnotationTest::class)
}
