package day4

import kotlinx.coroutines.runBlocking
import utils.*
import utils.string.asLines
import java.lang.Math.pow
import kotlin.math.roundToInt
import kotlin.math.roundToLong

private typealias SolutionType = Int

private const val defaultSolution: SolutionType = 0

private const val dayNumber: String = "04"
private val testSolution1: SolutionType? = 13
private val testSolution2: SolutionType? = 30


private fun part1(input: String): SolutionType {
    return input.asLines().sumOf { line ->
        val (cardNumber, card) = line.split(":")
        val (winningNumbers, numbers) = card.split("|")
        val winningSet = winningNumbers.trim().split(" ").filterNot { it.isEmpty() }.toSet()
        val numberSet = numbers.trim().split(" ").filter { it.isNotEmpty() }.toSet()

        val winningCount = numberSet.count { winningSet.contains(it) }

        if (winningCount == 0) return@sumOf 0
        pow(2.0, winningCount.toDouble() - 1).roundToInt()
    }

}

fun <T : Any> MutableMap<T, Int>.increment(key: T) {
    val old = getOrDefault(key, 0)
    put(key, old + 1)
}

fun countCopies(cardWins: Map<Int, Int>, cardCounts: MutableMap<Int, Int>, cardNumber: Int) {
    val wins = cardWins.getOrDefault(cardNumber, 0)

    repeat(wins) {
        val cardIndex = cardNumber + it + 1
        countCopies(cardWins, cardCounts, cardIndex)
        cardCounts.increment(cardIndex)
    }

}

private fun part2(input: String): SolutionType {
    val cardMap = input.asLines().associate { line ->
        val (cardNumber, card) = line.split(":")
        val cardInt = cardNumber.split(" ").last().trim().toInt()

        val (winningNumbers, numbers) = card.split("|")
        val winningSet = winningNumbers.trim().split(" ").filterNot { it.isEmpty() }.toSet()
        val numberSet = numbers.trim().split(" ").filter { it.isNotEmpty() }.toSet()

        val winningCount = numberSet.count { winningSet.contains(it) }

        cardInt to winningCount
    }

    val cardCounts = mutableMapOf<Int, Int>()

    repeat(cardMap.count()) {
        val cardNumber = it + 1
        countCopies(cardMap, cardCounts, cardNumber)
    }


    return cardCounts.values.sum() + cardMap.count()
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

val sampleInput = "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53\n" +
        "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19\n" +
        "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1\n" +
        "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83\n" +
        "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36\n" +
        "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"
