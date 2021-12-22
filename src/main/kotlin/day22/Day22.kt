package day22

data class Point3D(val x: Int, val y: Int, val z: Int)

data class Region(val x: IntRange, val y: IntRange, val z: IntRange)

data class CommandRow(
    val on: Boolean,
    val region: Region
)

fun parseInput(input: String): List<CommandRow> {
    val rowRegex = Regex("(o[nf]+) x=([\\-\\d]+)..([\\-\\d]+),y=([\\-\\d]+)..([\\-\\d]+),z=([\\-\\d]+)..([\\-\\d]+)")
    return input.split("\n")
        .map { inputRow ->
            val match = rowRegex.matchEntire(inputRow)
            val (onOff, minX, maxX, minY, maxY, minZ, maxZ) = match!!.destructured
            CommandRow(
                "on".equals(onOff),
                Region(minX.toInt()..maxX.toInt(), minY.toInt()..maxY.toInt(), minZ.toInt()..maxZ.toInt())
            )
        }
}

fun splitByPanes(commands: List<CommandRow>, rangeFun: (Region) -> (IntRange)): List<IntRange> {
    val splitPanesLefts = mutableSetOf<Int>()
    commands
        .map { it.region }
        .map(rangeFun)
        .forEach {
            splitPanesLefts.add(it.first - 1)
            splitPanesLefts.add(it.last)
        }

    val splitPanesLeftsSorted = splitPanesLefts.sorted()

    return splitPanesLeftsSorted
        .windowed(2)
        .map { pair -> (pair.first() + 1)..pair.last() }
}

fun isRegionContains(region: Region, xRange: IntRange, yRange: IntRange, zRange: IntRange): Boolean {
    return xRange.first >= region.x.first && xRange.last <= region.x.last
            && yRange.first >= region.y.first && yRange.last <= region.y.last
            && zRange.first >= region.z.first && zRange.last <= region.z.last
}

fun taskImpl(input: String, predicate: (CommandRow) -> (Boolean)): Long {
    val commands = parseInput(input)
    val filteredCommands = commands
        .filter(predicate)

    val xRanges = splitByPanes(filteredCommands) { it.x }
    val yRanges = splitByPanes(filteredCommands) { it.y }
    val zRanges = splitByPanes(filteredCommands) { it.z }

    val totalRegions = xRanges.size * yRanges.size * zRanges.size
    var processedRegions = 0L
    val speakupInterval = 100_000L
    var speakupRegions = 0L

    var totalLitCount = 0L
    for (xRange in xRanges) {
        val xRangeSize = 1L + xRange.last - xRange.first
        for (yRange in yRanges) {
            val yRangeSize = 1L + yRange.last - yRange.first
            for (zRange in zRanges) {
                val lastRegionContaining = filteredCommands
                    .lastOrNull() { isRegionContains(it.region, xRange, yRange, zRange) }
                if (lastRegionContaining?.on == true) {
                    val zRangeSize = 1L + zRange.last - zRange.first
                    totalLitCount += xRangeSize * yRangeSize * zRangeSize
                }


                processedRegions++
                speakupRegions++
                if (speakupRegions >= speakupInterval) {
                    speakupRegions -= speakupInterval
                    val percents = 100L * processedRegions / totalRegions
                    println("Processed $processedRegions / $totalRegions ($percents%)")
                }
            }
        }
    }

    return totalLitCount
}

fun task1(input: String): Long {
    val limit = 50
    return taskImpl(input) {
        it.region.x.first >= -limit && it.region.x.last <= limit
                && it.region.y.first >= -limit && it.region.y.last <= limit
                && it.region.z.first >= -limit && it.region.z.last <= limit
    }
}

fun task2(input: String): Long {
    return taskImpl(input) { true }
}