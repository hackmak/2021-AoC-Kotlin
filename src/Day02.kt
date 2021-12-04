fun main() {
    // part 1
    fun calculatePosition(input: List<String>): Int {
        var horizontalPosition = 0;
        var depth = 0
        for (command in input) {
            // given commands are space separated
            val splitCommand = command.split(" ")
            val direction = splitCommand[0]
            val change = splitCommand[1].toInt()

            when(direction) {
                "forward" -> horizontalPosition += change
                "down" -> depth += change
                "up" -> depth -= change
                else -> {
                    print("invalid input given")
                }
            }
        }
        return horizontalPosition * depth
    }

    // test if implementation meets criteria from the description
    val testInput = readInput("Day02_test")
    check(calculatePosition(testInput) == 150)

    // use actual input
    val input = readInput("Day02")
    println(calculatePosition(input))
}
