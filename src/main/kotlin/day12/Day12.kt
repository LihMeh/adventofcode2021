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
    val caves = parseInput(input)
    check(caves.containsKey("start"))
    check(caves.containsKey("end"))

    val smallCaves = caves.keys
        .filter { cave -> cave.none { chr -> chr.isUpperCase() } }
        .toSet()

    val pathsFound = mutableListOf<List<String>>()
    val unfinishedPaths = ArrayDeque<List<String>>()
    unfinishedPaths.add(listOf("start"))

    while (unfinishedPaths.isNotEmpty()) {
        val currentPath = unfinishedPaths.removeFirst()
        val currentCave = currentPath.last()

        val connectedCaves = caves[currentCave]!!
        val visitedSmallCaves = currentPath
            .filter { cave -> smallCaves.contains(cave) }
            .toSet()
        val cavesToCheck = connectedCaves.minus(visitedSmallCaves)
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