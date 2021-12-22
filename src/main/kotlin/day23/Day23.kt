package day23

import kotlin.math.abs

data class Point2D(val x: Int, val y: Int)

fun parseInput(inputString: String): Map<Point2D, Char> {
    val result = mutableMapOf<Point2D, Char>()
    var y = -1
    for (row in inputString.split("\n")) {
        y++
        for (x in row.indices) {
            result[Point2D(x, y)] = row[x]
        }
    }
    return result
}

fun stackIdxToX(stackIdx: Int): Int {
    return 3 + stackIdx * 2
}

data class State(
    val insideStacks: List<List<Char>>,
    val outsizeXToChar: Map<Int, Char>,
    val score: Int
)

fun buildStartingState(pointToChar: Map<Point2D, Char>): State {
    val insideStacks = mutableListOf<List<Char>>()
    for (stackIdx in 0..3) {
        val stackX = stackIdxToX(stackIdx)
        val currentStack = mutableListOf<Char>()
        for (y in 3 downTo 2) {
            currentStack.add(pointToChar[Point2D(stackX, y)]!!)
        }
        insideStacks.add(currentStack)
    }
    return State(insideStacks, mapOf(), 0)
}

val stackIdsToTargetChar = listOf('A', 'B', 'C', 'D')

val charToMoveCost = mapOf(
    'A' to 1,
    'B' to 10,
    'C' to 100,
    'D' to 1000
)

fun possibleMovesOutside(state: State): List<State> {
    val result = mutableListOf<State>()
    val leftBorder = 0
    val rightBorder = 12

    val knownStackXs = state.insideStacks.indices
        .map { stackIdxToX(it) }
        .toSet()

    for (stackIdx in state.insideStacks.indices) {
        val stack = state.insideStacks[stackIdx]
        val targetChar = stackIdsToTargetChar[stackIdx]
        if (stack.isEmpty() || stack.all { it == targetChar }) {
            continue
        }

        val charToMove = stack.last()
        val stepCost = charToMoveCost[charToMove]!!
        val distanceToOutside = 3 - stack.size

        val targetPossibleXs = mutableSetOf<Int>()
        val currentX = stackIdxToX(stackIdx)
        var rightX = currentX + 1
        while (!state.outsizeXToChar.containsKey(rightX) && rightX < rightBorder) {
            targetPossibleXs.add(rightX)
            rightX++
        }
        var leftX = currentX - 1
        while (!state.outsizeXToChar.containsKey(leftX) && leftX > leftBorder) {
            targetPossibleXs.add(leftX)
            leftX--
        }
        targetPossibleXs.removeAll(knownStackXs)

        val updatedInsideStacks = state.insideStacks
            .mapIndexed { newStackIdx, oldStack ->
                if (newStackIdx == stackIdx) {
                    oldStack.subList(0, oldStack.size - 1)
                } else {
                    oldStack
                }
            }

        for (targetX in targetPossibleXs) {
            val currentMoveDistance = distanceToOutside + abs(targetX - currentX)
            val currentMoveCost = currentMoveDistance * stepCost

            val updatedOutsideXToChar = mutableMapOf<Int, Char>()
            updatedOutsideXToChar.putAll(state.outsizeXToChar)
            updatedOutsideXToChar.put(targetX, charToMove)

            result.add(
                State(
                    updatedInsideStacks,
                    updatedOutsideXToChar,
                    state.score + currentMoveCost
                )
            )
        }

    }

    return result
}

fun possibleMovesInside(state: State): List<State> {
    if (state.outsizeXToChar.isEmpty()) {
        return listOf()
    }

    val result = mutableListOf<State>()

    for (outsideCharEntry in state.outsizeXToChar) {
        val currentChar = outsideCharEntry.value
        val targetStackId = stackIdsToTargetChar.indexOf(currentChar)

        val targetStack = state.insideStacks[targetStackId]
        val isShouldGoToStack = targetStack.isEmpty() || targetStack.all { it == currentChar }
        if (!isShouldGoToStack) continue

        val currentX = outsideCharEntry.key
        val targetX = stackIdxToX(targetStackId)
        check(targetX != currentX)
        val movePath = if (targetX > currentX) {
            (currentX + 1)..targetX
        } else {
            (currentX - 1) downTo targetX
        }
        val isPathFree = movePath.all { !state.outsizeXToChar.containsKey(it) }
        if (!isPathFree) continue

        val distanceToX = abs(targetX - currentX)
        val distanceInside = 2 - targetStack.size
        val distance = distanceToX + distanceInside
        val cost = distance * charToMoveCost[currentChar]!!

        val updatedStacks = state.insideStacks
            .mapIndexed { newStackIdx, oldStack ->
                if (newStackIdx == targetStackId) {
                    val updatedStack = mutableListOf<Char>()
                    updatedStack.addAll(oldStack)
                    updatedStack.add(currentChar)
                    updatedStack
                } else {
                    oldStack
                }
            }

        val updatedOutside = mutableMapOf<Int, Char>()
        updatedOutside.putAll(state.outsizeXToChar)
        updatedOutside.remove(currentX)

        result.add(
            State(
                updatedStacks,
                updatedOutside,
                state.score + cost
            )
        )
    }

    return result
}

fun isFinalState(state: State): Boolean {
    if (state.outsizeXToChar.isNotEmpty()) {
        return false
    }

    for (stackIdx in state.insideStacks.indices) {
        val stack = state.insideStacks[stackIdx]
        check(stack.isNotEmpty())
        val targetChar = stackIdsToTargetChar[stackIdx]
        if (stack.any { it != targetChar }) {
            return false
        }
    }

    return true
}

fun task1(input: String): Int {
    val pointToChar = parseInput(input)

    val statesToCheck = ArrayDeque<State>()
    statesToCheck.add(buildStartingState(pointToChar))

    var bestScore = Integer.MAX_VALUE

    while (statesToCheck.isNotEmpty()) {
        val state = statesToCheck.removeLast()
        val movesOutside = possibleMovesOutside(state)
        statesToCheck.addAll(movesOutside)

        val movesInside = possibleMovesInside(state)
        for (stateAfterMoveInside in movesInside) {
            if (isFinalState(stateAfterMoveInside) && stateAfterMoveInside.score < bestScore) {
                bestScore = stateAfterMoveInside.score
                println("Best score so far: $bestScore")
            } else {
                statesToCheck.add(stateAfterMoveInside)
            }
        }
    }

    return bestScore
}