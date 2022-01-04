fun main() {
    fun part1(input: List<String>): Int {
        val commands = input.map {
            val directionToSteps = it.split(" ")
            val steps = directionToSteps[1].toInt()
            when (directionToSteps[0]) {
                Command.Horizontal.Forward.STRING_REPRESENTATION -> Command.Horizontal.Forward(steps)
                Command.Vertical.Up.STRING_REPRESENTATION -> Command.Vertical.Up(steps)
                Command.Vertical.Down.STRING_REPRESENTATION -> Command.Vertical.Down(steps)
                else -> throw IllegalArgumentException("Direction unknown")
            }
        }
        val horizontalPosition = commands.filterIsInstance<Command.Horizontal.Forward>().sumOf { it.steps }
        val depth = commands.filterIsInstance<Command.Vertical>().fold(0) { acc, command ->
            when (command) {
                is Command.Vertical.Down -> acc + command.steps
                is Command.Vertical.Up -> acc - command.steps
            }
        }
        return horizontalPosition * depth
    }

    fun part2(input: List<String>): Int {
        var horizontalPosition = 0
        var depth = 0
        var aim = 0
        input.forEach { line ->
            val commandToSteps = line.split(" ")
            val command = commandToSteps[0]
            val steps = commandToSteps[1].toInt()
            when (command) {
                FORWARD_COMMAND -> {
                    horizontalPosition += steps
                    depth += aim * steps
                }
                UP_COMMAND -> aim -= steps
                DOWN_COMMAND -> aim += steps
            }
        }
        return horizontalPosition * depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

private sealed class Command {
    abstract val steps: Int

    sealed class Horizontal : Command() {
        data class Forward(override val steps: Int) : Horizontal() {
            companion object {
                const val STRING_REPRESENTATION = "forward"
            }
        }
    }

    sealed class Vertical : Command() {
        data class Up(override val steps: Int) : Vertical() {
            companion object {
                const val STRING_REPRESENTATION = "up"
            }
        }

        data class Down(override val steps: Int) : Vertical() {
            companion object {
                const val STRING_REPRESENTATION = "down"
            }
        }
    }
}

private const val FORWARD_COMMAND = "forward"
private const val UP_COMMAND = "up"
private const val DOWN_COMMAND = "down"
