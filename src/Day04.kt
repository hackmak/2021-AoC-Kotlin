fun main() {
    // part 1
    data class BingoObject (val number: Int, val isMarked: Boolean)

    fun markBingoBoard(bingoNumber: Int, currentBoard: List<List<BingoObject>>): List<List<BingoObject>> {
        return currentBoard.map { bingoList ->
            bingoList.map { bingoObject ->
                if (bingoObject.number == bingoNumber)
                    BingoObject(bingoObject.number, true)
                else bingoObject
            }
        }
    }

    fun calculateScore(winningBoard: List<List<BingoObject>>, lastNumberCalled: Int): Int {
        return winningBoard.flatten().filter {  bingoObject ->
            // collect all unmarked numbers
            !bingoObject.isMarked
        }.fold(0) { sum, unmarkedObj ->
            sum + unmarkedObj.number
        } * lastNumberCalled
    }

    fun isWinningBoard (currentBoard: List<List<BingoObject>>): Boolean {
        // check for horizontal wins
        for (row in currentBoard) {
            for (i in row.indices) {
                if (row[i].isMarked) {
                    if (i == row.lastIndex) {
                        return true
                    }
                } else {
                    // if any are not marked, break
                    break
                }
            }
        }

        // check first row for vertical wins
        val firstRow = currentBoard[0]
        for (i in firstRow.indices) {
            for (j in 1 until currentBoard.size) { // skip first row
                if (firstRow[i].isMarked) {
                    if (currentBoard[j][i].isMarked) {
                        if (j == currentBoard.size - 1) {
                            return true
                        }
                    } else {
                        break
                    }
                } else {
                    // if any are not marked, break
                    break
                }
            }
        }
        return false
    }

    fun checkPotentialWinningBoard(markedBoards: List<List<List<BingoObject>>>, draw: Int): Pair<Int, List<List<List<BingoObject>>>> {
        val winners = markedBoards.filter { markedBoard ->
            isWinningBoard(markedBoard)
        }
        return if (winners.isNotEmpty())
            Pair(calculateScore(winners.first(), draw), winners)
        else
            Pair(0, markedBoards)
    }

    fun getWinningBoard(numberDraws: List<Int>, allBoards: List<List<List<BingoObject>>>, draw: Int): Pair<Int, List<List<List<BingoObject>>>> {
        // go through each number drawn and mark each bingo board if they have the value
        val markedBoards = allBoards.map { board ->
            markBingoBoard(draw, board)
        }
        // check if each board wins after 5+ moves
        return if (numberDraws.indexOf(draw) < 5)
            Pair(0, markedBoards)
        else
            checkPotentialWinningBoard(markedBoards, draw)
    }

    fun inputToBingoBoards(filteredInput: List<String>, allBoards: List<List<List<BingoObject>>>): List<List<List<BingoObject>>> {
        return if (filteredInput.size < 5) {
            allBoards
        } else {
            inputToBingoBoards(filteredInput.drop(5), allBoards.plusElement(filteredInput.take(5).map { line ->
                // split by whitespace
                line.split("\\s+".toRegex()).map { bingoNumber ->
                    BingoObject(bingoNumber.toInt(), false)
                }
            }))
        }
    }

    fun calculateBingoScore(input: List<String>): Int {
        // number of draws is first line of input
        val numberDraws = input[0].split(",").map { draw -> draw.toInt() }

        // filter input to get rid of starting whitespace, number draws, and new lines
        val filteredInput = input.map { line -> line.trim() }
            .filterIndexed { i, line ->
                i != 0 && line != "" // ignore first line which is number draws and any new lines
            }

        // get all bingo tables and add to a list
        val allBoards = inputToBingoBoards(filteredInput, ArrayList())

        return numberDraws.fold(Pair(0, allBoards)) { pair, draw -> // need pair to keep track of marked boards and score
            val (winningScore, boards) = pair
            if (winningScore > 0)
                return@fold pair
            else
                // check if each board wins after 5+ moves
                return@fold getWinningBoard(numberDraws, boards, draw)
        }.first // first element is score
    }

    // test if implementation meets criteria from the description
    val testInput = readInput("Day04_test")
    check(calculateBingoScore(testInput) == 4512)

    val verticalTest = readInput("Day04_test1")
    check(calculateBingoScore(verticalTest) == 3280)

    // use actual input
    val input = readInput("Day04")
    println(calculateBingoScore(input))
}