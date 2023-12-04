package utils.numbers


infix fun IntRange.overlaps(other: IntRange): Boolean = other.first in this || other.last in this
infix fun IntRange.overlapOrTouches(other: IntRange): Boolean = other.first - 1 in this || other.last + 1 in this

fun IntRange.widen(
    step: Int = 1,
    min: Int = this.first - step,
    max: Int = this.last + step
) = IntRange(
    (this.first - step).coerceAtLeast(min),
    (this.last + step).coerceAtMost(max)
)