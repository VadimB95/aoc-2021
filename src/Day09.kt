fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)

    val input = readInput("Day09")
    println(part1(input))
//    println(part2(input))
}

private fun part1(input: List<String>): Int {
    val rows = input.size
    val columns = input[0].length
    val heightmap = Array(rows) { IntArray(columns) }
    val lowPoints = Array(rows) { BooleanArray(columns) }
    for (i in 0 until rows) for (j in 0 until columns) {
        heightmap[i][j] = input[i][j].digitToInt()
    }
    var ans = 0
    for (i in 0 until rows) points@ for (j in 0 until columns) {
        var isAdjacentEquals = true
        for (adjPoint in Point(i, j).getAdjacent(rows, columns)) {
            if ((heightmap[adjPoint.i][adjPoint.j] < heightmap[i][j])) continue@points
            isAdjacentEquals = heightmap[adjPoint.i][adjPoint.j] == heightmap[i][j]
        }
        if (!isAdjacentEquals) {
            lowPoints[i][j] = true
            ans += heightmap[i][j] + 1
        }
    }
    return ans
}

private fun part2(input: List<String>): Int {
    return 0
}

data class Point(val i: Int, val j: Int) {
    fun getAdjacent(rows: Int, columns: Int): List<Point> =
        listOf(Point(i, j - 1), Point(i + 1, j), Point(i, j + 1), Point(i - 1, j)).filter { point ->
            (point.i in 0 until rows) && (point.j in 0 until columns)
        }
}