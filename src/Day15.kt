package day15

import utils.Point
import utils.readInputAsText
import utils.runSolver
import utils.string.asLines
import kotlin.math.absoluteValue

private typealias SolutionType = Int
private typealias InputType = Pair<String, Int>

private const val defaultSolution = 0

private const val dayNumber: String = "15"


fun Point.squareDistanceTo(other: Point): Int =
    (first - other.first).absoluteValue + (second - other.second).absoluteValue


data class Sensor(val position: Point, val beacon: Point) {
    val range = position.squareDistanceTo(beacon)

    fun coveredRange(y: Int): IntRange {
        val xSlack = range - (y - position.second)
        return position.first - xSlack..position.first + xSlack
    }

    fun coverageAsRanges(): Map<Int, IntRange> {
        val out = mutableMapOf<Int, IntRange>()
        for (i in (-range)..range) {
            val xSlack = range - (i).absoluteValue
            out[i + position.second] = position.first - xSlack..position.first + xSlack
        }
        return out
    }

    fun pointsInRangeAtY(y: Int): Set<Point> {
        val covered = coveredRange(y)

        return covered.map {
            it to y
        }.toSet()
    }

    fun isPointInRange(point: Point): Boolean =
        range >= position.squareDistanceTo(point)

    fun coveredRangeSet(y: Int) = coveredRange(y).toSet()

    fun coveredPoints(): Set<Point> {
        val out = mutableSetOf<Point>()

        for (i in (-range)..range) {
            val x = i + position.first
            val ySlack = range - (i).absoluteValue
            for (y in -ySlack..ySlack) {
                val point = x to y
                out.add(point)
            }
        }

        return out
    }

    fun circumference(): Set<Point> {
        val range = range + 1
        val out = mutableSetOf<Point>()

        for (i in (-range)..range) {
            val x = i + position.first
            val ySlack = range - (i).absoluteValue
            out.add(x to position.second + ySlack)
            out.add(x to position.second - ySlack)
        }

        return out
    }
}

fun atToPoint(pointText: String): Point {
    val x = pointText.substringAfter("at x=").substringBefore(",").toInt()
    val y = pointText.substringAfter(" y=").toInt()
    return x to y
}

fun parseSensors(input: String): Set<Sensor> {
    return input.asLines().map {
        val (sensor, beacon) = it.split(":")
        Sensor(
            atToPoint(sensor),
            atToPoint(beacon)
        )
    }.toSet()
}

private fun part1(data: InputType): SolutionType {
    val (input, row) = data
    val sensors = parseSensors(input)
    val beacons = sensors.map { it.beacon }.toSet()

    val coveredPoints = sensors.asSequence().map { it.pointsInRangeAtY(row) }.flatten().toSet()

    val coveredBeacons = coveredPoints.intersect(beacons)
    return coveredPoints.size - coveredBeacons.size
}


private const val encode = 4000000L

private fun part2Helper(input: String, size: Long): Int {
    val sizeRange = 0..size
    val sensors = parseSensors(input)
    val beacons = sensors.map { it.beacon }

    val targets = sensors.asSequence()
        .flatMap { it.circumference() }
        .filter { it.first in sizeRange && it.second in sizeRange }
        .filter { possible -> sensors.none { it.isPointInRange(possible) } }
        .filter { possible -> possible !in beacons }
        .map { it.first * encode + it.second }
        .toList()

    println(targets)

    return targets.size
}

private fun part2Test(input: String): SolutionType {
    return part2Helper(input, 20)
}

private fun part2(input: String): SolutionType {
    return part2Helper(input, encode)
    //849139071 is too low
}


fun main() {
    runSolver("Test 1", readInputAsText("Day${dayNumber}_test") to 10, 26, ::part1)
    //runSolver("Part 1", readInputAsText("Day${dayNumber}") to 2000000, null, ::part1)

    runSolver("Test 2", readInputAsText("Day${dayNumber}_test"), 56000011, ::part2Test)
    runSolver("Part 2", readInputAsText("Day${dayNumber}"), null, ::part2)
}
