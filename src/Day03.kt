package day3

import kotlinx.coroutines.runBlocking
import utils.*
import utils.grid.filterCells
import utils.grid.toGrid
import utils.numbers.lengthInBase10

private typealias SolutionType = Long

private const val defaultSolution: SolutionType = 0L

private const val dayNumber: String = "03"
private val testSolution1: SolutionType? = 4361
private val testSolution2: SolutionType? = null


sealed class CellType {
    object Blank : CellType()
    class Symbol(val value: Char) : CellType()
    class Number(val value: Long) : CellType()
}


private fun part1(input: String): SolutionType {
    val grid = input.toGrid("\\d+|\\S") {
        if (it == ".") return@toGrid CellType.Blank
        it.toLongOrNull()?.let { return@toGrid CellType.Number(it) }
        return@toGrid CellType.Symbol(it.first())
    }

    return grid
        .filterCells { it is CellType.Number }
        .filter {
            val number = it.value as CellType.Number
            val surroundingCells = it.getAdjacentCells(number.value.lengthInBase10())

            surroundingCells.any { adj ->
                adj.value is CellType.Symbol
            }
        }.sumOf {
            (it.value as? CellType.Number)?.value ?: 0L
        }
}

private fun part2(input: String): SolutionType {
    return defaultSolution
}


fun main() {
    runBlocking {
        checkOrGetInput(day = 3)
    }
    runSolver("Test 1", sampleInput, testSolution1, ::part1)
    //runSolver("Part 1", readInputAsText("Day${dayNumber}"), null, ::part1)

    //runSolver("Test 2", sampleInput, testSolution2, ::part2)
    //runSolver("Part 2", readInputAsText("Day${dayNumber}"), null, ::part2)
}

val sampleInput = "467..114..\n" +
        "...*......\n" +
        "..35..633.\n" +
        "......#...\n" +
        "617*......\n" +
        ".....+.58.\n" +
        "..592.....\n" +
        "......755.\n" +
        "...\$.*....\n" +
        ".664.598.."
