package day20

data class Point2D(val x: Int, val y: Int)

data class Input(val enhancementAlgorithm: List<Boolean>, val imagePoints: Set<Point2D>)

data class Image(val points: Set<Point2D>, val valueOutside: Boolean) {
    val borders: ImageBorders

    init {
        check(points.isNotEmpty())
        borders = getImageBorders(points)
    }

    fun isLit(x: Int, y: Int): Boolean {
        val isOutsideBorders = x < borders.minX || x > borders.maxX || y < borders.minY || y > borders.maxY
        if (isOutsideBorders) {
            return valueOutside
        } else {
            return points.contains(Point2D(x, y))
        }
    }
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

fun parseInput(inputString: String): Input {
    check(inputString.contains("\n\n"))
    val (algoString, imageString) = inputString.split("\n\n")

    val enhancementAlgorithm = algoString
        .filter { it != '\n' }
        .map { it == '#' }
    check(enhancementAlgorithm.size == 512)

    val imagePoints = imageString.split("\n")
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

    return Input(enhancementAlgorithm, imagePoints)
}

fun drawImage(image: Image) {
    for (y in image.borders.minY..image.borders.maxY) {
        for (x in image.borders.minX..image.borders.maxX) {
            val chrToDraw = if (image.isLit(x, y)) '#' else '.'
            print(chrToDraw)
        }
        println()
    }
}

fun readBlockDigit(image: Image, blockCenter: Point2D): Int {
    var number = 0
    var currentPosScale = 1
    for (y in (blockCenter.y + 1) downTo (blockCenter.y - 1)) {
        for (x in (blockCenter.x + 1) downTo (blockCenter.x - 1)) {
            if (image.isLit(x, y)) {
                number += currentPosScale
            }
            currentPosScale *= 2
        }
    }
    return number
}

fun enchanceImage(image: Image, enhancementAlgorithm: List<Boolean>): Image {
    val newImagePoints = mutableSetOf<Point2D>()

    for (y in (image.borders.minY - 1)..(image.borders.maxY + 1)) {
        for (x in (image.borders.minX - 1)..(image.borders.maxX + 1)) {
            val point = Point2D(x, y)
            val digit = readBlockDigit(image, point)
            val isLit = enhancementAlgorithm[digit]
            if (isLit) {
                newImagePoints.add(point)
            }
        }
    }

    return Image(newImagePoints, image.valueOutside.xor(enhancementAlgorithm[0]))
}

fun task1(inputString: String): Int {
    val (enhancementAlgorithm, imagePoints) = parseInput(inputString)

    var currentImage = Image(imagePoints, false)
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

    check(!currentImage.valueOutside)
    return currentImage.points.count()
}