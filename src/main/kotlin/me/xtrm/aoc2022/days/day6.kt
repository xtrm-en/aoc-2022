package me.xtrm.aoc2022.days

import me.xtrm.aoc2022.getInput

// Part 2 toggle
@Suppress("unused")
internal const val lookForMessage = true

fun main() {
    val input = getInput(day = 6)[0]

    val lookFor = if (lookForMessage) 14 else 4

    var pointer = lookFor - 1
    while (pointer < input.length) {
        val lookingAt = input.substring(pointer - (lookFor - 1), pointer + 1)

        val hasRepetition = lookingAt.chars().anyMatch { c ->
            lookingAt.chars().filter { c2 -> c2 == c }.count() > 1
        }
        if (!hasRepetition)
            break

        pointer++
    }

    println("Got first marker after ${pointer + 1} characters")
}