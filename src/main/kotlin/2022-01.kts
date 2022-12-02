val lines = java.io.File("../resources/input-01.txt").readLines().asSequence()

val sums = mutableListOf<Int>()
var current = 0
lines.forEach {
    if (it.isBlank()) {
        // yay there's a blank line at the end
        sums.add(current)
        current = 0
    } else {
        current += it.toInt()
    }
}
sums.sort()
sums.reverse()

// part 1: 67633
println(sums[0])

// part 2: 199628
println(sums.take(3).sum())
