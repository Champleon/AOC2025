package day07

import java.io.File
import java.io.InputStream

fun main() {
    val inputStream: InputStream = File("inputDay7.txt").inputStream()
    val inputLines = mutableListOf<String>()
    inputStream.bufferedReader().forEachLine { inputLines.add(it) }

    println("The solution for part 1 is: ${part1(inputLines)}")
    println("The solution for part 2 is: ${part2(inputLines)}")
}

fun part1(inputLines: List<String>): Int {
    val index = inputLines[0].indexOf('S')
    val grid = inputLines.map { it.toCharArray() }.toMutableList()
    var splits = 0

    val stack = ArrayDeque<Pair<Int, Int>>()
    stack.add(1 to index)

    while (stack.isNotEmpty()) {
        val (r, c) = stack.removeLast()
        if (!areCoordsValid(inputLines, r, c)) continue
        val currentChar = grid[r][c]
        if (currentChar == '|' || currentChar == 'x') continue

        when (currentChar) {
            '.' -> {
                grid[r][c] = '|'
                stack.add(r + 1 to c)
            }

            '^' -> {
                splits++
                grid[r][c] = 'x'
                stack.add(r to c - 1)
                stack.add(r to c + 1)
            }

            else -> println("Should never happen")
        }
    }
    return splits
}

fun part2(inputLines: List<String>): Long {
    val index = inputLines[0].indexOf('S')
    val grid = inputLines.map { it.toCharArray() }.toMutableList()
    val memo = HashMap<Pair<Int, Int>, Long>()
    return countTimeLines(grid, 1, index, memo)
}

private fun countTimeLines(
    grid: MutableList<CharArray>,
    row: Int,
    col: Int,
    memo: HashMap<Pair<Int, Int>, Long>
): Long {
    if (col < 0 || col >= grid[0].size) return 0
    if (row >= grid.size) return 1

    memo[row to col]?.let { return it }

    val currentChar = grid[row][col]
    val result = when (currentChar) {
        '^' -> {
            countTimeLines(grid, row, col - 1, memo) + countTimeLines(grid, row, col + 1, memo)
        }

        else -> {
            countTimeLines(grid, row + 1, col, memo)
        }
    }
    memo[row to col] = result
    return result
}

private fun areCoordsValid(inputLines: List<String>, row: Int, col: Int): Boolean {
    return (row >= 0) && (col >= 0) && (row < inputLines.size) && (col < inputLines[0].length)
}