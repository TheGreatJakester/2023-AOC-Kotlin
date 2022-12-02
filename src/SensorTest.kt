import day15.Sensor
import org.junit.jupiter.api.Assertions.*

class SensorTest {

    @org.junit.jupiter.api.Test
    fun circumference() {
        val testSensor = Sensor(2 to 2, 1 to 2)
        val expectedCir = setOf(
            2 to 4,
            1 to 3,
            3 to 3,
            0 to 2,
            4 to 2,
            1 to 1,
            3 to 1,
            2 to 0,
        ).sortedBy { it.first }

        val actualDir = testSensor.circumference().sortedBy { it.first }

        val dif = actualDir - expectedCir
        assert(dif.isEmpty())
    }
    @org.junit.jupiter.api.Test
    fun range() {
        val testSensor = Sensor(2 to 2, 1 to 2)
        assert(testSensor.range == 1)
    }

    @org.junit.jupiter.api.Test
    fun coverageAsRanges(){
        val testSensor = Sensor(2 to 2, 1 to 2)
        val ranges = testSensor.coverageAsRanges()
        assert(ranges.size == 3)
    }

    @org.junit.jupiter.api.Test
    fun rangesAtY(){
        val testSensor = Sensor(2 to 2, 1 to 2)
        val range = testSensor.coveredRange(2)
        assert(range == 1..3)
    }
}