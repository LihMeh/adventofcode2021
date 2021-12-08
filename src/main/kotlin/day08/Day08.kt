package day08

data class Entry(val signalPatterns: List<Set<Char>>, val outputValue: List<Set<Char>>)

fun parseInput(input: String): List<Entry> {
    return input.split("\n")
        .flatMap { row -> row.split("|") }
        .map { row ->
            row.split(" ")
                .filter { value -> value.isNotBlank() }
                .map { value -> value.toCharArray().toSet() }
        }
        .windowed(2, 2)
        .map { pair -> Entry(pair[0], pair[1]) }
        .toList()
}

fun task1(input: String): Int {
    val entries = parseInput(input)
    val expectedSegmentCounts = setOf(2, 3, 4, 7)
    return entries
        .flatMap { it.outputValue }
        .count { expectedSegmentCounts.contains(it.size) }
}

fun task2(input: String): Int {
    val entries = parseInput(input)
    return entries
        .map { predictOutputValue(it) }
        .sum()
}

/*
В самом начале, мы знаем, что:
есть 2 сегмента - это 1
есть 4 сегмента - это 4
есть 3 сегмента - это 7
есть 7 сегментов - это 8

0 - 8 минус (пересечение 4 и всех, у кого 5 сегментов)
1 - где 2 сегмента
2 (3 - 1) + (6-5) + (8 - 6)
3 - пересечь всех, у кого 5 сегментов  и прибавить 1
4 - где 4 сегмента
5 - пересечение 6 и 9
6 - 6 сегментов, но это не 0 и не 9
7 - где 3 сегмента
8 - где 7 сегментов
9 - на 1 сегмент бьольше, чем в 4+7
 */

fun predictOutputValue(entry: Entry): Int {
    val digitToChars = mutableMapOf<Int, Set<Char>>()
    digitToChars.put(
        1, // цифра 1 - где 2 сегмента
        entry.signalPatterns.find { it.size == 2 }!!
    )
    digitToChars.put(
        4, // цифра 4 - где 4 сегмента
        entry.signalPatterns.find { it.size == 4 }!!
    )
    digitToChars.put(
        7, // цифра 7 - где 3 сегмента
        entry.signalPatterns.find { it.size == 3 }!!
    )
    digitToChars.put(
        8, // цифра 8 - где 7 сегментов
        entry.signalPatterns.find { it.size == 7 }!!
    )
    digitToChars.put(
        3, // 3 - пересечь всех, у кого 5 сегментов  и прибавить 1
        entry.signalPatterns
            .filter { it.size == 5 }
            .reduce({ left, right -> left.intersect(right) })
            .union(digitToChars[1]!!)
    )
    digitToChars.put(
        0, // 0 - 8 минус (пересечение 4 и всех, у кого 5 сегментов)
        digitToChars[8]!!.minus(
            entry.signalPatterns
                .filter { it.size == 5 }
                .reduce({ left, right -> left.intersect(right) })
                .intersect(digitToChars[4]!!)
        )
    )
    val d4andd7 = digitToChars[4]!!.union(digitToChars[7]!!)
    digitToChars.put(
        9, //    9 - на 1 сегмент бьольше, чем в 4+7
        entry.signalPatterns
            .filter { it.size == 6 }
            .find { it.minus(d4andd7).size == 1 }!!
    )
    digitToChars.put(
        6, //    6 - 6 сегментов, но это не 0 и не 9
        entry.signalPatterns
            .filter { !it.equals(digitToChars[0]!!) }
            .filter { !it.equals(digitToChars[9]!!) }
            .find { it.size == 6 }!!
    )
    digitToChars.put(
        5, //    5 - пересечение 6 и 9
        digitToChars[6]!!.intersect(digitToChars[9]!!)
    )
    digitToChars.put(
        2, //    2 = (3 - 1) + (6-5) + (8 - 6)
        digitToChars[3]!!.minus(digitToChars[1]!!)
            .plus(digitToChars[6]!!.minus(digitToChars[5]!!))
            .plus(digitToChars[8]!!.minus(digitToChars[6]!!))
    )

    val charsToDigit = digitToChars
        .map { pair -> pair.value to pair.key }
        .toMap()

    var result = 0
    var currentPosValue = 1
    for (digitChars in entry.outputValue.reversed()) {
        val digit = charsToDigit[digitChars]!!
        result += (digit * currentPosValue)
        currentPosValue *= 10
    }

    return result
}