package day22

data class Point3D(val x: Int, val y: Int, val z: Int)

data class Region(val min: Point3D, val max: Point3D) {
    init {
        check(max.x >= min.x)
        check(max.y >= min.y)
        check(max.z >= min.z)
    }
}

fun expandCuboid(region: Region): Set<Point3D> {
    val result = mutableSetOf<Point3D>()
    for (x in region.min.x..region.max.x) {
        for (y in region.min.y..region.max.y) {
            for (z in region.min.z..region.max.z) {
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
    val rowRegex = Regex("(o[nf]+) x=([\\-\\d]+)..([\\-\\d]+),y=([\\-\\d]+)..([\\-\\d]+),z=([\\-\\d]+)..([\\-\\d]+)");
    return input.split("\n")
        .map { inputRow ->
            val match = rowRegex.matchEntire(inputRow)
            val (onOff, minX, maxX, minY, maxY, minZ, maxZ) = match!!.destructured
            CommandRow(
                "on".equals(onOff),
                Region(
                    Point3D(minX.toInt(), minY.toInt(), minZ.toInt()),
                    Point3D(maxX.toInt(), maxY.toInt(), maxZ.toInt())
                )
            )
        }
}

fun task1(input: String): Int {
    val limit = 50;
    val commands = parseInput(input)
    val filteredCommands = commands
        .filter {
            it.region.min.x >= -limit && it.region.min.y >= -limit
                    && it.region.min.z >= -limit && it.region.max.x <= limit
                    && it.region.max.y <= limit && it.region.max.z <= limit
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