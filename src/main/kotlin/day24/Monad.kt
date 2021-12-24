package day24

fun isValidMonad(input: String): Boolean {
    val inputQueue = ArrayDeque<Int>()
    for (chr in input) {
        inputQueue.addLast(chr.digitToInt())
    }

    var x = 0
    var y = 0
    var z = 0
    var w = 0

    val firstParam = listOf(1, 1, 1, 26, 26, 1, 26, 26, 1, 1, 26, 1, 26, 26)
    val secondParam = listOf(12, 13, 13, -2, 10, 13, -14, -5, 15, 15, -14, 10, -14, -5)
    val thirdParam = listOf(7, 8, 10, 4, 4, 6, 11, 13, 1, 8, 4, 13, 4, 14)

    for (idx in firstParam.indices) {
        w = inputQueue.removeFirst()//        inp w
        x *= 0//        mul x 0
        x += z//        add x z
        x %= 26//        mod x 26
        z /= firstParam[idx] //        div z 1 // 1, 1, 1, 26, 26, 1, 26, 26, 1, 1, 26, 1, 26, 26
        x += secondParam[idx]//        add x 12 // 12, 13, 13, -2, 10, 13, -14, -5, 15, 15, -14, 10, -14, -5
        x = if (x == w) 1 else 0//        eql x w
        x = if (x == 0) 1 else 0//        eql x 0
        y *= 0//        mul y 0
        y += 25//        add y 25
        y *= x//        mul y x
        y += 1//        add y 1
        z *= y//        mul z y
        y *= 0//        mul y 0
        y += w//        add y w
        y += thirdParam[idx]//        add y 7 // 7, 8, 10, 4, 4, 6, 11, 13, 1, 8, 4, 13, 4, 14
        y *= x//        mul y x
        z += y//        add z y
    }

    return z == 0
}