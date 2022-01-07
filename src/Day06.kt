fun main() {
    fun part1Array(input: List<String>): Int {
        val fishTimers = input[0].split(",").map { it.toInt() }.toMutableList()
        for (i in 1..80) {
            var fishesAdd = 0
            for ((index, fishTimer) in fishTimers.withIndex()) {
                if (fishTimer == 0) {
                    fishTimers[index] = 6
                    fishesAdd++
                } else {
                    fishTimers[index] = fishTimer - 1
                }
            }
            repeat(fishesAdd) { fishTimers.add(8) }
        }
        return fishTimers.size
    }

    fun part1Map(input: List<String>): Int {
        var timersMap = mutableMapOf<Int, Int>()
        for (i in 0..8) timersMap[i] = 0
        input[0].split(",").map { it.toInt() }.forEach { timer ->
            val current = timersMap[timer] ?: 0
            timersMap[timer] = current + 1
        }
        for (i in 1..80) {
            val newMap = mutableMapOf<Int, Int>()
            timersMap.forEach { (timer, quantity) ->
                if (timer == 0) {
                    newMap[6] = (newMap[6] ?: 0) + quantity
                    newMap[8] = quantity
                } else {
                    newMap[timer - 1] = (newMap[timer - 1] ?: 0) + quantity
                }
            }
            timersMap = newMap
        }
        return timersMap.values.sum()
    }

    fun part2(input: List<String>): Long {
        var timersMap = mutableMapOf<Int, Long>()
        for (i in 0..8) timersMap[i] = 0
        input[0].split(",").map { it.toInt() }.forEach { timer ->
            val current = timersMap[timer] ?: 0
            timersMap[timer] = current + 1
        }
        for (i in 1..256) {
            val newMap = mutableMapOf<Int, Long>()
            timersMap.forEach { (timer, quantity) ->
                if (timer == 0) {
                    newMap[6] = (newMap[6] ?: 0) + quantity
                    newMap[8] = quantity
                } else {
                    newMap[timer - 1] = (newMap[timer - 1] ?: 0) + quantity
                }
            }
            timersMap = newMap
        }
        return timersMap.values.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1Map(testInput) == 5934)

    val input = readInput("Day06")
    println(part1Map(input))
    println(part2(input))
}
