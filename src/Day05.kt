fun main() {
    val input = readInput("Day05")
//    val input = readInput("Day05_test")
    println(part2(input))
}

private fun part1(input: List<String>): Int {
    var maxCoord = 0
    val lines = mutableListOf<Line>()
    for (inputCoords in input) {
        val lineCoords = inputCoords.split(",", " -> ").map(String::toInt)
        val maxLineCoord = lineCoords.maxOrNull() ?: 0
        if (maxLineCoord > maxCoord) maxCoord = maxLineCoord
        val line = Line(x1 = lineCoords[0], y1 = lineCoords[1], x2 = lineCoords[2], y2 = lineCoords[3])
        if (line.isStraight()) lines.add(line)
    }
    val field = Array(maxCoord + 1) { IntArray(maxCoord + 1) }
    for (line in lines) {
        line.apply {
            if (isHorizontal()) {
                val (start, end) = if (x2 > x1) x1 to x2 else x2 to x1
                var x = start
                while (x <= end) {
                    field[y1][x]++
                    x++
                }
            } else {
                val (start, end) = if (y2 > y1) y1 to y2 else y2 to y1
                var y = start
                while (y <= end) {
                    field[y][x1]++
                    y++
                }
            }
        }
    }
    var ans = 0
    for (y in field.indices) for (x in field.indices) {
        if (field[y][x] >= 2) ans++
    }
    return ans
}

private fun part2(input: List<String>): Int {
    var maxCoord = 0
    val lines = mutableListOf<Line>()
    for (inputCoords in input) {
        val lineCoords = inputCoords.split(",", " -> ").map(String::toInt)
        val maxLineCoord = lineCoords.maxOrNull() ?: 0
        if (maxLineCoord > maxCoord) maxCoord = maxLineCoord
        val line = Line(x1 = lineCoords[0], y1 = lineCoords[1], x2 = lineCoords[2], y2 = lineCoords[3])
        lines.add(line)
    }
    val field = Array(maxCoord + 1) { IntArray(maxCoord + 1) }
    for (line in lines) {
        line.apply {
            when {
                isHorizontal() -> {
                    val (start, end) = if (x2 > x1) x1 to x2 else x2 to x1
                    var x = start
                    while (x <= end) {
                        field[y1][x]++
                        x++
                    }
                }
                isVertical() -> {
                    val (start, end) = if (y2 > y1) y1 to y2 else y2 to y1
                    var y = start
                    while (y <= end) {
                        field[y][x1]++
                        y++
                    }
                }
                isDiagonal() -> {
                    var x = x1
                    var y = y1
                    while (true) {
                        field[y][x]++
                        if (x < x2) x++ else x--
                        if (y < y2) y++ else y--
                        if (x == x2 && y == y2) {
                            field[y][x]++
                            break
                        }
                    }
                }
            }
        }
    }
    var ans = 0
    for (y in field.indices) for (x in field.indices) {
        if (field[y][x] >= 2) ans++
    }
    return ans
}

private data class Line(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {
    fun isStraight() = x1 == x2 || y1 == y2
    fun isDiagonal() = !isStraight()
    fun isVertical() = isStraight() && x1 == x2
    fun isHorizontal() = isStraight() && y1 == y2
}