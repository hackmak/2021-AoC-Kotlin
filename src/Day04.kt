fun main() {
    // part 1
    class BingoObject constructor(number: Int, isMarked: Boolean) {
        var number = number
            get() = field
            set(value) {
                field = value
            }

        var isMarked = isMarked
            get() = field
            set(value) {
                field = value
            }
    }

    fun calculateBingoScore(input: List<String>): Int {
        // number of draws is first line of input
        val numberDraws = input[0].split(",").map { draw -> draw.toInt() }
        val allBoards = ArrayList<List<List<BingoObject>>>()

        // filter input to get rid of starting whitespace, number draws, and new lines
        var filteredInput = input.map { line -> line.trim() }
            .filterIndexed { i, line ->
                i != 0 && line != "" // ignore first line which is number draws and any new lines
            }

        // get all bingo tables and add to a list
        while(filteredInput.size >= 5) {
            // get first 5 elements
            val bingoBoard = filteredInput.take(5).map {
                line -> line.split("\\s+".toRegex()).map {
                    bingoNumber -> BingoObject(bingoNumber.toInt(), false)
                } //.toTypedArray()
            } //.toTypedArray()

            println(bingoBoard)
            allBoards.add(bingoBoard)

            // remove first 5 elements
            filteredInput = filteredInput.drop(5)
        }

        // go through each number drawn and mark each bingo board if they have the value

        // check if each board wins after 5+ moves
        return 0
    }

    // test if implementation meets criteria from the description
    val testInput = readInput("Day04_test")
    check(calculateBingoScore(testInput) == 4512)

}