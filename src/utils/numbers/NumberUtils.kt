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

fun Long.lengthInBase10(): Int {
    if (this >= 10000) TODO("support larger numbers")
    if (this >= 1000) return 4
    if (this >= 100) return 3
    if (this >= 10) return 2
    if (this >= 1) return 1

    return (-this).lengthInBase10() + 1
}
fun Int.lengthInBase10(): Int = this.toLong().lengthInBase10()