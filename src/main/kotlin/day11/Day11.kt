package day11

class State(proposedOctopuses: List<List<Int>>) {
    val octopuses: MutableList<MutableList<Int>>
    val rowSize: Int

    init {
        check(proposedOctopuses.isNotEmpty())
        check(proposedOctopuses.first().isNotEmpty())
        rowSize = proposedOctopuses.first().size
        check(proposedOctopuses.all { it.size == rowSize })

        octopuses = proposedOctopuses
            .map { it.toMutableList() }
            .toMutableList()
    }


    fun getRowCount(): Int {
        return octopuses.size
    }

    fun getColCount(): Int {
        return octopuses.first().size
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
        for (y in 0 until getRowCount()) {
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

/*
 performs a step and returns flashed octopus count.
 Mutates state
 */
fun task1Step(state: State): Int {
    val octopusesToFlash = mutableSetOf<Point2D>()
    for (y in 0 until state.getRowCount()) {
        for (x in 0 until state.getColCount()) {
            val newValue = state.get(x, y) + 1
            state.set(x, y, newValue)
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
            if (state.isValid(neighbour)) {
                val newValue = state.get(neighbour.x, neighbour.y) + 1
                state.set(neighbour.x, neighbour.y, newValue)
                if (newValue > 9 && !flashedOctopuses.contains(neighbour)) {
                    octopusesToFlash.add(neighbour)
                }
            }
        }
    }

    for (flashedOne in flashedOctopuses) {
        state.set(flashedOne.x, flashedOne.y, 0)
    }

    return flashedOctopuses.size
}

fun task1(input: String): Int {
    val state = parseState(input)
    var totalFlashCount = 0
    for (stepNum in 0 until 100) {
        totalFlashCount += task1Step(state)
    }

    return totalFlashCount
}

fun task2(input: String): Int {
    val state = parseState(input)
    var stepNum = 0
    while (stepNum < 10_000) {
        stepNum++
        val flashedOctopuses = task1Step(state)
        if (flashedOctopuses == state.getRowCount() * state.getColCount()) {
            return stepNum
        }
    }
    throw IllegalStateException("Reached step limit")
}
