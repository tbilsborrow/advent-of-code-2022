val line = java.io.File("../resources/input-06.txt").readLines().first()

fun day6(str: String, windowSize: Int): Int {
    var i = 0
    for (s in str.windowed(windowSize)) {
        val set = s.toSet()
        if (set.size == windowSize) break
        i++
    }
    return i + windowSize
}

// part 1: 1655
println(day6(line, 4))

// part 2: 2665
println(day6(line, 14))
