package utils.numbers

fun Int.justOne() = this.coerceIn(-1, 1)

fun sumOfLongs(count: Int, block: (Int) -> Long): Long {
    var sum = 0L
    repeat(count) {
        sum += block(it)
    }
    return sum
}

fun sumOfInts(count: Int, block: (Int) -> Int): Int {
    var sum = 0
    repeat(count) {
        sum += block(it)
    }
    return sum
}