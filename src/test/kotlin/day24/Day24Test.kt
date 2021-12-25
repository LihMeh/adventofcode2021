package day24

import kotlin.test.Test

class Day24Test {

    @Test
    fun day24() {
        val current = 10000000000000L
        while (current <= 99999999999999L) {
            if (isValidMonad(current.toString())) {
                println(current)
            }
        }
    }

}