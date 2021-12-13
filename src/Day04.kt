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

    fun markBingoBoards(bingoNumber: Int, allBoards: List<List<List<BingoObject>>>): List<List<List<BingoObject>>> {
        return allBoards.map { board ->
            markBingoBoard(bingoNumber, board)
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
        // todo make this functional
        // check first column for horizontal wins
        for (row in currentBoard) {
            for (i in row.indices) {
                if ((i == 0 || i == row.lastIndex) && row[i].isMarked) {
                    // continue
                } else if (i == row.lastIndex && row[i].isMarked) {
                    // calculate score
                    return true
                } else {
                    // if any are not marked, break
                    break
                }
            }
        }

        // only need to check first row for vertical wins
        val firstRow = currentBoard[0]
        for (i in firstRow.indices) {
            for (j in 1 until currentBoard.size) { // skip first row
                if (firstRow[i].isMarked) {
                    // continue
                    if (currentBoard[j][i].isMarked) {
                        if (j == currentBoard.size - 1) {
                            // vertical win
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

    fun getWinningBoard(allBoards: List<List<List<BingoObject>>>, draw: Int): Pair<Int, List<List<List<BingoObject>>>> {
        val markedBoards = allBoards.map { board ->
            markBingoBoard(draw, board)
        }
        val winners = markedBoards.filter { markedBoard ->
            isWinningBoard(markedBoard)
        }
        return if (winners.isNotEmpty())
            Pair(calculateScore(winners.first(), draw), winners)
        else
            Pair(0, markedBoards)
    }

    fun calculateBingoScore(input: List<String>): Int {
        // number of draws is first line of input
        val numberDraws = input[0].split(",").map { draw -> draw.toInt() }

        // filter input to get rid of starting whitespace, number draws, and new lines
        var filteredInput = input.map { line -> line.trim() }
            .filterIndexed { i, line ->
                i != 0 && line != "" // ignore first line which is number draws and any new lines
            }

        val allBoards = ArrayList<List<List<BingoObject>>>()
        // get all bingo tables and add to a list
        while(filteredInput.size >= 5) {
            // get first 5 elements
            allBoards.add(filteredInput.take(5).map {
                line -> line.split("\\s+".toRegex()).map { // split by whitespace
                    bingoNumber -> BingoObject(bingoNumber.toInt(), false)
                }
            })
            // remove first 5 elements
            filteredInput = filteredInput.drop(5)
        }

        val boardListAll = allBoards.toList() // fixme will need to set allBoards functionally

        // go through each number drawn and mark each bingo board if they have the value
        val test0 = numberDraws.fold(Pair(0, boardListAll)) { pair, draw ->
            val (winningScore, boards) = pair
            if (winningScore > 0)
                return@fold pair
            else
                // check if each board wins todo after 5+ moves
                return@fold getWinningBoard(boards, draw)
        }
        val test1 = numberDraws.fold(boardListAll) { boardList, number ->
            boardList.map { board ->
                markBingoBoard(number, board)
            }
        }
        val allMarkedBoards = numberDraws.map { number ->
            boardListAll.fold(boardListAll) { boardList, board ->
               return@fold markBingoBoards(number, boardListAll)
            }
        }

        val test2 = numberDraws.mapIndexed { i, number ->
            println(number)
            boardListAll.fold(boardListAll) { boardList, board ->
                return@fold markBingoBoards(number, boardList)
            }
        }

        // TODO recursion??
        // check each board to see if it's a win
        // FIXME this is inefficient, find a better way?? calculating the win twice.
//        return calculateScore(allMarkedBoards.first { board -> isWinningBoard(board) },
//            allMarkedBoards.indexOfFirst { board ->
//                // check if each board wins todo after 5+ moves
//                isWinningBoard(board)
//        })
        return 0
    }

    // test if implementation meets criteria from the description
    val testInput = readInput("Day04_test")
    check(calculateBingoScore(testInput) == 4512)
}