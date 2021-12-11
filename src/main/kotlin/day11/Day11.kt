package day11

class State(proposedOctopuses: List<List<Int>>) {
    val octopuses: MutableList<MutableList<Int>>
    val rowSize: Int
    var totalFlashesCount: Int

    init {
        check(proposedOctopuses.isNotEmpty())
        check(proposedOctopuses.first().isNotEmpty())
        rowSize = proposedOctopuses.first().size
        check(proposedOctopuses.all { it.size == rowSize })

        octopuses = proposedOctopuses
            .map { it.toMutableList() }
            .toMutableList()

        totalFlashesCount = 0
    }

    fun getRowRange(): IntRange {
        return 0 until octopuses.size
    }

    fun getColRange(): IntRange {
        return 0 until rowSize
    }

    fun isValid(point: Point2D): Boolean {
        return point.x >= 0 && point.x < octopuses.first().size
                && point.y >= 0 && point.y < rowSize
    }

    fun get(x: Int, y: Int): Int {
        //check(x in getColRange() && y in getRowRange())
        return octopuses[y][x]
    }

    fun set(x: Int, y: Int, value: Int) {
        //check(x in getColRange() && y in getRowRange())
        octopuses[y][x] = value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as State

        if (octopuses != other.octopuses) return false
        if (rowSize != other.rowSize) return false

        return true
    }

    override fun hashCode(): Int {
        var result = octopuses.hashCode()
        result = 31 * result + rowSize
        return result
    }

    override fun toString(): String {
        val resultBuilder = StringBuilder()
        resultBuilder.append("\n\n")
        for (y in getRowRange()) {
            val row = octopuses[y]
            for (value in row) {
                resultBuilder.append(value)
            }
            resultBuilder.append("\n")
        }
        resultBuilder.append("\n")
        return resultBuilder.toString()
    }
}

fun parseState(input: String): State {
    return State(input
        .split("\n")
        .map { line -> line.toList().map { chr -> chr.digitToInt() } })
}

data class Point2D(val x: Int, val y: Int)

fun pointsAround(point: Point2D): Set<Point2D> {
    val result = mutableSetOf<Point2D>()
    for (dx in -1..1) {
        for (dy in -1..1) {
            if (dx != 0 || dy != 0) {
                result.add(Point2D(point.x + dx, point.y + dy))
            }
        }
    }
    return result
}

fun task1Step(before: State): State {
    val after = State(before.octopuses)

    val octopusesToFlash = mutableSetOf<Point2D>()
    for (y in after.getColRange()) {
        for (x in after.getColRange()) {
            val newValue = before.get(x, y) + 1
            after.set(x, y, newValue)
            if (newValue > 9) {
                octopusesToFlash.add(Point2D(x, y))
            }
        }
    }


    val flashedOctopuses = mutableSetOf<Point2D>()
    while (octopusesToFlash.isNotEmpty()) {
        val nextFlash = octopusesToFlash.first()
        octopusesToFlash.remove(nextFlash)
        flashedOctopuses.add(nextFlash)

        val neighbours = pointsAround(nextFlash)
        for (neighbour in neighbours) {
            if (after.isValid(neighbour)) {
                val newValue = after.get(neighbour.x, neighbour.y) + 1
                after.set(neighbour.x, neighbour.y, newValue)
                if (newValue > 9 && !flashedOctopuses.contains(neighbour)) {
                    octopusesToFlash.add(neighbour)
                }
            }
        }
    }

    for (flashedOne in flashedOctopuses) {
        after.set(flashedOne.x, flashedOne.y, 0)
    }

    after.totalFlashesCount = before.totalFlashesCount + flashedOctopuses.size

    return after
}

fun task1(input: String): Int {
    var state = parseState(input)
    for (stepNum in 0 until 100) {
        state = task1Step(state)
    }

    return state.totalFlashesCount
}
