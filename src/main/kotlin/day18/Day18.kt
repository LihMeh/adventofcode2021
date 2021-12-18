package day18

data class Number(val value: Long?, val children: Pair<Number, Number>?) {

    init {
        val isSimpleValue = value != null && children == null
        val isPair = value == null && children != null
        check(isSimpleValue || isPair)
    }

    constructor(value: Long) : this(value, null)
    constructor(leftChild: Number, rightChild: Number) : this(null, leftChild to rightChild)

    private fun render(target: Appendable) {
        if (value != null) {
            target.append(value.toString())
        } else {
            target.append("[")
            children!!.first.render(target)
            target.append(",")
            children.second.render(target)
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
        return Number(splitIfNeeded(input.children!!.first), splitIfNeeded(input.children.second))
    }

    if (input.value < 10) {
        return input
    }

    val left = input.value / 2
    val right = input.value - left
    return Number(Number(left), Number(right))
}

fun explodeIfNeeded(input: Number): Number {
    return input
}

fun magnitude(input: Number): Long {
    if (input.value != null) {
        return input.value
    }

    return 3 * magnitude(input.children!!.first) + 2 * magnitude(input.children.second)
}

fun task1(input: String): Long {
    val inputNumbers = input.split("\n")
        .map { parseNumber(it) }
    val sumOfInputNumbers = inputNumbers
        .reduceRight { left, right -> add(left, right) }
    return magnitude(sumOfInputNumbers)
}