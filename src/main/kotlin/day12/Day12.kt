package day12

import java.util.*

fun parseInput(input: String): Map<String, Set<String>> {
    val result = mutableMapOf<String, Set<String>>()
    input
        .split("\n")
        .filter { it.isNotBlank() }
        .map { row ->
            val (from, to) = row.split("-")
            listOf(from to to, to to from)
        }
        .flatten()
        .forEach { (from, to) ->
            val existingTargets = result.getOrDefault(from, setOf())
            val newTargets = existingTargets.union(setOf(to))
            result[from] = newTargets
        }
    return result
}

fun task1(input: String): Int {
    return taskImpl(input) { path ->
        path
            .filter { isSmallCave(it) }
            .toSet()
    }
}

fun task2(input: String): Int {
    return taskImpl(input) { path ->
        val caveToVisitCount = countVisits(path)
        val visitedSmallCaves = caveToVisitCount.keys
            .filter { isSmallCave(it) }
            .toSet()
        val smallCavesVisitedSeveralTimes = visitedSmallCaves
            .filter { cave -> caveToVisitCount[cave]!! > 1 }
            .toSet()
        check(smallCavesVisitedSeveralTimes.size < 2)
        if (smallCavesVisitedSeveralTimes.isEmpty()) {
            setOf("start")
        } else {
            visitedSmallCaves
        }
    }
}

fun taskImpl(input: String, forbiddenCavesStrategy: (List<String>) -> Set<String>): Int {
    val caves = parseInput(input)
    check(caves.containsKey("start"))
    check(caves.containsKey("end"))

    val pathsFound = mutableListOf<List<String>>()
    val unfinishedPaths = ArrayDeque<List<String>>()
    unfinishedPaths.add(listOf("start"))

    while (unfinishedPaths.isNotEmpty()) {
        val currentPath = unfinishedPaths.removeFirst()
        val currentCave = currentPath.last()
        val connectedCaves = caves[currentCave]!!
        val forbiddenCaves = forbiddenCavesStrategy(currentPath)

        val cavesToCheck = connectedCaves.minus(forbiddenCaves)
        for (caveToCheck in cavesToCheck) {
            val updatedPath = currentPath.toMutableList()
            updatedPath.add(caveToCheck)
            if ("end" == caveToCheck) {
                pathsFound.add(updatedPath)
            } else {
                unfinishedPaths.add(updatedPath)
            }
        }
    }

    return pathsFound.size
}

fun isSmallCave(cave: String): Boolean {
    return cave.none { chr -> chr.isUpperCase() }
}

fun countVisits(path: List<String>): Map<String, Int> {
    val result = mutableMapOf<String, Int>()
    path.forEach { cave ->
        result[cave] = 1 + (result[cave] ?: 0)
    }
    return result
}