fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)

    val input = readInput("Day08")
    println(part1(input))
}

private fun part1(input: List<String>): Int {
    var ans = 0
    val requiredSegmentsNumber = listOf(SEGMENTS_NUMBER_1, SEGMENTS_NUMBER_4, SEGMENTS_NUMBER_7, SEGMENTS_NUMBER_8)
    for (line in input) {
        ans += line.split(" | ")[1].split(" ").filter { requiredSegmentsNumber.contains(it.length) }.size
    }
    return ans
}

private const val SEGMENTS_NUMBER_1 = 2
private const val SEGMENTS_NUMBER_4 = 4
private const val SEGMENTS_NUMBER_7 = 3
private const val SEGMENTS_NUMBER_8 = 7
