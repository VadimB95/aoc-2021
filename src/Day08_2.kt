fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part2(testInput) == 61229)

    val input = readInput("Day08")
    println(part2(input))
}

private fun part2(input: List<String>): Int {
    var ans = 0
    for (line in input) {
        val (patterns, output) = line.split(" | ").let { it[0].split(" ") to it[1] }
        val freqTable = mutableMapOf<Char, Int>()
        for (pattern in patterns) {
            pattern.onEach { c ->
                val currentQuantity = freqTable[c]
                if (currentQuantity != null) {
                    freqTable[c] = currentQuantity + 1
                } else {
                    freqTable[c] = 1
                }
            }
        }
        val lookupTable = mutableMapOf<Char, Char>()
        for (entry in freqTable.entries) {
            when (entry.value) {
                6 -> lookupTable['b'] = entry.key
                4 -> lookupTable['e'] = entry.key
                9 -> lookupTable['f'] = entry.key
            }
        }
        val one = patterns.first { it.length == 2 }.toSet()
        val four = patterns.first { it.length == 4 }.toSet()
        val seven = patterns.first { it.length == 3 }.toSet()
        val eight = patterns.first { it.length == 7 }.toSet()
        lookupTable['a'] = (seven - one).first()
        lookupTable['c'] = (one - (lookupTable['f']!!)).first()
        lookupTable['d'] = four.subtract(lookupTable.values).first()
        lookupTable['g'] = eight.subtract(lookupTable.values).first()
        val lookupTableReversed = mutableMapOf<Char, Char>()
        for (entry in lookupTable.entries) {
            lookupTableReversed[entry.value] = entry.key
        }
        val digitsEncodedFixed =
            output.split(" ").map { digit -> digit.map { segment -> lookupTableReversed[segment] }.toSet() }
        val digitsDecoded = digitsEncodedFixed
            .map { digitDecoded -> digitsReference.first { ref -> ref.second == digitDecoded }.first }
            .joinToString(separator = "")
        ans += digitsDecoded.toInt()
    }
    return ans
}

private val zero = '0' to setOf('a', 'b', 'c', 'e', 'f', 'g')
private val one = '1' to setOf('c', 'f')
private val two = '2' to setOf('a', 'c', 'd', 'e', 'g')
private val three = '3' to setOf('a', 'c', 'd', 'f', 'g')
private val four = '4' to setOf('b', 'c', 'd', 'f')
private val five = '5' to setOf('a', 'b', 'd', 'f', 'g')
private val six = '6' to setOf('a', 'b', 'd', 'e', 'f', 'g')
private val seven = '7' to setOf('a', 'c', 'f')
private val eight = '8' to setOf('a', 'b', 'c', 'd', 'e', 'f', 'g')
private val nine = '9' to setOf('a', 'b', 'c', 'd', 'f', 'g')

private val digitsReference = listOf(zero, one, two, three, four, five, six, seven, eight, nine)
