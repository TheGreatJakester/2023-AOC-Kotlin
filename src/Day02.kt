package day2

import kotlinx.coroutines.runBlocking
import utils.*
import utils.string.asLines
import java.awt.Color

private typealias SolutionType = Long

private const val defaultSolution = 0

private const val dayNumber: String = "02"
private val testSolution1: SolutionType? = 8
private val testSolution2: SolutionType? = 2286


fun colorToMax(color: String) = when (color) {
    "red" -> 12
    "green" -> 13
    "blue" -> 14
    else -> throw Exception("Missing color: $color")
}

fun isCountOverMax(color: String, count: Int): Boolean = colorToMax(color) < count


fun MutableMap<String, Int>.applyNewMax(color: String, count: Int) {
    val old = getOrDefault(color, 0)
    if (count > old) {
        put(color, count)
    }
}

fun Map<*, Int>.productOfValues(): Long = this.values.fold(1L) { acc, i ->
    acc * i
}


private fun part1(input: String): SolutionType {
    val sum = input.asLines().sumOf {

        val (gameId, sets) = it.split(":")

        sets.split(";").forEach { piles ->

            piles.split(",").forEach { pile ->
                val (count, color) = pile.trim().split(" ")
                if (isCountOverMax(color, count.trim().toInt())) return@sumOf 0
            }
        }

        return@sumOf gameId.split(" ").last().toInt()
    }


    return sum.toLong()
}

private fun part2(input: String): SolutionType {
    val sum = input.asLines().sumOf {
        val maxs = mutableMapOf<String, Int>()
        val (gameId, sets) = it.split(":")

        sets.split(";").forEach { set ->

            set.split(",").forEach { pile ->
                val (count, color) = pile.trim().split(" ")
                maxs.applyNewMax(color, count.toInt())
            }


        }

        return@sumOf maxs.productOfValues()
    }


    return sum
}


fun main() {
    runBlocking {
        checkOrGetInput(day = 2)
    }
    runSolver("Test 1", sampleInput, testSolution1, ::part1)
    runSolver("Part 1", readInputAsText("Day${dayNumber}"), null, ::part1)

    runSolver("Test 2", sampleInput, testSolution2, ::part2)
    runSolver("Part 2", readInputAsText("Day${dayNumber}"), null, ::part2)
}

val sampleInput = "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green\n" +
        "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue\n" +
        "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red\n" +
        "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red\n" +
        "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"
