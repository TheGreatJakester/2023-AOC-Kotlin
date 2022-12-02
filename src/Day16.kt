package day16


import utils.readInputAsText
import utils.runSolver
import utils.string.asLines
import java.lang.IllegalArgumentException

private typealias SolutionType = Int

private const val defaultSolution = 0

private const val dayNumber: String = "16"

data class Valve(val name: String, val rate: Int, val connectedNames: Set<String>) {
    var isOpen = false
    lateinit var connected: Set<Valve>
    fun setup(allValves: Set<Valve>) {
        connected = connectedNames
            .map { connectedName ->
                try {
                    allValves.first { it.name == connectedName }
                } catch (ex: NoSuchElementException) {
                    throw IllegalArgumentException("Missing $connectedName")
                }
            }.toSet()
    }

    fun whereToGoNext(): List<Valve> = connected.filter { it.distance > this.distance }

    fun startNewSearch(minutesLeft: Int) {
        this.minutesLeft = minutesLeft
        this.distance = Int.MAX_VALUE
    }

    private var minutesLeft = 0
    var distance = Int.MAX_VALUE

    val totalNewValue: Int
        get() = if (!isOpen)
            (minutesLeft - (distance + 1)).coerceAtLeast(0) * rate
        else 0
}

fun parseInputAsTunnels(input: String): Set<Valve> {
    val allValves = input.asLines().map {
        val name = it.substringAfter("Valve ").substringBefore(" has")
        val rate = it.substringAfter("rate=").substringBefore(";").toInt()
        val connected = it.substringAfter("valves").replace(" ", "").split(",").toSet()
        Valve(name, rate, connected)
    }.toSet()

    allValves.forEach {
        it.setup(allValves)
    }

    return allValves
}

fun searchTunnels(startValve: Valve, minutesLeft: Int, allValves: Set<Valve>) {
    if (startValve !in allValves) {
        throw IllegalArgumentException("No start tunnel by name: ${startValve.name}")
    }

    allValves.forEach { it.startNewSearch(minutesLeft) }

    startValve.distance = 0

    val searchingQueue = ArrayDeque(listOf(startValve))

    while (searchingQueue.isNotEmpty()) {
        val nextTunnel = searchingQueue.removeFirst()
        val toSearch = nextTunnel.whereToGoNext()
        toSearch.forEach { it.distance = nextTunnel.distance + 1 }
        searchingQueue.addAll(toSearch)
    }
}

private fun part1(data: String): SolutionType {
    val tunnels = parseInputAsTunnels(data)
    val startTunnel = tunnels.first { it.name == "AA" }
    val valveOrder = mutableListOf<Pair<Valve, Int>>()

    var nextTunnel = startTunnel
    var timeLeft = 30

    while (timeLeft >= 0) {
        searchTunnels(nextTunnel, timeLeft, tunnels)
        nextTunnel = tunnels.maxBy { it.totalNewValue }
        nextTunnel.isOpen = true
        timeLeft -= nextTunnel.distance + 1
        valveOrder.add(nextTunnel to timeLeft)
    }

    return valveOrder.sumOf { it.first.rate * it.second }
}

private fun part2(input: String): SolutionType {
    return defaultSolution
}


fun main() {
    runSolver("Test 1", readInputAsText("Day${dayNumber}_test"), 1651, ::part1)
    //runSolver("Part 1", readInputAsText("Day${dayNumber}"), null, ::part1)

    runSolver("Test 2", readInputAsText("Day${dayNumber}_test"), null, ::part2)
    runSolver("Part 2", readInputAsText("Day${dayNumber}"), null, ::part2)
}
