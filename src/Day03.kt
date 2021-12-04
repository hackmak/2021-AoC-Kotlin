fun main() {
    // part 1
    fun binaryToDecimal(input: String): Int {
        return input.toInt(2)
    }

    fun calculatePowerConsumption(input: List<String>): Int {
        var gammaRate = ""
        var epsilonRate = "" // just gamma flipped
        val frequencyArray = Array(input[0].length) { 0 }

        // count occurrences of 0. We can get the count of 1 by subtracting from the total count
        for (binary in input) {
            for (i in binary.indices) {
                if (binary[i] == '0') {
                    frequencyArray[i] += 1
                }
            }
        }

        for (frequency in frequencyArray) {
            if (input.size - frequency > input.size / 2) {
                // 0 is the most common bit
                gammaRate += '0'
                epsilonRate += '1'
            } else {
                // 1 is the most common bit
                gammaRate += '1'
                epsilonRate += '0'
            }
        }

        return binaryToDecimal(gammaRate) * binaryToDecimal(epsilonRate)
    }

    // test helper method
    check(binaryToDecimal("10110") == 22)

    // test if implementation meets criteria from the description
    val testInput = readInput("Day03_test")
    check(calculatePowerConsumption(testInput) == 198)

    // use actual input
    val input = readInput("Day03")
    println(calculatePowerConsumption(input))
}
