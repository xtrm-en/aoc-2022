package me.xtrm.aoc2022.days

import me.xtrm.aoc2022.getInput

// Part 2 toggle
@Suppress("unused")
internal const val correctGuide = true

fun main() {
    val input = getInput(day = 2)
    val nameMapping = mapOf(
        "A" to "Rock",
        "B" to "Paper",
        "C" to "Scissors",
    )
    val winAgainst = mapOf(
        "Rock" to "Scissors",
        "Paper" to "Rock",
        "Scissors" to "Paper",
    )

    println("Opponent\tvs Mine" + if (correctGuide) " (Correct guide)" else "")
    println("========\t   ====")
    var score = 0
    for (line in input) {
        var roundScore = 0
        var (opponent, target) = line.split(" ")
        // normalize
        target = (target.toCharArray()[0] - 23).toString()

        opponent = nameMapping[opponent]!!
        target = if (correctGuide) {
            // normalize
            when (target) {
                "A" -> winAgainst[opponent]!! // lose
                "B" -> opponent // draw
                "C" -> winAgainst.filter { it.value == opponent }.firstNotNullOf { it.key }.toString() // win
                else -> error("damn")
            }
        } else {
            nameMapping[target]!!
        }

        val sep = "\t".repeat(if (opponent == "Scissors") 1 else 2)
        val sep2 = "\t".repeat(if (target == "Rock") 2 else 1)
        print("$opponent${sep}vs $target$sep2")

        // true -> win, false -> lose, null -> draw
        val winState: Boolean? = if (winAgainst[target] == opponent) {
            true
        } else if (winAgainst[opponent] == target) {
            false
        } else {
            null
        }

        print(" | ${(winState ?: "draw").toString().replace("true", " win").replace("false", "lose")}")

        // add outcome-based points
        when (winState) {
            null -> {
                print(" +3")
                roundScore += 3
            }

            true -> {
                print(" +6")
                roundScore += 6
            }

            else -> {
                print(" +0")
            }
        }

        // add choice-based points
        roundScore += (winAgainst.keys.toList().indexOf(target) + 1).also { print(" (+ $it, $target)") }
        print(("\t".takeIf { target != "Scissors" } ?: "") + "\t| Total += $roundScore")
        score += roundScore
        print("\n")
    }

    println("Score: $score")
}