package day03

import java.io.File
import java.io.InputStream

fun main() {
    val inputStream: InputStream = File("inputDay3.txt").inputStream()
    val inputLines = mutableListOf<String>()
    inputStream.bufferedReader().forEachLine { inputLines.add(it) }

    println("The solution for part 1 is ${part1(inputLines)}")
    println("The solution for part 2 is ${part2(inputLines)}")


}

fun part1(inputLines: MutableList<String>): Int {
    val listOfJoltages = mutableListOf<Int>()
    for (line in inputLines) {
        val firstIndex = firstIndexOfLargestNumber(line)
        val firstNumber = line[firstIndex]
        val sub = line.substring(firstIndex+1)
        val secondNumberAsChar = maxNumberInString(sub)
        listOfJoltages.add("$firstNumber$secondNumberAsChar".toInt())
    }
    return listOfJoltages.sum()
}

fun part2(inputLines: MutableList<String>): Long {
    val listOfJoltages = mutableListOf<Long>()
    for (line in inputLines) {
        var joltage = ""
        val firstSubString = line.substring(0, line.length-11)
        val firstIndex = firstIndexOfLargestNumber(firstSubString, isPartTwo = true)
        val firstNumber = line[firstIndex]
        joltage += firstNumber.toString()
        var sub = line.substring(firstIndex+1)
        var i = 10
        repeat(11) {
            val secondSubString = sub.substring(0, sub.length-i)
            i--
            val secondIndex = firstIndexOfLargestNumber(secondSubString, isPartTwo = true)
            val secondNumber = sub[secondIndex]
            sub = sub.substring(secondIndex+1)
            joltage += secondNumber.toString()
        }
        listOfJoltages.add(joltage.toLong())
    }
    return listOfJoltages.sum()
}

private fun firstIndexOfLargestNumber(inputString: String, isPartTwo: Boolean = false) : Int {
    val charsAsInts = mutableListOf<Int>()
    for (char in inputString.trim()) {
        charsAsInts.add(char.digitToInt())
    }
    var indexOfMax = charsAsInts.size - 2
    if(isPartTwo) indexOfMax++
    var maxNumber = charsAsInts[indexOfMax]
    for (i in charsAsInts.size - 1 downTo 1) {
        if (charsAsInts[i - 1] >= maxNumber) {
            indexOfMax = i - 1
            maxNumber = charsAsInts[indexOfMax]
        }
    }
    return indexOfMax
}

private fun maxNumberInString(inputString: String): Int {
    val charsAsInts = mutableListOf<Int>()
    for (char in inputString.trim()) {
        charsAsInts.add(char.digitToInt())
    }
    var maxNumber = 0
    for (number in charsAsInts) {
        if (number > maxNumber) maxNumber = number
    }
    return maxNumber
}