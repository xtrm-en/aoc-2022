package me.xtrm.aoc2022.days

import me.xtrm.aoc2022.getInput
import java.util.stream.IntStream
import kotlin.streams.toList

// Part 2 toggle
@Suppress("unused")
internal const val checkSingleOverlap = true

fun main() {
    val input = getInput(day = 4)

    var overlaps = 0
    for (line in input) {
        val lines = line.split(",")
        val firstData = lines[0].split("-")
        val secondData = lines[1].split("-")
        val firstStream = IntStream.range(firstData[0].toInt(), firstData[1].toInt() + 1).toList()
        val secondStream = IntStream.range(secondData[0].toInt(), secondData[1].toInt() + 1).toList()

        val doesOverlap: Boolean = if (checkSingleOverlap) {
            firstStream.any { it in secondStream }
        } else {
            firstStream.containsAll(secondStream) || secondStream.containsAll(firstStream)
        }
        overlaps += if (doesOverlap) 1 else 0
        println("Overlaps: $doesOverlap")
    }

    println("Total: $overlaps")

}