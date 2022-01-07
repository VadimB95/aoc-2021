import kotlin.math.abs

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

private fun part1(input: List<String>): Int {
    val series = input[0].split(",").map { it.toInt() }.sorted()
    val median = if (series.size % 2 == 0) {
        (series[series.size / 2] + series[series.size / 2 - 1]) / 2
    } else {
        series[series.size / 2]
    }
    return series.fold(0) { acc, i -> acc + abs(i - median) }
}

private fun part2(input: List<String>): Long {
    val series = input[0].split(",").map { it.toInt() }
    val avg = series.average().toInt()
    return series.fold(0L) { acc, i ->
        val delta = abs(i - avg)
        var totalFuelToMove = acc
        var fuelToStep = 1
        for (a in 1..delta) {
            totalFuelToMove += fuelToStep
            fuelToStep++
        }
        totalFuelToMove
    }
}
