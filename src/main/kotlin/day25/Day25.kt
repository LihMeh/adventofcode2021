package day25

data class Point2D(val x: Int, val y: Int)

data class State(
    val cucumbers: Map<Point2D, Char>,
    val width: Int,
    val height: Int
)

fun parseInput(inputString: String): State {
    val inputRows = inputString.split("\n")
    val cucumbers = inputRows.flatMapIndexed { y, row ->
        row.mapIndexed { x, chr -> Point2D(x, y) to chr }
    }
        .filter { pair -> pair.second != '.' }
        .toMap()
    return State(
        cucumbers,
        inputRows.first().length,
        inputRows.size
    )
}

fun teleportIfNeeded(point: Point2D, width: Int, height: Int): Point2D {
    var newX = point.x
    while (newX < 0) newX += width
    while (newX >= width) newX -= width

    var newY = point.y
    while (newY < 0) newY += height
    while (newY >= height) newY -= height

    return Point2D(newX, newY)
}

fun move(state: State, cucumberFilter: Char, moveFun: (Point2D) -> (Point2D)): Pair<State, Int> {
    val newCucumbers = mutableMapOf<Point2D, Char>()
    var movedCucumbers = 0
    for (pair in state.cucumbers) {
        val cucumber = pair.value
        val point = pair.key
        if (cucumber != cucumberFilter) {
            newCucumbers[point] = cucumber
            continue
        }

        val nextPointCandidate = teleportIfNeeded(
            moveFun(point),
            state.width, state.height
        )

        if (state.cucumbers.containsKey(nextPointCandidate)) {
            newCucumbers[point] = cucumber
        } else {
            newCucumbers[nextPointCandidate] = cucumber
            movedCucumbers++
        }
    }
    return State(newCucumbers, state.width, state.height) to movedCucumbers
}

fun draw(state: State, steps: Int) {
    println()
    println("After $steps steps:")
    for (y in 0 until state.height) {
        for (x in 0 until state.width) {
            val charToDraw = state.cucumbers.getOrDefault(Point2D(x, y), '.')
            print(charToDraw)
        }
        println()
    }
}

fun task1(inputString: String): Int {
    var state = parseInput(inputString)
    var cucumbersMovesLastStep = -1
    var stepCount = 0
    while (cucumbersMovesLastStep != 0) {
        val eastMoveResult = move(state, '>') { point -> Point2D(point.x + 1, point.y) }
        val southMoveResult = move(eastMoveResult.first, 'v') { point -> Point2D(point.x, point.y + 1) }
        state = southMoveResult.first
        cucumbersMovesLastStep = eastMoveResult.second + southMoveResult.second
        stepCount++
        //draw(state, stepCount)
    }
    return stepCount
}