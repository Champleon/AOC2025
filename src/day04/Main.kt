package day04

import java.io.File
import java.io.InputStream

fun main() {
    val inputStream: InputStream = File("inputDay4.txt").inputStream()
    val inputLines = mutableListOf<String>()
    inputStream.bufferedReader().forEachLine { inputLines.add(it) }

    println("The solution for part 1 is ${part1(inputLines)}")
    println("The solution for part 2 is ${part2(inputLines)}")
}

fun part1(inputLines: MutableList<String>): Int {
    var numberOfAccessibleRolls = 0
    for (row in 0..inputLines.size - 1) {
        for (col in 0..inputLines[0].length - 1) {
            if (inputLines[row][col] == '@' && checkAdjacentPositions(row, col, inputLines)) {
                numberOfAccessibleRolls++
            }
        }
    }
    return numberOfAccessibleRolls
}

fun part2(inputLines: MutableList<String>): Int {
    var numberOfPreviousAccessibleRolls = -1
    var numberOfAccessibleRolls = 0

    while (numberOfAccessibleRolls != numberOfPreviousAccessibleRolls) {
        numberOfPreviousAccessibleRolls = numberOfAccessibleRolls
        for (row in 0..inputLines.size - 1) {
            for (col in 0..inputLines[0].length - 1) {
                if (inputLines[row][col] == '@' && checkAdjacentPositions(row, col, inputLines)) {
                    numberOfAccessibleRolls++
                    val newLine = StringBuilder(inputLines[row])
                    newLine[col] = '.'
                    inputLines[row] = newLine.toString()
                }
            }
        }
    }
    return numberOfAccessibleRolls
}

private fun checkAdjacentPositions(row: Int, col: Int, grid: MutableList<String>): Boolean {
    var countRolls = 0
    val coords = listOf(
        Pair(row - 1, col - 1),
        Pair(row - 1, col),
        Pair(row - 1, col + 1),
        Pair(row, col - 1),
        Pair(row, col + 1),
        Pair(row + 1, col - 1),
        Pair(row + 1, col),
        Pair(row + 1, col + 1)
    )

    for (coordinate in coords) {
        if (isPositionValid(coordinate.first, coordinate.second, grid)) {
            if (grid[coordinate.first][coordinate.second] == '@') {
                countRolls++
            }
        }
    }
    return countRolls < 4
}

private fun isPositionValid(row: Int, col: Int, grid: MutableList<String>): Boolean {
    return !(row < 0 || col < 0 || row >= grid.size || col >= grid[0].length)
}