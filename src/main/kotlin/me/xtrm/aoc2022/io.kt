package me.xtrm.aoc2022

private class Dummy

internal fun getInput(day: Int): List<String> =
    Dummy::class.java.getResource("/inputs/day$day.txt")!!
        .openStream().reader().readLines()
