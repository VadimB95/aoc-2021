fun main() {
    val testInput = readInputAsString("Day04_test")
    check(part1(testInput) == 4512)

    val input = readInputAsString("Day04")
    println(part1(input))
    println(part2(input))
}

private fun part1(input: String): Int {
    val inputSplit = input.split("\n\n")
    val drawnNumbers = inputSplit[0].split(",").map { it.toInt() }
    val boardsNotParsed = inputSplit.slice(1 until inputSplit.size)
    val boardSize = getBoardSize(inputSplit[1])
    val boards = List(boardsNotParsed.size) { Array(boardSize) { Array(boardSize) { 0 to false } } }

    parseBoards(boardsNotParsed, boards)

    var answer = -1
    var boardWonIndex = -1
    for (drawnNumber in drawnNumbers) {
        boards.forEachIndexed { boardIndex, board ->
            board.forEachIndexed { rowIndex, row ->
                row.forEachIndexed { columnIndex, pair ->
                    if (pair.first == drawnNumber) {
                        board[rowIndex][columnIndex] = pair.copy(second = true)
                    }
                }
            }
            if (checkBoardWon(board)) {
                boardWonIndex = boardIndex
                answer = calculateAnswer(board, drawnNumber)
                return@forEachIndexed
            }
        }
        if (boardWonIndex != -1) break
    }
    if (answer == -1) {
        println("No board won")
    } else {
        println("Board $boardWonIndex won")
    }
    return answer
}

private fun part2(input: String): Int {
    val inputSplit = input.split("\n\n")
    val drawnNumbers = inputSplit[0].split(",").map { it.toInt() }
    val boardsNotParsed = inputSplit.slice(1 until inputSplit.size)
    val boardSize = getBoardSize(inputSplit[1])
    val boardsNumber = boardsNotParsed.size
    val boards = List(boardsNumber) { Array(boardSize) { Array(boardSize) { 0 to false } } }

    parseBoards(boardsNotParsed, boards)

    var answer = -1
    val boardsWonIndexes = mutableListOf<Int>()
    for (drawnNumber in drawnNumbers) {
        boards.forEachIndexed { boardIndex, board ->
            if (boardsWonIndexes.contains(boardIndex)) return@forEachIndexed
            board.forEachIndexed { rowIndex, row ->
                row.forEachIndexed { columnIndex, pair ->
                    if (pair.first == drawnNumber) {
                        board[rowIndex][columnIndex] = pair.copy(second = true)
                    }
                }
            }
            if (checkBoardWon(board)) {
                boardsWonIndexes.add(boardIndex)
                if (boardsWonIndexes.size == boardsNumber) {
                    answer = calculateAnswer(board, drawnNumber)
                    return@forEachIndexed
                }
            }
        }
        if (answer != -1) break
    }
    if (answer == -1) {
        println("No board won")
    } else {
        println("Last board won index: ${boardsWonIndexes.last()}")
    }
    return answer
}

private fun parseBoards(
    boardsToParse: List<String>,
    destBoards: List<Array<Array<Pair<Int, Boolean>>>>
) {
    for ((boardIndex, s) in boardsToParse.withIndex()) {
        s.split("\n").forEachIndexed { rowIndex, rowString ->
            val rowStringTrimmed = rowString.trim()
            rowStringTrimmed.split(oneOrMoreWhitespacesRegex).forEachIndexed { columnIndex, numberString ->
                val number = numberString.toInt()
                destBoards[boardIndex][rowIndex][columnIndex] = number to false
            }
        }
    }
}

private fun getBoardSize(boardString: String): Int {
    val indexOfLf = boardString.indexOfFirst { it == '\n' }
    return boardString.slice(0 until indexOfLf).split(oneOrMoreWhitespacesRegex).size
}

private fun checkBoardWon(board: Array<Array<Pair<Int, Boolean>>>): Boolean {
    val boardSize = board.size
    (0 until boardSize).forEach { i0 ->
        var isRowWon = true
        var isColumnWon = true
        (0 until boardSize).forEach { i1 ->
            isRowWon = isRowWon && board[i0][i1].second
            isColumnWon = isColumnWon && board[i1][i0].second
        }
        if (isRowWon || isColumnWon) return true
    }
    return false
}

private fun calculateAnswer(board: Array<Array<Pair<Int, Boolean>>>, drawnNumber: Int): Int {
    var unmarkedSum = 0
    for (row in board) {
        for (pair in row) {
            if (!pair.second) {
                unmarkedSum += pair.first
            }
        }
    }
    return unmarkedSum * drawnNumber
}

private val oneOrMoreWhitespacesRegex = "\\s+".toRegex()
