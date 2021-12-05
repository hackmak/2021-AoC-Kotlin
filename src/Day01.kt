fun main() {
    // part 1
    fun countDepthIncreases(input: List<Int>): Int {
        return input.filterIndexed { i, _ ->
            if (i != 0)
                // check if current value is greater than previous
                input[i] > input[i - 1]
            else false
        }.size
    }

    // test if implementation meets criteria from the description
    val testInput = readInputAsInt("Day01_test")
    check(countDepthIncreases(testInput) == 7)

    // use actual input
    val input = readInputAsInt("Day01")
    println(countDepthIncreases(input))

    // part 2
    fun countSumIncreases(input: List<Int>): Int {
        // create new list of sums
        val sumList = ArrayList<Int>()
        for (i in 0..input.size - 3) {
            // check if sum of next 3 values starting with current index is greater than the sum from the previous index
            sumList.add(i, (input[i] + input[i + 1] + input[i + 2]))
        }
        return countDepthIncreases(sumList)
    }
    // test if implementation meets criteria from the description
    check(countSumIncreases(testInput) == 5)

    // use actual input
    println(countSumIncreases(input))
}
