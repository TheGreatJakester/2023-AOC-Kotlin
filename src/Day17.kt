package day17


import utils.readInputAsText
import utils.runSolver

private typealias SolutionType = Int

private const val defaultSolution = 0

private const val dayNumber: String = "17"

private fun part1(data: String): SolutionType {
    return defaultSolution
}

private fun part2(input: String): SolutionType {
    return defaultSolution
}


fun main() {
    runSolver("Test 1", readInputAsText("Day${dayNumber}_test"), null, ::part1)
    runSolver("Part 1", readInputAsText("Day${dayNumber}"), null, ::part1)

    runSolver("Test 2", readInputAsText("Day${dayNumber}_test"), null, ::part2)
    runSolver("Part 2", readInputAsText("Day${dayNumber}"), null, ::part2)
}
