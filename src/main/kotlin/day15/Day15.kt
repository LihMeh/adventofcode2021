package day15

data class Point2D(val x: Int, val y: Int)

fun nearbyPoints(point: Point2D): Set<Point2D> {
    return setOf(
        Point2D(point.x + 1, point.y),
        Point2D(point.x - 1, point.y),
        Point2D(point.x, point.y + 1),
        Point2D(point.x, point.y - 1)
    )
}

fun parseInput(input: String): Map<Point2D, Int> {
    return input.split("\n")
        .mapIndexed { y, row ->
            row.mapIndexed() { x, chr -> Point2D(x, y) to chr.digitToInt() }
        }
        .flatten()
        .toMap()
}

fun task1(input: String): Int {
    val pointToLocalRisk = parseInput(input)
    val minX = pointToLocalRisk.keys.minOf { it.x }
    val maxX = pointToLocalRisk.keys.maxOf { it.x }
    val minY = pointToLocalRisk.keys.minOf { it.y }
    val maxY = pointToLocalRisk.keys.maxOf { it.y }

    val pointToTotalRisk = mutableMapOf<Point2D, Int>()
    val nextPoints = mutableSetOf(Point2D(0, 0))

    var infoTimer = 0
    while (nextPoints.isNotEmpty()) {
        val currentPoint = nextPoints.first()
        nextPoints.remove(currentPoint)
        val currentRisk = pointToTotalRisk[currentPoint] ?: 0
        for (candidatePoint in nearbyPoints(currentPoint)) {
            if (candidatePoint.x in minX..maxX && candidatePoint.y in minY..maxY) {
                val candidateRisk = currentRisk + pointToLocalRisk[candidatePoint]!!
                val knownRisk = pointToTotalRisk[candidatePoint] ?: Int.MAX_VALUE
                if (candidateRisk < knownRisk) {
                    pointToTotalRisk[candidatePoint] = candidateRisk
                    nextPoints.add(candidatePoint)
                }
            }
        }
    }

    return pointToTotalRisk[Point2D(maxX, maxY)]!!
}