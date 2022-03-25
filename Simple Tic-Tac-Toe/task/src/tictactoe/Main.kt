package tictactoe

const val FIELD = 3
const val EMPTY = ' '
const val X = 'X'
const val O = 'O'

fun main() {
    val ticTacToe = ticTacInitiate()
    fieldPrint(ticTacToe)

    var player = X
    do{
        nextMove(ticTacToe, player)
        if (endOfGame(ticTacToe, player)) break
        player = if (player == X) O else X
    } while (true)
}

fun ticTacInitiate(): MutableList<MutableList<Char>> {
    return MutableList(FIELD) { MutableList(FIELD) { EMPTY } }
}

fun fieldPrint(ticTacToe: MutableList<MutableList<Char>>) {
    println("-".repeat(FIELD + 2 + 2 + 2))
    for (line in ticTacToe) {
        println(line.joinToString(" ", "| ", " |"))
    }
    println("-".repeat(FIELD + 2 + 2 + 2))
}

fun nextMove(ticTacToe: MutableList<MutableList<Char>>, player: Char) {
    val coordinate = inputNextMove(ticTacToe)
    takeCell(ticTacToe, coordinate, player)
    fieldPrint(ticTacToe)
}

fun endOfGame(ticTacToe: MutableList<MutableList<Char>>, player: Char): Boolean {
    return when {
        wins(ticTacToe, player) -> {
            println("$player wins")
            true
        }
        finished(ticTacToe) -> {
            println("Draw")
            true
        }
        else -> false
    }
}

fun wins(ticTacToe: MutableList<MutableList<Char>>, player: Char): Boolean {
    for (i in 0 until FIELD) {
        if (ticTacToe[i].all { it == player }) return true
        if (ticTacToe.filter { it[i] == player }.size == FIELD) return true
    }
    var check = false
    for (i in 0 until FIELD) {
        if (ticTacToe[i][i] != player) {
            check = true
            break
        }
    }
    if (!check) return true
    for (i in 0 until FIELD) {
        if (ticTacToe[FIELD - 1 - i][i] != player && check) return false
    }
    return true
}

fun finished(ticTacToe: MutableList<MutableList<Char>>): Boolean {
    return !ticTacToe.any { it.contains(EMPTY) }
}

fun inputNextMove(ticTacToe: MutableList<MutableList<Char>>): List<Int> {
    var coordinate: List<Int>
    do {
        print("Enter the coordinates: ")
        try {
            coordinate = readln().split(" ").map { it.toInt() - 1 }
        } catch (e: Exception) {
            println("You should enter numbers!")
            continue
        }
        if (!coordinate.all { it in  0 until FIELD}) {
            println("Coordinates should be from 1 to 3!")
            continue
        }
        if (cellIsOccupied(ticTacToe, coordinate)) {
            println("This cell is occupied! Choose another one!")
            continue
        } else {
            return coordinate
        }
    } while (true)
}

fun cellIsOccupied(ticTacToe: MutableList<MutableList<Char>>, coordinate: List<Int>): Boolean {
    if (ticTacToe[coordinate.first()][coordinate.last()] == EMPTY) return false
    return true
}

fun takeCell(ticTacToe: MutableList<MutableList<Char>>, coordinate: List<Int>, player: Char) {
    ticTacToe[coordinate.first()][coordinate.last()] = player
}