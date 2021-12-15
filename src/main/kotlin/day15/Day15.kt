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
    val maxPoint = pointToLocalRisk.keys.maxByOrNull { it.x + it.y }!!
    return taskImpl(maxPoint) { point ->
        pointToLocalRisk[point]
    }
}

fun task2(input: String): Int {
    val riskTile = parseInput(input)

    val pointToLocalRisk = mutableMapOf<Point2D, Int>()
    riskTile.forEach { (key, value) -> pointToLocalRisk[key] = value }
    val tileSizeX = riskTile.keys.maxOf { it.x } + 1
    val tileSizeY = riskTile.keys.maxOf { it.y } + 1
    for (tileNumX in 1 until 5) {
        for (x in 0 until tileSizeX) {
            val currentX = x + tileNumX * tileSizeX
            val prevX = currentX - tileSizeX
            for (y in 0 until tileSizeY) {
                val prevPoint = Point2D(prevX, y)
                val currentPoint = Point2D(currentX, y)
                val risk = pointToLocalRisk[prevPoint]!! + 1
                val normalizedRisk = if (risk < 10) risk else 1
                pointToLocalRisk[currentPoint] = normalizedRisk
            }
        }
    }
    for (tileNumY in 1 until 5) {
        for (y in 0 until tileSizeY) {
            val currentY = y + tileNumY * tileSizeY
            val prevY = currentY - tileSizeY
            for (x in 0 until tileSizeX * 5) {
                val prevPoint = Point2D(x, prevY)
                val currentPoint = Point2D(x, currentY)
                val risk = pointToLocalRisk[prevPoint]!! + 1
                val normalizedRisk = if (risk < 10) risk else 1
                pointToLocalRisk[currentPoint] = normalizedRisk
            }
        }
    }

    val findRiskFun: (Point2D) -> Int? = { point -> pointToLocalRisk[point] }
    val maxX = pointToLocalRisk.keys.maxOf { it.x }
    val maxY = pointToLocalRisk.keys.maxOf { it.y }
    return taskImpl(Point2D(maxX, maxY), findRiskFun)
}

fun taskImpl(targetPoint: Point2D, findLocalRisk: (Point2D) -> Int?): Int {
    val pointToTotalRisk = mutableMapOf<Point2D, Int>()
    val nextPoints = mutableSetOf(Point2D(0, 0))

    while (nextPoints.isNotEmpty()) {
        val currentPoint = nextPoints.first()
        nextPoints.remove(currentPoint)
        val currentRisk = pointToTotalRisk[currentPoint] ?: 0
        for (candidatePoint in nearbyPoints(currentPoint)) {
            val candidateLocalRisk = findLocalRisk(candidatePoint)
            if (candidateLocalRisk != null) {
                val candidateTotalRisk = currentRisk + candidateLocalRisk
                val knownRisk = pointToTotalRisk[candidatePoint] ?: Int.MAX_VALUE
                if (candidateTotalRisk < knownRisk) {
                    pointToTotalRisk[candidatePoint] = candidateTotalRisk
                    nextPoints.add(candidatePoint)
                }
            }
        }
    }

    return pointToTotalRisk[targetPoint]!!
}