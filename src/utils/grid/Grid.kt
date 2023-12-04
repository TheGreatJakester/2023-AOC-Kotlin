package utils.grid

private typealias Grid<T> = List<List<T>>
private typealias MutableGrid<T> = MutableList<MutableList<T>>

fun Grid<*>.height() = count()
fun Grid<*>.width() = maxOf { it.count() }

fun <T> Grid<T>.forForEach(lambda: (el: T) -> Unit) = forEach {
    it.forEach(lambda)
}

fun <T> Grid<T>.forForEachIndexed(lambda: (x: Int, y: Int, el: T) -> Unit) = forEachIndexed { y, sub ->
    sub.forEachIndexed { x, el ->
        lambda(x, y, el)
    }
}

fun <T> mutableGridOf(width: Int, height: Int, default: T): MutableGrid<T> = List(height) {
    List(width) { default }.toMutableList()
}.toMutableList()

fun <T> String.toGrid(
    cellRegex: String,
    lineDeliminator: String = "\n",
    cellMapper: (String) -> T
): Grid<T> {
    val regex = cellRegex.toRegex()
    return split(lineDeliminator).map {
        regex.findAll(it)
            .map { cellMatch -> cellMapper(cellMatch.value) }
            .toList()
    }
}

fun <T> Grid<T>.filterCells(filter: (T) -> Boolean): List<Cell<T>> {
    val cells = mutableListOf<Cell<T>>()

    forEachIndexed { y, sub ->
        sub.forEachIndexed { x, el ->
            if (filter(el)) {
                cells.add(Cell(x, y, this))
            }
        }
    }

    return cells
}

class Cell<T>(val x: Int, val y: Int, private val grid: Grid<T>) {
    val value = grid[y][x]

    fun getAdjacentCells(cellSize: Int = 1): List<Cell<T>> {
        val cells = mutableListOf<Cell<T>>()

        for (row in y - 1..y + 1) {
            for (col in (x - 1..x + cellSize + 1)) {
                if (row == y && col in x..x + cellSize) continue

                val isCell = grid.getOrNull(row)?.indices?.contains(col) ?: false
                if (isCell) {
                    cells.add(Cell(col, row, grid))
                }
            }
        }

        return cells
    }
}




































