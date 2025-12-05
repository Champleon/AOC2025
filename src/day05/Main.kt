package day05

import java.io.File
import java.io.InputStream

fun main() {
    val inputStream: InputStream = File("inputDay5.txt").inputStream()
    val freshRanges = mutableListOf<String>()
    val availableIngredients = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine {
        if (it.contains('-')) {
            freshRanges.add(it)
        } else if (it.isNotEmpty()) {
            availableIngredients.add(it)
        }
    }
    println("The solution of part 1 is: ${part1(freshRanges, availableIngredients)}")
    println("The solution of part 2 is: ${part2(freshRanges)}")
}

fun part1(freshRanges: List<String>, availableIngredients: List<String>): Int {
    var count = 0
    var ranges = freshRanges.map {
        val (s, e) = it.split("-")
        s.toLong() to e.toLong()
    }
    ranges = ranges.sortedBy { it.first }
    val mergedRanges = mergeOverlaps(ranges)

    for (ingredient in availableIngredients) {
        val value = ingredient.toLong()
        var low = 0
        var high = mergedRanges.size - 1

        while (low <= high) {
            val mid = (low + high) / 2
            val (start, end) = mergedRanges[mid]

            if (value < start) {
                high = mid - 1
            } else if (value > end) {
                low = mid + 1
            } else {
                count++
                break
            }
        }
    }
    return count
}

fun part2(freshRanges: List<String>): Long{
    var count: Long = 0
    var ranges = freshRanges.map {
        val (s, e) = it.split("-")
        s.toLong() to e.toLong()
    }
    ranges = ranges.sortedBy { it.first }
    val mergedRanges = mergeOverlaps(ranges)
    for (range in mergedRanges) {
        count += range.second - range.first + 1
    }
    return count
}

private fun mergeOverlaps(ranges: List<Pair<Long, Long>>): List<Pair<Long, Long>> {
    val merged = mutableListOf<Pair<Long, Long>>()
    var (currentStart, currentEnd) = ranges[0]
    for (i in 1 .. ranges.size-1) {
        val (start, end) = ranges[i]

        if (start <= currentEnd) {
            currentEnd = maxOf(currentEnd, end)
        } else {
            merged.add(currentStart to currentEnd)
            currentStart = start
            currentEnd = end
        }
    }
    merged.add(currentStart to currentEnd)
    return merged
}