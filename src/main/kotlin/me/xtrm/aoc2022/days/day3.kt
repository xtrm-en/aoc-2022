package me.xtrm.aoc2022.days

import me.xtrm.aoc2022.getInput

// Part 2 toggle
private const val groupElves = true

fun main() {
    val input = getInput(day = 3)

    val priorityString = "abcdefghijklmnopqrstuvwxyz".repeat(2).mapIndexed { index, c ->
        if (index > 25) c.uppercase() else c
    }.joinToString("")

    fun getPriority(c: Char): Int =
        priorityString.indexOf(c) + 1

    var sum = 0
    if (groupElves) {
        for (i in 0 until (input.size / 3)) {
            val startIndex = i * 3
            print("Group $startIndex -> ${startIndex + 2}")
            val sublist = input.subList(startIndex, startIndex + 3)

            val char = sublist.map { it.toSet() }
                .reduce { acc, set -> acc.intersect(set) }
                .toCharArray()[0]

            print(" | $char")
            println()
            sum += getPriority(char)
        }
    } else {
        for (line in input) {
            val first = line.substring(0, line.length / 2)
            val second = line.substring(line.length / 2)

            print("$first / $second")

            val repeat = first.first { it in second }
            print(" | $repeat")

            val priority = getPriority(repeat)
            print(" | $priority")

            println()
            sum += priority
        }
    }
    println("Sum: $sum")
}