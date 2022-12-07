package me.xtrm.aoc2022.days

import me.xtrm.aoc2022.getInput

data class AOCFile(
    val name: String,
    val size: Int,
    val dir: Boolean,
    val children: MutableList<AOCFile> = mutableListOf(),
    val parent: AOCFile? = null,
) {
    fun totalSize(): Int = size + children.sumOf { it.totalSize() }

    fun find(name: String): AOCFile? {
        if (name == "..") return parent
        if (this.name == name) return this
        return children.firstNotNullOfOrNull { it.find(name) }
    }

    fun all(): List<AOCFile> = children + children.flatMap { it.all() }

    override fun toString(): String {
        return "File(name='$name', size=$size, dir=$dir, children=${children.size}, parent=${parent?.name})"
    }
}

fun main() {
    val input = getInput(day = 7)

    val root = AOCFile("/", 0, true)
    var current = root
    for (line in input) {
        if (line.startsWith("$ ")) {
            if (line.substring(2, 4) == "cd") {
                val target = line.substring(5)
                current = when (target) {
                    "/" -> root
                    ".." -> current.parent!!
                    else -> current.children.firstOrNull { it.name == target }!!
                }
            }
        } else {
            val (data, name) = line.split(" ")
            val dir = data == "dir"
            val file = AOCFile(name, if (dir) 0 else data.toInt(), dir, parent = current)
            current.children += file
        }
    }

    println(
        root.all().filter { it.dir }
            .filter { it.totalSize() <= 100000 }
            .sumOf { it.totalSize() }
    )

    val totalAvailable = 70000000
    val required = 30000000
    val neededFree = totalAvailable - required

    val taken = root.totalSize()

    val smallestBiggestDir = root.all().filter { it.dir }
        .filter { taken - it.totalSize() <= neededFree }
        .minByOrNull { it.totalSize() }!!
    println(smallestBiggestDir.name + " size=" + smallestBiggestDir.totalSize())
}