fun main() {
    // part 1
    fun binaryToDecimal(input: String): Int {
        return input.toInt(2)
    }

    fun calculatePowerConsumption(input: List<String>): Int {
        // FIXME make functional by using reduce or fold?
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

    // part 2
    fun updateMostCommonList(input: List<String>, bitPosition: Int, mostCommonBit: Char): ArrayList<String> {
        val mostCommonList = ArrayList<String>()
        for (binary in input) {
            if (binary[bitPosition] == mostCommonBit) {
                mostCommonList.add(binary)
            }
        }
        return mostCommonList
    }

    fun getOxygenRating(input: List<String>, zeroCounter: Int, bitPosition: Int): ArrayList<String> {
        return if (2 * zeroCounter > input.size) {
            // 0 is the most common bit
            updateMostCommonList(input, bitPosition, '0')
        } else {
            // 1 is the most common bit or they are equal
            updateMostCommonList(input, bitPosition, '1')
        }
    }

    fun updateLeastCommonList(input: List<String>, bitPosition: Int, leastCommonBit: Char): ArrayList<String> {
        val leastCommonList = ArrayList<String>()
        for (binary in input) {
            if (binary[bitPosition] == leastCommonBit) {
                leastCommonList.add(binary)
            }
        }
        return leastCommonList
    }

    fun getScrubberRating(input: List<String>, zeroCounter: Int, bitPosition: Int): ArrayList<String> {
        return if (2 * zeroCounter <= input.size) {
            // 0 is the least common bit or they are equal
            updateLeastCommonList(input, bitPosition, '0')
        } else {
            // 1 is the least common bit
            updateLeastCommonList(input, bitPosition, '1')
        }
    }

    fun calculateLifeSupportRating(input: List<String>): Int {
        var oxygenRating = ""
        var scrubberRating = ""
        val frequencyArray = Array(input[0].length) { 0 }
        var mostCommonList = input
        var leastCommonList = input
        var zeroCounter = 0

        for (i in 0 until input[0].length) {
            // loop until last value or only 1 value left
            for (binary in mostCommonList) {
                if (binary[i] == '0') {
                    zeroCounter += 1
                }
            }
            // calculate most frequent bit in current position
            mostCommonList = getOxygenRating(mostCommonList, zeroCounter, i)
            zeroCounter = 0

            if (mostCommonList.size == 1) {
                oxygenRating = mostCommonList[0]
                break
            }
        }

        for (i in 0 until input[0].length) {
            // loop until last value or only 1 value left
            for (binary in leastCommonList) {
                if (binary[i] == '0') {
                    zeroCounter += 1
                }
            }
            // calculate least frequent bit in current position
            leastCommonList = getScrubberRating(leastCommonList, zeroCounter, i)
            zeroCounter = 0

            if (leastCommonList.size == 1) {
                scrubberRating = leastCommonList[0]
                break
            }
        }

        return binaryToDecimal(oxygenRating) * binaryToDecimal(scrubberRating)
    }

    // test if implementation meets criteria from the description
    check(calculateLifeSupportRating(testInput) == 230)

    // use actual input
    println(calculateLifeSupportRating(input))
}
