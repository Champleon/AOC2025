package day06

import java.io.File
import java.io.InputStream
import kotlin.text.contains

fun main() {
    val inputStream: InputStream = File("inputDay6.txt").inputStream()
    val inputLines = mutableListOf<String>()
    val operatorInput = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine {
        if (it.contains("[0-9]".toRegex())) {
            inputLines.add(it)
        } else if (it.isNotEmpty()) {
            operatorInput.add(it)
        }
    }
    val numbers = mutableListOf<List<Long>>()
    for (line in inputLines) {
        val numberSplit = line.split(" ").filter { it.isNotBlank() }
        numbers.add(numberSplit.map { it.trim().toLong() })
    }

    val operators = operatorInput[0].split(" ").filter { it.isNotBlank() }
    println("The solution of part 1 is: ${part1(numbers, operators)}")
    println("The solution of part 2 is: ${part2(inputLines, operators)}")
}

fun part1(inputNumbers: List<List<Long>>, operators: List<String>): Long {
    val sums: MutableList<Long> = MutableList(operators.size) { 0 }

    for (col in 0..inputNumbers[0].size - 1) {
        val operator = operators[col]
        for (row in 0..inputNumbers.size - 1) {
            when (operator.trim()) {
                "+" -> {
                    sums[col] += inputNumbers[row][col]
                }

                "*" -> {
                    if (sums[col] == 0L) sums[col] = 1
                    sums[col] *= inputNumbers[row][col]
                }

                else -> println("this should never happen")
            }
        }
    }
    return sums.sum()
}

fun part2(inputLines: List<String>, operators: List<String>): Long {
    val sums: MutableList<Long> = MutableList(operators.size) { 0 }

    val columns = splitStringListIntoColumns(inputLines)
    var i = 0

    for (col in columns) {
        val operator = operators[i]

        if (!col.any { it.isDigit() }) {
            i++
        } else {
            var num = ""
            for (char in col) {
                if (char.isDigit()) num += char
            }

            when (operator.trim()) {
                "+" -> {
                    sums[i] += num.toLong()
                }

                "*" -> {
                    if (sums[i] == 0L) sums[i] = 1
                    sums[i] *= num.toLong()
                }

                else -> println("this should never happen")
            }
        }

    }
    return sums.sum()
}

private fun splitStringListIntoColumns(rows: List<String>): List<List<Char>> {
    val columns = mutableListOf<MutableList<Char>>()
    val width = rows.maxOf { it.length }

    for (col in 0..width - 1) {
        val columnChars = mutableListOf<Char>()
        for (row in 0..rows.size - 1) {
            columnChars.add(rows[row][col])
        }
        columns.add(columnChars)
    }
    return columns
}