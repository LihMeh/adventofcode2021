package day05

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

data class Point2D(val x: Int, val y: Int)

data class Line(val begin: Point2D, val end: Point2D)

fun parseInput(input: String): List<Line> {
    val rowRegex = Regex("(\\d+),(\\d+) -> (\\d+),(\\d+)(\n)?")

    val lines = rowRegex.findAll(input)
        .map { rowMatch ->
            val (x1, y1, x2, y2) = rowMatch.destructured
            Line(Point2D(x1.toInt(), y1.toInt()), Point2D(x2.toInt(), y2.toInt()))
        }
        .toList()

    check(lines.all {
        it.begin.x == it.end.x
                || it.begin.y == it.end.y
                || abs(it.end.x - it.begin.x) == abs(it.end.y - it.begin.y)
    })

    return lines
}

fun task1(input: String): Int {
    return taskImpl(input) { it.begin.x == it.end.x || it.begin.y == it.end.y }
}

fun task2(input: String): Int {
    return taskImpl(input) { true }
}

fun taskImpl(input: String, lineFilter: (Line) -> Boolean): Int {
    val vents = parseInput(input)
    val filteredVents = vents
        .filter { lineFilter(it) }
        .toList()

    val indexPairsSequence = (0 until filteredVents.size - 1)
        .flatMap { leftIdx -> (leftIdx + 1 until filteredVents.size).map { rightIdx -> Pair(leftIdx, rightIdx) } }
    val ventPairsSequence = indexPairsSequence
        .map { indexPair -> Pair(filteredVents[indexPair.first], filteredVents[indexPair.second]) }

    val intersectionPointSets = ventPairsSequence
        .map { pair -> lineIntersectionPoints(pair.first, pair.second) }
        .filter { intersection -> intersection.isNotEmpty() }

    return intersectionPointSets
        .flatten()
        .distinct()
        .count()
}

fun lineIntersectionPoints(left: Line, right: Line): Set<Point2D> {
    val leftPointsSet = toPointSequence(left).toSet()
    val rightPointsSet = toPointSequence(right).toSet()
    val intersect = leftPointsSet.intersect(rightPointsSet)
    return intersect
}

fun toPointSequence(line: Line): Sequence<Point2D> {
    if (line.begin.x == line.end.x) {
        val minY = min(line.begin.y, line.end.y)
        val maxY = max(line.begin.y, line.end.y)
        return (minY..maxY)
            .asSequence()
            .map { y -> Point2D(line.begin.x, y) }
    }

    if (line.begin.y == line.end.y) {
        val minX = min(line.begin.x, line.end.x)
        val maxX = max(line.begin.x, line.end.x)
        return (minX..maxX)
            .asSequence()
            .map { x -> Point2D(x, line.begin.y) }
    }

    check(abs(line.end.x - line.begin.x) == abs(line.end.y - line.begin.y))

    val startX = min(line.begin.x, line.end.x)
    val startY = min(line.begin.y, line.end.y)
    val steps = abs(line.end.x - line.begin.x)
    return (0..steps)
        .asSequence()
        .map { step -> Point2D(startX + step, startY + step) }
}