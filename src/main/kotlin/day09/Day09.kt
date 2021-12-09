package day09

data class Point2D(val x: Int, val y: Int)

fun parseInput(input: String): Map<Point2D, Int> {
    return input.split("\n")
        .mapIndexed { y, row -> row.toCharArray().mapIndexed { x, chr -> Point2D(x, y) to chr.digitToInt() } }
        .flatten()
        .toMap()
}

fun nearbyPoints(point: Point2D): Set<Point2D> {
    return setOf(
        Point2D(point.x - 1, point.y),
        Point2D(point.x + 1, point.y),
        Point2D(point.x, point.y - 1),
        Point2D(point.x, point.y + 1)
    )
}

fun isLowPoint(point: Point2D, heightMap: Map<Point2D, Int>): Boolean {
    check(heightMap.containsKey(point))
    val targetHeight = heightMap[point]!!
    return nearbyPoints(point)
        .mapNotNull { heightMap[it] }
        .all { it > targetHeight }
}

fun task1(input: String): Int {
    val heightMap = parseInput(input)

    val lowPoints = heightMap.keys
        .filter { isLowPoint(it, heightMap) }

    return lowPoints.sumOf { 1 + heightMap[it]!! }
}
