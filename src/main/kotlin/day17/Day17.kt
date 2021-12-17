package day17

/*
Do not read it, that's the worst solution ever.
 */

data class Area(
    val minX: Int,
    val maxX: Int,
    val minY: Int,
    val maxY: Int
) {
    init {
        check(maxX >= minX)
        check(maxY >= minY)
    }
}

fun parseInput(input: String): Area {
    val regex = Regex("target area: x=([\\d\\-]+)\\.\\.([\\d\\-]+), y=([\\d\\-]+)\\.\\.([\\d\\-]+)")
    val match = regex.matchEntire(input)
    val (minX, maxX, minY, maxY) = match!!.destructured
    return Area(minX.toInt(), maxX.toInt(), minY.toInt(), maxY.toInt())
}

data class State(val x: Int, val y: Int, val vx: Int, val vy: Int) {
}

fun simulateStep(oldState: State): State {
    return State(
        oldState.x + oldState.vx,
        oldState.y + oldState.vy,
        if (oldState.vx > 0) oldState.vx - 1 else 0,
        oldState.vy - 1
    )
}

data class PathSimulateResult(val isValidPath: Boolean, val highestY: Int)

fun simulatePath(vx: Int, vy: Int, targetArea: Area, highestYThreshold: Int): PathSimulateResult {
    var currentState = State(0, 0, vx, vy)
    var stepLimit = 100_000
    var highestY = 0

    while (stepLimit > 0) {
        currentState = simulateStep(currentState)
        if (currentState.y > highestY) {
            highestY = currentState.y
        }

        if (currentState.vy < 0 && highestY < highestYThreshold) {
            return PathSimulateResult(false, highestY)
        }

        val isFitTargetArea = currentState.x >= targetArea.minX
                && currentState.x <= targetArea.maxX
                && currentState.y >= targetArea.minY
                && currentState.y <= targetArea.maxY
        if (isFitTargetArea) {
            return PathSimulateResult(true, highestY)
        }

        val isPassTargetArea = currentState.x > targetArea.maxX
                || currentState.y < targetArea.minY
        if (isPassTargetArea) {
            return PathSimulateResult(false, highestY)
        }

        stepLimit--
    }
    throw IllegalStateException("step limit reached")
}

fun task1(inputString: String): Int {
    val targetArea = parseInput(inputString)
    var highestY = 0
    for (vx in 1..100) {
        for (vy in 1..100) {
            val simulateResult = simulatePath(vx, vy, targetArea, highestY)
            if (simulateResult.isValidPath) {
                println("valid: vx = $vx , vy = $vy")
            }
            if (simulateResult.isValidPath && simulateResult.highestY > highestY) {
                highestY = simulateResult.highestY
            }
        }
    }

    return highestY
}

fun task2(inputString: String): Int {
    val targetArea = parseInput(inputString)
    var validPathsCount = 0
    for (vx in 1..1000) {
        for (vy in -1000..1000) {
            val simulateResult = simulatePath(vx, vy, targetArea, Integer.MIN_VALUE)
            if (simulateResult.isValidPath) {
                validPathsCount++
            }
        }
    }

    return validPathsCount
}
