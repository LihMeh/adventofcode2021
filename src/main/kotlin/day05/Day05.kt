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

    val intersections = ventPairsSequence
        .mapNotNull { pair -> intersectLines(pair.first, pair.second) }

    return intersections.asSequence()
        .flatMap { toPointSequence(it) }
        .distinct()
        .count()
}

fun intersectLines(left: Line, right: Line): Line? {
    val isLeftHorizontal = left.begin.y == left.end.y
    val isRightHorizontal = right.begin.y == right.end.y
    val isLeftVertical = left.begin.x == left.end.x
    val isRightVertical = right.begin.x == right.end.x

    if (isLeftHorizontal && isRightHorizontal) {
        if (left.begin.y != right.begin.y) {
            return null
        }

        val leftMinX = min(left.begin.x, left.end.x)
        val leftMaxX = max(left.begin.x, left.end.x)
        val rightMinX = min(right.begin.x, right.end.x)
        val rightMaxX = max(right.begin.x, right.end.x)

        val targetMinX = max(leftMinX, rightMinX)
        val targetMaxX = min(leftMaxX, rightMaxX)
        return if (targetMaxX >= targetMinX) Line(
            Point2D(targetMinX, left.begin.y),
            Point2D(targetMaxX, left.begin.y)
        ) else null
    }

    if (isLeftVertical && isRightVertical) {
        if (left.begin.x != right.begin.x) {
            return null
        }

        val leftMinY = min(left.begin.y, left.end.y)
        val leftMaxY = max(left.begin.y, left.end.y)
        val rightMinY = min(right.begin.y, right.end.y)
        val rightMaxY = max(right.begin.y, right.end.y)

        val targetMinY = max(leftMinY, rightMinY)
        val targetMaxY = min(leftMaxY, rightMaxY)
        return if (targetMaxY >= targetMinY) Line(
            Point2D(left.begin.x, targetMinY),
            Point2D(left.begin.x, targetMaxY)
        ) else null
    }

    val isHorizontalAndVertical = (isLeftHorizontal && isRightVertical) || (isLeftVertical && isRightHorizontal)
    if (isHorizontalAndVertical) {
        val horiLine = if (isLeftHorizontal) left else right
        val vertLine = if (isRightHorizontal) left else right

        val horiMinX = min(horiLine.begin.x, horiLine.end.x)
        val horiMaxX = max(horiLine.begin.x, horiLine.end.x)
        if (vertLine.begin.x < horiMinX || vertLine.begin.x > horiMaxX) {
            return null
        }

        val vertMinY = min(vertLine.begin.y, vertLine.end.y)
        val vertMaxY = max(vertLine.begin.y, vertLine.end.y)
        if (horiLine.begin.y < vertMinY || horiLine.begin.y > vertMaxY) {
            return null
        }

        val commonPoint = Point2D(vertLine.begin.x, horiLine.begin.y)
        return Line(commonPoint, commonPoint)
    }

    val isHorizontalAndDiagonal = (isLeftHorizontal && !isRightVertical) || (isRightHorizontal && !isLeftVertical)
    if (isHorizontalAndDiagonal) {
        val horiLine = if (isLeftHorizontal) left else right
        val diagLine = if (isLeftHorizontal) right else left

        val horiY = horiLine.begin.y
        val diagMinY = min(diagLine.begin.y, diagLine.end.y)
        val diagMaxY = max(diagLine.begin.y, diagLine.end.y)
        if (diagMinY > horiY || diagMaxY < horiY) {
            return null
        }

        val diagMinX = min(diagLine.begin.x, diagLine.end.x)
        val intersectionX = diagMinX + horiY - diagMinY
        val horiMinX = min(horiLine.begin.x, horiLine.end.x)
        val horiMaxX = max(horiLine.begin.x, horiLine.end.x)
        if (intersectionX in horiMinX..horiMaxX) {
            val commonPoint = Point2D(intersectionX, horiY)
            return Line(commonPoint, commonPoint)
        } else {
            return null
        }
    }

    val isVerticalAndDiagonal = (isLeftVertical && !isRightVertical) || (isRightVertical && !isRightVertical)
    if (isVerticalAndDiagonal) {
        val vertLine = if (isLeftVertical) left else right
        val diagLine = if (isLeftVertical) right else left

        val vertX = vertLine.begin.x
        val diagMinX = min(diagLine.begin.x, diagLine.end.x)
        val diagMaxX = max(diagLine.begin.x, diagLine.end.x)
        if (diagMinX > vertX || diagMaxX < vertX) {
            return null
        }

        val diagMinY = min(diagLine.begin.y, diagLine.end.y)
        val intersectionY = diagMinY + vertX - diagMinX
        val vertMinY = min(vertLine.begin.y, vertLine.end.y)
        val vertMaxY = max(vertLine.begin.y, vertLine.end.y)
        if (intersectionY in vertMinY..vertMaxY) {
            val commonPoint = Point2D(vertX, intersectionY)
            return Line(commonPoint, commonPoint)
        } else {
            return null
        }
    }

    val leftUpperPoint = if (left.begin.y < left.end.y) left.begin else left.end
    val leftBottomPoint = if (left.begin.y > left.end.y) left.begin else left.end
    val rightUpperPoint = if (right.begin.y < left.begin.y) left.begin else left.end
    val rightBottomPoint = if (right.begin.y > left.end.y) left.begin else left.end
    if (leftBottomPoint.y < rightUpperPoint.y || rightBottomPoint.y < leftUpperPoint.y) {
        return null
    }

    val leftMinX = min(left.begin.x, left.end.x)
    val leftMaxX = max(left.begin.x, left.end.x)
    val rightMinX = min(right.begin.x, right.end.x)
    val rightMaxX = max(right.begin.x, right.end.x)
    if (leftMaxX < rightMinX || leftMinX > rightMaxX) {
        return null
    }

    val isParallel = (leftBottomPoint.x - leftUpperPoint.x > 0) == (rightBottomPoint.x - rightUpperPoint.x > 0)
    if (isParallel) {
        check(false) { "parallel diagonals are not supported" }
    }

    check(false) { "non-parallel diagonals are not supported" }
    return null
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