package day06

import java.util.*

class Fish(var age: Int) {
    init {
        check(age > 0)
    }

    fun liveDays(days: Int) {
        check(days <= age)
        age -= days
    }

    fun resetDays() {
        check(age == 0)
        age = 6
    }
}

fun parseInput(input: String): List<Fish> {
    return input.splitToSequence(",")
        .filter { it.isNotBlank() }
        .map { Fish(it.toInt()) }
        .toList()
}

fun task1(input: String, daysToPass: Int): Int {
    val fishList = LinkedList(parseInput(input))
    check(fishList.isNotEmpty())

    var daysLeft = daysToPass
    while (daysLeft > 0) {
        var newFishesCount = 0
        for (fish in fishList) {
            if (fish.age > 0) {
                fish.liveDays(1)
            } else {
                fish.resetDays()
                newFishesCount++
            }
        }
        generateSequence { Fish(8) }
            .take(newFishesCount)
            .forEach { fishList.addFirst(it) }
        daysLeft--
    }

    return fishList.size
}