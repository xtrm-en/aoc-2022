package me.xtrm.aoc2022.days

import me.xtrm.aoc2022.getInput

private enum class Direction(
    val dx: Int,
    val dy: Int,
    val offx: Int,
    val offy: Int,
) {
    NORTH(0, -1, 1, 0),
    SOUTH(0, 1, 1, 0),
    EAST(1, 0, 0, 1),
    WEST(-1, 0, 0, 1);

    fun vec(): Pair<Int, Int> = dx to dy

    operator fun component1(): Int = dx
    operator fun component2(): Int = dy
    operator fun component3(): Int = offx
    operator fun component4(): Int = offy
}

@OptIn(ExperimentalStdlibApi::class)
fun main() {
    val input = getInput(day = 8)
    if (input[0].length != input.size) error("Not a square")
    input.forEach { println(it) }
    val size = input.size

    // old part 1
    // oldMain(input)

    // Helper functions
    fun List<String>.get(x: Int, y: Int): Int =
        this[y][x].toString().toInt()

    fun List<String>.get(pair: Pair<Int, Int>): Int =
        get(pair.first, pair.second)

    fun Pair<Int, Int>.offset(pair: Pair<Int, Int>): Pair<Int, Int> =
        Pair(first + pair.first, second + pair.second)

    val scenicMap: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()
    var totalVisible = 0
    for (x in 0..<input[0].length) {
        for (y in input.indices) {
            val origin = x to y
            val current = input.get(origin)
            println(">>> Origin $origin = $current")
            var isVisible = false
            for (dir in Direction.values()) {
                println("=> Going ${dir.name}")
                var coords = (x to y).offset(dir.vec())
                var visibleInDir = true
                var scenicDistance = 0
                while (coords.first in 0..<size && coords.second in 0..<size) {
                    val new = input.get(coords)
                    print("Found $coords = $new")
                    scenicDistance++
                    if (new >= current) {
                        visibleInDir = false
                        println(" ...stopping")
                        break
                    }
                    println()
                    coords = coords.offset(dir.vec())
                }
                if (!isVisible) {
                    isVisible = visibleInDir
                }
                println("Scenic[${dir.name}] = $scenicDistance")

                scenicMap.getOrDefault(origin, 1).let {
                    scenicMap[origin] = it * scenicDistance
                }
            }
            if (isVisible) {
                totalVisible += 1
            }
        }
    }
    println("Part 1: $totalVisible")

    scenicMap.forEach { (k, v) -> println("${k.first}, ${k.second}: $v") }
    scenicMap.maxBy { it.value }.let {
        println("Part 2: ${it.key.first},${it.key.second} = ${it.value}")
    }
}

// wasn't viable for part 2
@Suppress("unused")
fun oldMain(input: List<String>) {
    val visibility: MutableSet<Pair<Int, Int>> = mutableSetOf()
    val size = input.size
    // For each direction
    for (dir in Direction.values()) {
        println("Direction: $dir")
        // For each following the walls
        for (i in 0 until size) {
            println("Start: $i")
            val (dx, dy, offx, offy) = dir
            var pointer = Pair(
                (if (dx < 0) size - 1 else 0) + offx * i,
                (if (dy < 0) size - 1 else 0) + offy * i
            )
            var currentVal = -1
            for (j in 0 until size) {
                val (x, y) = pointer
                val value = input[y][x].toString().toInt()
                print("$x, $y = $value")
                if (value > currentVal) {
                    print(" >= $currentVal, adding")
                    currentVal = value
                    visibility.add(Pair(x, y))
                }
                println()
                pointer = Pair(x + dx, y + dy)
            }
        }
    }

    println("Total visible: ${visibility.size}")
}