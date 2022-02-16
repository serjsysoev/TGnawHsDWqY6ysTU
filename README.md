# Задание для практики "Reflekt: a plugin for Kotlin compiler for compile-time reflection"

## Задание

Реализовать плагин для Kotlin компилятора, который для всех data-классов в проекте во время компиляции считает shallow
size (сумму размеров всех полей класса в байтах) и генерирует .kt файлик с набором функций-расширений для этих классов,
возвращающих этот размер.

То есть, если код содержит два data-класса A и B, необходимо сгенерировать .kt файлик с функциями fun A.shallowSize() =
16 и fun B.shallowSize() = 24 (сами значения должны быть посчитаны во время компиляции). Для простоты можно считать, что
в проекте все классы имеют различные имена.

При реализации можно использовать как библиотеки для написания плагинов, например arrow-meta, так и писать плагин
напрямую.

**Важно**: для упрощения задания генерируется исходный код, то есть .kt файл, а не bytecode или промежуточное
представление (IR), но никто не ограничивает вас в этом. Также, при желании можно добавить различного рода фильтрацию -
например, генерировать такие функции только для data-классов, помеченных специальной аннотацией.

Плагин должен работать с одной из версий Kotlin, но не ниже, чем 1.5.0 (на ваш выбор).

## Решение

### Quickstart

Чтобы собрать плагин, необходимо исполнить ```./gradlew build```

Затем нужно опубликовать его в локальный maven: ```./gradlew publishToMavenLocal```

Инструкции по использованию практически в точности повторяют инструкции для Reflekt:

Добавить в build.gradle.kts

```
import org.jetbrains.reflektTask.plugin.reflektTask

plugins {
    kotlin("jvm") version "1.5.31" apply true
    id("org.jetbrains.reflektTask") version "1.5.31" apply true
}

repositories {
    mavenLocal()
    mavenCentral()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        useIR = true
        languageVersion = "1.5"
        apiVersion = "1.5"
        jvmTarget = "11"
        incremental = false
    }
}

reflektTask {
    // Enable or disable ReflektTask plugin
    enabled = true
}
```

Добавить в settings.gradle.kts

```
pluginManagement {
    repositories {
        maven(url = uri("https://plugins.gradle.org/m2/"))
        mavenLocal()
    }
}
```

**Plugin таргетирует Kotlin 1.5.31**

### Внутренности

Написаны два плагина для компилятора: IrGenerationExtension и SyntheticResolveExtension. Вместе они добавляют в IR всех
data class'ов метод shallowSize. Плагин должен работать на всех платформах, однако был протестирован только на JVM и
результаты shallowSize имеют смысл только для JVM.

SyntheticResolveExtension добавляет метод shallowSize без тела всем data class'ам на ранних стадиях компиляции, чтобы
код прошёл дальнейшую валидацию и type checking.

IrGenerationExtension добавляет всем методам shallowSize тело до оптимизации и генерации байткода/транспиляции в JS, но
после валидации и typechecking'а.

На этой диаграмме отмечено в какой момент запускается IrGenerationExtension (plugins):
![](Compiler%20diagram.png)

### Методика подсчёта shallow size

Размеры полей посчитаны для 32-битной HotSpot JVM (или 64-битной с heap size <= 32gb).

Non-nullable Byte, Boolean, Short, Char, Int, Float, Long, Double компилируются в примитивные типы, размеры в байтах для
них 1, 1, 2, 2, 4, 4, 8, 8 соответственно. Все остальные поля окажутся ссылками. Размер указателей/ссылок принят за 4
байта.

### Инкрементальная компиляция не работает

Я пока не понял почему так происходит. Если скомпилировать ```fun main() { dataClass.shallowSize() }```, а после этого
заменить код на ```fun main() { regularClass.shallowSize() }```, код не должен скомпилироваться, однако он компилируется
и запускается...

### Подсветка кода не работает

Для того чтобы IDEA правильно подсвечивала .shallowSize() нужно либо написать отдельный плагин для IDEA, либо дождаться
Kotlin ~1.7, где
[возможно добавится](https://kotlinlang.slack.com/archives/C7L3JB43G/p1637758246160700?thread_ts=1637756576.159300&cid=C7L3JB43G)
генерация подсветки по generated synthetic descriptors. Несмотря на то, что подсветки нет, можно написать
dataClass.shallowSize() и запустить - код скомпилируется.

### Ссылки

В написании плагина очень помогли:

* [Reflekt](https://github.com/JetBrains-Research/reflekt) (код плагина базируется на кодовой базе Reflekt)
* [Блогпосты](https://blog.bnorm.dev/writing-your-second-compiler-plugin-part-1) by Brian Norman о написании
  IrGenerationExtension
* [Лекция](https://youtu.be/hDs_LyBmhtc) by Anastasia Birillo о рефлексии в Kotlin
* [Презентация](https://youtu.be/w-GMlaziIyo) by Kevin Most "Writing Your First Kotlin Compiler Plugin by Kevin Most"
* [Kotlin source code](https://github.com/JetBrains/kotlin) (особенно полезны примеры extension'ов)
* Kotlinlang slack
* [Презентация](https://assets.ctfassets.net/2grufn031spf/5vS9hELJ9KFPPfYracUPjO/cc34a6b7b06aea467d688355f4983a6d/Andrey_Shikov_Magiya_rasshireniy_kompilyatora_Kotlin_2020_06_23_15_20_39.pdf)
by Andrei Shikov "Магия расширений компилятора Kotlin"
* [How to debug kotlin compiler plugin](https://github.com/Foso/MpApt/wiki/How-to-debug-Kotlin-Compiler-Plugin)