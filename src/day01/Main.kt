package day01

import java.io.File
import java.io.InputStream

fun main() {
    println("The solution for part 1 is: ${part1()}")
    println("The solution for part 2 is: ${part2()}")
}

private fun part1(): Int {
    val inputStream: InputStream = File("inputDay1.txt").inputStream()
    val inputLines = mutableListOf<String>()
    var position = 50
    var count = 0

    inputStream.bufferedReader().forEachLine { inputLines.add(it) }
    inputLines.forEach { line ->
        if (line.first() == 'L') {
            position -= line.replace("L", "").trimIndent().toInt()
            position = Math.floorMod(position, 100)
        } else if (line.first() == 'R') {
            position += line.replace("R", "").trimIndent().toInt()
            position = Math.floorMod(position, 100)
        }
        if (position == 0) count++
    }
    return count
}

private fun part2(): Int {
    val inputStream: InputStream = File("inputDay1.txt").inputStream()
    val inputLines = mutableListOf<String>()

    var position = 50
    var count = 0

    inputStream.bufferedReader().forEachLine { inputLines.add(it) }
    inputLines.forEach { line ->
        val steps = line.substring(1).trimIndent().toInt()
        repeat(steps) {
            if (line.first() == 'L') {
                position--
                if (position < 0) position = Math.floorMod(position, 100)
            }
            else if (line.first() =='R') {
                position++
                if (position >= 100) position = Math.floorMod(position, 100)
            }
            if (position == 0) count++
        }
    }
    return count
}