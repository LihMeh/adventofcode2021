package day20

data class Point2D(val x: Int, val y: Int)

data class Input(val enhancementAlgorithm: List<Boolean>, val image: Set<Point2D>)

fun parseInput(inputString: String): Input {
    check(inputString.contains("\n\n"))
    val (algoString, imageString) = inputString.split("\n\n")

    val enhancementAlgorithm = algoString
        .filter { it != '\n' }
        .map { it == '#' }
    check(enhancementAlgorithm.size == 512)

    val image = imageString.split("\n")
        .flatMapIndexed { y: Int, row: String ->
            row.mapIndexedNotNull { x, chr ->
                if (chr == '#') {
                    Point2D(x, y)
                } else {
                    null
                }
            }
        }
        .toSet()

    return Input(enhancementAlgorithm, image)
}

data class ImageBorders(val minX: Int, val maxX: Int, val minY: Int, val maxY: Int)

fun getImageBorders(image: Set<Point2D>): ImageBorders {
    check(image.isNotEmpty())
    return ImageBorders(
        image.minOf { it.x },
        image.maxOf { it.x },
        image.minOf { it.y },
        image.maxOf { it.y }
    )
}

fun drawImage(image: Set<Point2D>) {
    check(image.isNotEmpty())
    val borders = getImageBorders(image)
    for (y in borders.minY..borders.maxY) {
        for (x in borders.minX..borders.maxX) {
            val chrToDraw = if (image.contains(Point2D(x, y))) '#' else '.'
            print(chrToDraw)
        }
        println()
    }
}

fun readBlockDigit(image: Set<Point2D>, blockCenter: Point2D): Int {
    var number = 0
    var currentPosScale = 1
    for (y in (blockCenter.y + 1) downTo (blockCenter.y - 1)) {
        for (x in (blockCenter.x + 1) downTo (blockCenter.x - 1)) {
            if (image.contains(Point2D(x, y))) {
                number += currentPosScale
            }
            currentPosScale *= 2
        }
    }
    return number
}

fun enchanceImage(image: Set<Point2D>, enhancementAlgorithm: List<Boolean>): Set<Point2D> {
    val newImage = mutableSetOf<Point2D>()
    val borders = getImageBorders(image)

    for (y in (borders.minY - 1)..(borders.maxY + 1)) {
        for (x in (borders.minX - 1)..(borders.maxX + 1)) {
            val point = Point2D(x, y)
            val digit = readBlockDigit(image, point)
            val isLit = enhancementAlgorithm[digit]
            if (isLit) {
                newImage.add(point)
            }
        }
    }

    return newImage
}

fun task1(inputString: String): Int {
    val (enhancementAlgorithm, image) = parseInput(inputString)

    var currentImage = image
    println()
    println("before step 0")
    drawImage(currentImage)
    println()

    for (step in 0 until 2) {
        println("after step $step")
        currentImage = enchanceImage(currentImage, enhancementAlgorithm)
        drawImage(currentImage)
        println()
    }

    return currentImage.count()
}