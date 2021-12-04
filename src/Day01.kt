fun main() {
    // part 1
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

    // part 2
    fun countSumIncreases(input: List<String>): Int {
        var prevSum: Int? = null
        var counter = 0

        // traverse list once
        for (i in input.indices) {
            // only calculate sums until the last group of 3
            if (i <= input.size - 3) {
                val currentSum = input[i].toInt() + input[i+1].toInt() + input[i+2].toInt()
                if (prevSum != null && prevSum < currentSum) {
                    counter += 1
                }
                prevSum = currentSum
            } else {
                break
            }
        }
        return counter
    }

    // test if implementation meets criteria from the description
    check(countSumIncreases(testInput) == 5)

    // use actual input
    println(countSumIncreases(input))
}
