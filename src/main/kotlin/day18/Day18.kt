package day18

data class Number(val value: Long?, val children: List<Number>) {

    init {
        val isSimpleValue = value != null && children.isEmpty()
        val isPair = value == null && children.size == 2
        check(isSimpleValue || isPair)
    }

    constructor(value: Long) : this(value, listOf())
    constructor(leftChild: Number, rightChild: Number) : this(null, listOf(leftChild, rightChild))
    constructor(children: List<Number>) : this(null, children)

    private fun render(target: Appendable) {
        if (value != null) {
            target.append(value.toString())
        } else {
            target.append("[")
            children[0].render(target)
            target.append(",")
            children[1].render(target)
            target.append("]")
        }
    }

    override fun toString(): String {
        val result = StringBuilder()
        render(result)
        return result.toString()
    }
}

fun parseNumber(input: String): Number {
    if (!input.startsWith("[")) {
        return Number(input.toLong())
    }
    check(input.endsWith("]"))

    var bracketCount = 0
    var currentIdx = 0
    while (currentIdx < input.length) {
        currentIdx++
        val char = input[currentIdx]
        if (char == '[') {
            bracketCount++
        }
        if (char == ']') {
            bracketCount--
        }
        if (char == ',' && bracketCount == 0) {
            break
        }
    }
    check(currentIdx < input.length)

    val left = parseNumber(input.substring(1, currentIdx))
    val right = parseNumber(input.substring(currentIdx + 1, input.lastIndex))
    return Number(left, right)
}

fun reduce(input: Number): Number {
    val exploded = explodeIfNeeded(input)
    val split = splitIfNeeded(exploded)
    return if (split == input) {
        input
    } else {
        reduce(split)
    }
}

fun add(left: Number, right: Number): Number {
    return reduce(Number(left, right))
}

fun splitIfNeeded(input: Number): Number {
    if (input.value == null) {
        return Number(input.children.map { splitIfNeeded(it) })
    }

    if (input.value < 10) {
        return input
    }

    val left = input.value / 2
    val right = input.value - left
    return Number(Number(left), Number(right))
}

fun explodeIfNeeded(input: Number): Number {
    val knownPaths = knownPaths(input)

    val pathToExplodeWithLeaf = knownPaths
        .firstOrNull { it.size > 4 }
    if (pathToExplodeWithLeaf == null) {
        return input
    }

    val pathToExplode = pathToExplodeWithLeaf.subList(0, pathToExplodeWithLeaf.size - 1)
    val explodingPair = get(input, pathToExplode)

    var result = replace(input, pathToExplode, Number(0))

    val explodingRightValue = explodingPair.children[1].value
    if (explodingRightValue != null) {                                              // а мож стоит упасть на null?
        result = appendTo(result, pathToExplode, explodingRightValue, 1)
    }

    val explodingLeftValue = explodingPair.children[0].value
    if (explodingLeftValue != null) {                                               // а мож стоит упасть на null?
        result = appendTo(result, pathToExplode, explodingLeftValue, 0)
    }

    return result
}

fun appendTo(input: Number, startPath: List<Int>, valueToAdd: Long, firstStep: Int): Number {
    check(firstStep >= 0)
    check(firstStep <= 1)
    check(startPath.isNotEmpty())

    if (startPath.all { it == firstStep }) {
        return input
    }

    val replacePath = startPath.toMutableList()
    while (replacePath.last() == firstStep) {
        replacePath.removeLast()
    }
    replacePath.removeLast()
    replacePath.add(firstStep)

    val mainStep = 1 - firstStep
    while (get(input, replacePath).value == null) {
        replacePath.add(mainStep)
    }

    val originalValue = get(input, replacePath).value!!
    return replace(input, replacePath, Number(originalValue + valueToAdd))
}

fun get(input: Number, path: List<Int>): Number {
    if (input.value == null && path.isEmpty()) {
        return input
    }

    check(path.isNotEmpty())
    check(input.value == null)

    val nextChild = input.children[path.first()]
    if (path.size == 1) {
        return nextChild
    } else {
        return get(nextChild, path.subList(1, path.size))
    }
}

fun replace(input: Number, path: List<Int>, newItem: Number): Number {
    if (path.isEmpty()) {
        return newItem
    }

    val pathHead = path.first()
    val pathTail = path.subList(1, path.size)

    val newChildren = mutableListOf(input.children[0], input.children[1])
    newChildren[pathHead] = replace(newChildren[pathHead], pathTail, newItem)

    return Number(newChildren.first(), newChildren.last())
}

fun knownPaths(input: Number): List<List<Int>> {
    if (input.value != null) {
        return listOf()
    }

    val result = mutableListOf<List<Int>>()
    for (childIdx in input.children.indices) {
        val childPaths = knownPaths(input.children[childIdx])
        if (childPaths.isEmpty()) { // leaf
            result.add(listOf(childIdx))
        } else { // branch
            result.addAll(childPaths
                .map {
                    val combinedPath = mutableListOf<Int>()
                    combinedPath.add(childIdx)
                    combinedPath.addAll(it)
                    combinedPath
                })
        }
    }

    return result
}

fun magnitude(input: Number): Long {
    if (input.value != null) {
        return input.value
    }

    return 3 * magnitude(input.children[0]) + 2 * magnitude(input.children[1])
}

fun task1(input: String): Long {
    val inputNumbers = input.split("\n")
        .map { parseNumber(it) }
    check(inputNumbers.size > 2)

    var sum = add(inputNumbers[0], inputNumbers[1])
    for (idx in 2..inputNumbers.lastIndex) {
        sum = add(sum, inputNumbers[idx])
    }

    return magnitude(sum)
}
