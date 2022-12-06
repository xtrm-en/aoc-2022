package me.xtrm.aoc2022

private class Dummy

@Suppress("unused")
internal fun getInput(day: Int, test: Boolean = false): List<String> =
    Dummy::class.java.getResource("/inputs/day$day${if (test) ".test" else ""}.txt")!!
        .openStream().reader().readLines().let {
            if (it[it.size - 1].isBlank()) {
                it.subList(0, it.size - 2)
            } else it
        }
