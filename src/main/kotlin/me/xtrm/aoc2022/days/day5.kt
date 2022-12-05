package me.xtrm.aoc2022.days

import me.xtrm.aoc2022.getInput

// Part 2 toggle
@Suppress("unused")
internal const val retainOrder = true

fun main() {
    val input = getInput(day = 5)

    val cratesStr = input.takeWhile { it.isNotBlank() }
        // Normalize the input
        .map { if (it.startsWith(" ".repeat(4))) "[-]${it.substring(3)}" else it }
        .map { it.replace(" ".repeat(4), " [-]") }

    val crateList = mutableListOf<MutableList<Char>>()
    cratesStr.forEachIndexed { i, s ->
        if (i == cratesStr.size - 1) return@forEachIndexed

        val data = s.trimEnd().split(" ")
        data.filter { it.isNotEmpty() }.forEachIndexed { index, item ->
            val char = item.replace("[", "").replace("]", "").toCharArray()[0]
            if (crateList.size - 1 < index)
                crateList.add(mutableListOf())

            if (char != '-')
                crateList[index].add(char)
        }
    }
    crateList.forEach { it.reverse() }

    val instructions = input.dropWhile { it.isNotBlank() }.drop(1)
    instructions.forEach { instruction ->
        val data = instruction.split(" ")
        val from = data[3].toInt() - 1
        val to = data[5].toInt() - 1
        val amount = data[1].toInt()

        if (retainOrder) {
            val store = mutableListOf<Char>()
            for (i in 0 until amount) {
                store.add(crateList[from].removeLast())
            }
            store.reversed().forEach { item ->
                crateList[to].add(item)
            }
        } else {
            for (i in 0 until amount) {
                val item = crateList[from].removeLast()
                crateList[to].add(item)
            }
        }
    }

    crateList.map { it.removeLast() }.joinToString("").let { println(it) }
}