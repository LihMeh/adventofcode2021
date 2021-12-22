package day22

data class Point3D(val x: Int, val y: Int, val z: Int)

data class Region(val x: IntRange, val y: IntRange, val z: IntRange)

fun expandCuboid(region: Region): Set<Point3D> {
    val result = mutableSetOf<Point3D>()
    for (x in region.x) {
        for (y in region.y) {
            for (z in region.z) {
                result.add(Point3D(x, y, z))
            }
        }
    }
    return result
}

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

fun task1(input: String): Int {
    val limit = 50
    val commands = parseInput(input)
    val filteredCommands = commands
        .filter {
            it.region.x.first >= -limit && it.region.x.last <= limit
                    && it.region.y.first >= -limit && it.region.y.last <= limit
                    && it.region.z.first >= -limit && it.region.z.last <= limit
        }

    val cubesLit = mutableSetOf<Point3D>()
    for (command in filteredCommands) {
        val subCuboids = expandCuboid(command.region)
        if (command.on) {
            cubesLit.addAll(subCuboids)
        } else {
            cubesLit.removeAll(subCuboids)
        }
    }

    return cubesLit.size
}