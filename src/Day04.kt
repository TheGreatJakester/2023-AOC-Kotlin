package day4

import kotlinx.coroutines.runBlocking
import utils.*
import utils.string.asLines
import java.awt.Color

private typealias SolutionType = Long

private const val defaultSolution: SolutionType = 0L

private const val dayNumber: String = "04"
private val testSolution1: SolutionType? = null
private val testSolution2: SolutionType? = null


private fun part1(input: String): SolutionType {
    return defaultSolution
}

private fun part2(input: String): SolutionType {
    return defaultSolution
}


fun main() {
    runBlocking {
        checkOrGetInput(day = 4)
    }
    runSolver("Test 1", sampleInput, testSolution1, ::part1)
    runSolver("Part 1", readInputAsText("Day${dayNumber}"), null, ::part1)

    runSolver("Test 2", sampleInput, testSolution2, ::part2)
    runSolver("Part 2", readInputAsText("Day${dayNumber}"), null, ::part2)
}

val sampleInput = ""
