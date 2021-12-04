fun main() {
    fun countDepthIncreases(input: List<String>): Int {
        var prev: Int? = null
        var counter = 0
        for (measurement in input) {
            val current = measurement.toIntOrNull() // assert not null
            if (prev != null && current != null && prev < current) {
                counter += 1
            }
            prev = current
        }
        return counter
    }

    // test if implementation meets criteria from the description
    val testInput = readInput("Day01_test")
    check(countDepthIncreases(testInput) == 7)

    // use actual input
    val input = readInput("Day01")
    println(countDepthIncreases(input))
}
