package day06

fun parseInput(input: String): List<Int> {
    return input.splitToSequence(",")
        .filter { it.isNotBlank() }
        .map { it.toInt() }
        .toList()
}

class State {
    private val ageToFishCount: MutableMap<Int, Long> = HashMap()
    fun addFishWithAge(age: Int, fishCount: Long) {
        check(age >= 0)
        check(fishCount >= 0)
        ageToFishCount[age] = ageToFishCount.getOrDefault(age, 0) + fishCount
    }

    fun removeFishWithAge(age: Int, fishCount: Long) {
        check(age >= 0)
        val currentCount = getFishCountByAge(age)
        check(fishCount >= currentCount)
        ageToFishCount[age] = currentCount - fishCount
    }

    fun getFishCountByAge(age: Int): Long {
        check(age >= 0)
        return ageToFishCount.getOrDefault(age, 0)
    }

    fun totalFishCount(): Long {
        return ageToFishCount.values
            .sumOf { it }
    }
}

fun task(input: String, daysToPass: Int): Long {
    val fishList = parseInput(input)
    check(fishList.isNotEmpty())
    check(fishList.all { it > 0 })

    val state = State()
    fishList.forEach { state.addFishWithAge(it, 1) }

    var daysLeft = daysToPass
    while (daysLeft > 0) {
        val fishToGiveBirth = state.getFishCountByAge(0)
        state.removeFishWithAge(0, fishToGiveBirth)

        for (age in 1..8) {
            val fishCount = state.getFishCountByAge(age)
            state.removeFishWithAge(age, fishCount)
            state.addFishWithAge(age - 1, fishCount)
        }

        state.addFishWithAge(6, fishToGiveBirth)
        state.addFishWithAge(8, fishToGiveBirth)

        daysLeft--
    }

    return state.totalFishCount()
}