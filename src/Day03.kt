package day3

import kotlinx.coroutines.runBlocking
import utils.*
import utils.numbers.overlapOrTouches
import utils.numbers.widen
import utils.string.asLines

private typealias SolutionType = Long

private const val defaultSolution: SolutionType = 0L

private const val dayNumber: String = "03"
private val testSolution1: SolutionType? = 4361
private val testSolution2: SolutionType? = 467835


sealed class CellType {
    object Blank : CellType()
    class Symbol(val value: Char) : CellType()
    class Number(val value: Long) : CellType()
}


private fun part1(input: String): SolutionType {
    val lines = input.asLines()
    val width = lines.first().length
    val blankLine = ".".repeat(width)
    val paddedLines = listOf(blankLine, *lines.toTypedArray(), blankLine)

    val symbolRegex = "[^(\\d|\\.)]".toRegex()
    val numberRegex = "\\d+".toRegex()


    val partNumbers = paddedLines.windowed(3) { window ->
        val current = window[1]

        val matches = numberRegex.findAll(current)

        val partNumberMatches = matches.filter {
            val start = it.range.first.dec().coerceAtLeast(0)
            val end = it.range.last.inc().inc().coerceAtMost(width)

            window.any { line ->
                val sub = line.subSequence(start, end)
                sub.contains(symbolRegex)
            }
        }.map { it.value.toLong() }.toList()
        return@windowed partNumberMatches
    }.flatten()

    return partNumbers.sum()
}

private fun part2(input: String): SolutionType {
    val lines = input.asLines()
    val width = lines.first().length
    val blankLine = ".".repeat(width)
    val paddedLines = listOf(blankLine, *lines.toTypedArray(), blankLine)

    val symbolRegex = "[^(\\d|\\.)]".toRegex()
    val numberRegex = "\\d+".toRegex()


    val gearValues = paddedLines.windowed(3) { window ->
        val current = window[1]

        val symbolMatches = symbolRegex.findAll(current)
        val numberMatches = window.flatMap {
            numberRegex.findAll(it)
        }

        val gearParts = symbolMatches.mapNotNull { gearHub ->
            val overlappingParts = numberMatches.filter { part -> part.range.widen().contains(gearHub.range.first) }
            if (overlappingParts.count() == 2) return@mapNotNull overlappingParts.map { it.value.toLong() }
            else return@mapNotNull null
        }.toList()

        gearParts.sumOf { it.first() * it.last() }
    }

    return gearValues.sum()
}


fun main() {
    runBlocking {
        checkOrGetInput(day = 3)
    }
    runSolver("Test 1", sampleInput, testSolution1, ::part1)
    runSolver("Part 1", readInputAsText("Day${dayNumber}"), null, ::part1)

    runSolver("Test 2", sampleInput, testSolution2, ::part2)
    runSolver("Part 2", readInputAsText("Day${dayNumber}"), null, ::part2)
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
