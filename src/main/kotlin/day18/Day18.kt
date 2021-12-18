package day18

data class Number(val value: Long?, val leftChild: Number?, val rightChild: Number?) {

    init {
        val isSimpleValue = value != null && leftChild == null && rightChild == null
        val isPair = value == null && leftChild != null && rightChild != null
        check(isSimpleValue || isPair)
    }

    constructor(value: Long) : this(value, null, null)
    constructor(leftChild: Number, rightChild: Number) : this(null, leftChild, rightChild)

}

fun parseNumber(input: String): Number {
    if (!input.startsWith("[")) {
        return Number(input.toLong(), null, null)
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
    return Number(null, left, right)
}

fun reduce(input: Number): Number {
    val split = splitIfNeeded(input)

    return split
}

fun splitIfNeeded(input: Number): Number {
    if (input.value == null) {
        return Number(null, splitIfNeeded(input.leftChild!!), splitIfNeeded(input.rightChild!!))
    }

    if (input.value < 10) {
        return input
    }

    val left = input.value / 2
    val right = input.value - left
    return Number(Number(left), Number(right))
}

fun magnitude(input: Number): Long {
    if (input.value != null) {
        return input.value
    }

    return 3 * magnitude(input.leftChild!!) + 2 * magnitude(input.rightChild!!)
}

fun task1(input: String): Long {
    val inputNumbers = input.split("\n")
        .map { parseNumber(it) }
    val sumOfInputNumbers = inputNumbers
        .reduceRight { left, right -> reduce(Number(left, right)) }
    return magnitude(sumOfInputNumbers)
}