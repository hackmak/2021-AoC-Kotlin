fun main() {
    // part 1
    fun countDepthIncreases(input: List<String>): Int {
        return input.filterIndexed { i, _ ->
            if (i != 0)
                input[i].toInt() > input[i - 1].toInt()
            else false
        }.size
    }

    // test if implementation meets criteria from the description
    val testInput = readInput("Day01_test")
    check(countDepthIncreases(testInput) == 7)

    // use actual input
    val input = readInput("Day01")
    println(countDepthIncreases(input))

    // part 2
    fun countSumIncreases(input: List<String>): Int {
        return input.filterIndexed { i, _ ->
            if (i != 0 && i + 2 < input.size)
                // start with second group of 3
                input[i].toInt() + input[i + 1].toInt() + input[i + 2].toInt() > input[i - 1].toInt() + input[i].toInt() + input[i + 1].toInt()
            else false
        }.size
    }
    // test if implementation meets criteria from the description
    check(countSumIncreases(testInput) == 5)

    // use actual input
    println(countSumIncreases(input))
}
