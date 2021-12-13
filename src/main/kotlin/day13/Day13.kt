package day13

data class Point2D(val x: Int, val y: Int)

enum class FoldFunction {
    x {
        override fun apply(point: Point2D, line: Int): Point2D {
            check(line != point.x)

            if (point.x < line) {
                return point
            }

            return Point2D(line - (point.x - line), point.y)
        }
    },
    y {
        override fun apply(point: Point2D, line: Int): Point2D {
            check(line != point.y)

            if (point.y < line) {
                return point
            }

            return Point2D(point.x, line - (point.y - line))
        }
    };

    abstract fun apply(point: Point2D, line: Int): Point2D
}

data class FoldInstruction(val function: FoldFunction, val line: Int)

data class Input(val dots: Set<Point2D>, val foldInstructions: List<FoldInstruction>)

fun parseInput(inputString: String): Input {
    val (dotStr, foldStr) = inputString.split("\n\n")
    val dots = dotStr.split("\n")
        .map { row ->
            val (xStr, yStr) = row.split(",")
            Point2D(xStr.toInt(), yStr.toInt())
        }
        .toSet()

    val foldInstructions = foldStr.split("\n")
        .map { row ->
            val function = FoldFunction.valueOf(row.substring("fold along ".length, "fold along y".length))
            val line = row.substring("fold along y=".length).toInt()
            FoldInstruction(function, line)
        }
        .toList()

    return Input(dots, foldInstructions)
}


fun task1(inputString: String): Int {
    val input = parseInput(inputString)
    val firstFold = input.foldInstructions.first()
    val dotsAfterYFold = input.dots
        .map { dot -> firstFold.function.apply(dot, firstFold.line) }
        .toSet()
    return dotsAfterYFold.size
}

fun task2(inputString: String): String {
    val input = parseInput(inputString)
    var dots = input.dots
    for (foldInstruction in input.foldInstructions) {
        dots = dots
            .map { dot -> foldInstruction.function.apply(dot, foldInstruction.line) }
            .toSet()
    }

    val minX = dots.minOf { it.x }
    val maxX = dots.maxOf { it.x }
    val minY = dots.minOf { it.y }
    val maxY = dots.maxOf { it.y }
    val resultBuilder = StringBuilder()
    for (y in minY..maxY) {
        resultBuilder.append('\n')
        for (x in minX..maxX) {
            val isShowDot = dots.contains(Point2D(x, y))
            val charToShow = if (isShowDot) '#' else '.'
            resultBuilder.append(charToShow)
        }
    }
    return resultBuilder.toString()
}
