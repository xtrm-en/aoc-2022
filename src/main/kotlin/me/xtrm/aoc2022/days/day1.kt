package me.xtrm.aoc2022.days

import me.xtrm.aoc2022.getInput

fun main() {
    val input = getInput(day = 1)

    // using a list instead of a map since the elf "identifier" is 0-n
    val elves = mutableListOf<Int>()
    elves.add(0)
    var index = 0

    input.forEach { line ->
        if (line.isBlank()) {
            index++
            elves.add(0)
        } else {
            elves[index] += line.toInt()
        }
    }

    println("Upmost: " + elves.sorted()[elves.size - 1])
    println("Top 3: " + elves.sorted().takeLast(3).sum())
}