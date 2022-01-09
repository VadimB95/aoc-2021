import java.util.*

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
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
    for (i in 0 until rows) heightmap@ for (j in 0 until columns) {
        var isAdjacentEquals = true
        for (adjPoint in Point(i, j).getAdjacent(rows, columns)) {
            if ((heightmap[adjPoint.i][adjPoint.j] < heightmap[i][j])) continue@heightmap
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
    val rows = input.size
    val columns = input[0].length
    val heightmap = Array(rows) { IntArray(columns) }
    val lowPoints = mutableListOf<Point>()
    for (i in 0 until rows) for (j in 0 until columns) {
        heightmap[i][j] = input[i][j].digitToInt()
    }
    for (i in 0 until rows) heightmap@ for (j in 0 until columns) {
        var isAdjacentEquals = true
        for (adjPoint in Point(i, j).getAdjacent(rows, columns)) {
            if ((heightmap[adjPoint.i][adjPoint.j] < heightmap[i][j])) continue@heightmap
            isAdjacentEquals = heightmap[adjPoint.i][adjPoint.j] == heightmap[i][j]
        }
        if (!isAdjacentEquals) lowPoints.add(Point(i, j))
    }

    val basinSizes = mutableListOf<Int>()
    for (lowPoint in lowPoints) {
        val visited = mutableSetOf<Point>()
        val queue = LinkedList<Point>().also { it.add(lowPoint) }
        var basinSize = 0
        while (queue.isNotEmpty()) {
            val point = queue.pollFirst()
            visited.add(point)
            basinSize++
            for (adjPoint in point.getAdjacent(rows, columns)) {
                if (heightmap[adjPoint.i][adjPoint.j] != 9 &&
                    !visited.contains(adjPoint) &&
                    !queue.contains(adjPoint)
                ) {
                    queue.addLast(adjPoint)
                }
            }
        }
        basinSizes.add(basinSize)
    }
    return basinSizes.sorted().takeLast(3).reduce { acc, i -> acc * i }
}

private data class Point(val i: Int, val j: Int) {
    fun getAdjacent(rows: Int, columns: Int): List<Point> =
        listOf(Point(i, j - 1), Point(i + 1, j), Point(i, j + 1), Point(i - 1, j)).filter { point ->
            (point.i in 0 until rows) && (point.j in 0 until columns)
        }
}