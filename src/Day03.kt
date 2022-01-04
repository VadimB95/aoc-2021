fun main() {
    fun part1(input: List<String>): Int {
        val rowsNumber = input.size
        val columnsNumber = input.first().length
        val report = MutableList(columnsNumber) { MutableList(rowsNumber) { 0 } }

        input.forEachIndexed { rowIndex, line ->
            line.forEachIndexed { columnIndex, c ->
                report[columnIndex][rowIndex] = c.digitToInt()
            }
        }
        val gammaRateSb = StringBuilder()
        val epsilonRateSb = StringBuilder()
        report.forEach { column ->
            if (column.sum() > column.size / 2) {
                gammaRateSb.append('1')
                epsilonRateSb.append('0')
            } else {
                gammaRateSb.append('0')
                epsilonRateSb.append('1')
            }
        }
        val gammaRate = Integer.parseInt(gammaRateSb.toString(), 2)
        val epsilonRate = Integer.parseInt(epsilonRateSb.toString(), 2)
        return gammaRate * epsilonRate
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)

    val input = readInput("Day03")
    println(part1(input))
//    println(part2(input))
}
