fun main() {
    fun part1(input: List<String>): Int {
        val intData = input.map { it.toInt() }
        var increasedNumber = 0
        for (i in 1 until intData.size) {
            if (intData[i] > intData[i - 1]) increasedNumber++
        }
        return increasedNumber
    }

    fun part2(input: List<String>): Int {
        val intData = input
            .map { it.toInt() }
            .windowed(3)
            .map { it.sum() }
        var increasedNumber = 0
        for (i in 1 until intData.size) {
            if (intData[i] > intData[i - 1]) increasedNumber++
        }
        return increasedNumber
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
