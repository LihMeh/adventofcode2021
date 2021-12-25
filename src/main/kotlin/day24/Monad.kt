package day24

fun isValidMonad(input: String): Boolean {
    val inputQueue = ArrayDeque<Long>()
    for (chr in input) {
        inputQueue.addLast(chr.digitToInt().toLong())
    }

    var z = 0L

    val firstParam = listOf<Long>(1, 1, 1, 26, 26, 1, 26, 26, 1, 1, 26, 1, 26, 26)
    val secondParam = listOf<Long>(12, 13, 13, -2, 10, 13, -14, -5, 15, 15, -14, 10, -14, -5)
    val thirdParam = listOf<Long>(7, 8, 10, 4, 4, 6, 11, 13, 1, 8, 4, 13, 4, 14)

    for (idx in firstParam.indices) {
        val w = inputQueue.removeFirst()
        var x = z % 26
        z /= firstParam[idx]
        x += secondParam[idx]
        if (x != w) {
            z *= 26
            z += w + thirdParam[idx]
        }
    }

    return z == 0L
}


