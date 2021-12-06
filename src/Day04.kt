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

    fun isWinningBoard(currentBoard: List<List<BingoObject>>) {
        // todo will need to calculate score
        currentBoard.forEach { bingoList ->
            bingoList.map {
                // check horizontal wins first

            }
        }
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
//        val test = numberDraws.mapIndexed { i, number ->
//            println(number)
//            boardListAll.fold(boardListAll) { boardList, board ->
//                return@fold markBingoBoards(number, boardList)
//            }
//
//        }

        // go through each number drawn and mark each bingo board if they have the value
        val test1 = numberDraws.fold(boardListAll) { boardList, number ->
                boardList.map { board ->
                    markBingoBoard(number, board)
                    // check if each board wins todo after 5+ moves

                }
        }
        println(test1)
//        for (i in numberDraws.indices) {
//            allBoards.map { board ->
//                board.fold(board) { _, _ ->
//                    markBingoBoard(numberDraws[i], board)
//                }
//            }

            //allBoards.fold(allBoards.map { board -> board.fold(board) {markBingoBoard(numberDraws[i], board) } })


//            if (i >= 4) {
//                // check if each board wins after 5+ moves
//
//            }

        return 0
    }

    // test if implementation meets criteria from the description
    val testInput = readInput("Day04_test")
    check(calculateBingoScore(testInput) == 4512)

}