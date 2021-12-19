package day19

typealias Point = List<Int>
typealias ScanResult = Set<Point>

fun parseInput(input: String): List<ScanResult> {
    return input.split("\n\n")
        .map { scannerData ->
            val scannerStrings = scannerData.split("\n")
            val pointStrings = scannerStrings.subList(1, scannerStrings.size)
            pointStrings.map {
                it.split(",")
                    .map { it.toInt() }
                    .toList()
            }.toSet()
        }
}

fun pointMinus(left: Point, right: Point): Point {
    check(left.size == right.size)
    return left.indices
        .map { idx -> left[idx] - right[idx] }
}

fun pointPlus(left: Point, right: Point): Point {
    check(left.size == right.size)
    return left.indices
        .map { idx -> left[idx] + right[idx] }
}

fun allPossibleDirections(input: ScanResult): Set<ScanResult> {
    val scannedRotations = mutableListOf(
        input,
        input.map { point -> listOf(point[0], point[2], point[1]) }.toSet(),
        input.map { point -> listOf(point[2], point[1], point[0]) }.toSet(),
        input.map { point -> listOf(point[1], point[0], point[2]) }.toSet(),
        input.map { point -> listOf(point[1], point[2], point[0]) }.toSet(),
        input.map { point -> listOf(point[2], point[0], point[1]) }.toSet()
    )

    val result = mutableSetOf<ScanResult>()
    for (rotation in scannedRotations) {
        for (xMultiplier in listOf(-1, 1)) {
            for (yMultiplier in listOf(-1, 1)) {
                for (zMultiplier in listOf(-1, 1)) {
                    val rotatedAndRedirected = mutableSetOf<Point>()

                    rotatedAndRedirected.addAll(rotation
                        .map { point ->
                            listOf(
                                point[0] * xMultiplier,
                                point[1] * yMultiplier,
                                point[2] * zMultiplier
                            )
                        })

                    result.add(rotatedAndRedirected)
                }
            }
        }
    }

    return result
}

fun task1(input: String): Int {
    val scanners = parseInput(input)
    check(scanners.isNotEmpty())

    val scannersQueue = ArrayDeque<ScanResult>()
    scannersQueue.addAll(scanners)

    val knownBeacons = mutableSetOf<Point>()
    knownBeacons.addAll(scannersQueue.removeFirst())

    while (scannersQueue.isNotEmpty()) {
        val nextScanner = scannersQueue.removeFirst()
        val nextScannerDirections = allPossibleDirections(nextScanner)

        val matchingDirections = nextScannerDirections
            .filter { findIntersectionShift(knownBeacons, it, 12) != null }
        check(matchingDirections.size <= 1)
        if (matchingDirections.isNotEmpty()) {
            val match = matchingDirections.first()
            val intersectionShift = findIntersectionShift(knownBeacons, match, 12)!!
            match
                .map { pointPlus(it, intersectionShift) }
                .forEach { knownBeacons.add(it) }
        } else {
            scannersQueue.addLast(nextScanner)
        }
        println("Beacons found: ${knownBeacons.size} ; scanners left to match: ${scannersQueue.size}")
    }

    return knownBeacons.size
}

fun findIntersectionShift(left: ScanResult, right: ScanResult, enoughPoints: Int): Point? {
    for (leftStartingPoint in left) {
        for (rightStartingPoint in right) {
            val targetDiff = pointMinus(leftStartingPoint, rightStartingPoint)
            val normalizedRightPoints = right.map { pointPlus(it, targetDiff) }.toSet()
            val commonPoints = left.intersect(normalizedRightPoints)
            if (commonPoints.size >= enoughPoints) {
                return targetDiff
            }
        }
    }
    return null
}