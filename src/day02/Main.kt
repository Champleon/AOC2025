package day02

import java.io.File

fun main()
{
    val inputLine: String = File("inputDay2.txt").readText()

    println("The solution for part 1 is ${part1(inputLine)}")
    println("The solution for part 2 is ${part2(inputLine)}")
}

private fun part1(inputLine: String): Long {
    val ranges = inputLine.split(",")
    val invalidIds = mutableListOf<Long>()
    for (range in ranges) {
        val ids = range.split("-")
        val firstID = ids[0].trim().toLong()
        val lastID = ids[1].trim().toLong()

        for (i in firstID..lastID) {
            val iAsString = i.toString().trim()
            val firstHalf = iAsString.substring(0, iAsString.length/2)
            val secondHalf = iAsString.substring(iAsString.length/2)
            if (firstHalf == secondHalf) invalidIds.add(i)
        }
    }
    return invalidIds.sum()
}

private fun part2(inputLine: String): Long {
    val ranges = inputLine.split(",")
    val invalidIds = mutableListOf<Long>()
    for (range in ranges) {
        val ids = range.split("-")
        val firstID = ids[0].trim().toLong()
        val lastID = ids[1].trim().toLong()

        for (i in firstID..lastID) {
            val iAsString = i.toString().trim()
            for (len in 1..iAsString.length) {
                if (iAsString.length % len == 0) {
                    val evenlySplitStrings = iAsString.chunked(len)
                    if (evenlySplitStrings.size > 1 && evenlySplitStrings.distinct().size == 1) {
                        invalidIds.add(i)
                        break
                    }
                }
            }
        }
    }
    return invalidIds.sum()
}