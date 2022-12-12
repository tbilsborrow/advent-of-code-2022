import java.lang.Integer.max
import java.util.BitSet

val lines = java.io.File("../resources/input-08.txt").readLines()
//val lines = java.io.File("../resources/test.txt").readLines()

val grid = lines.map { line ->
    val row = IntArray(line.length)
    line.forEachIndexed { i,c -> row[i] = c.digitToInt() }
    row
}

val visibilityGrid = Array(grid.size) { BitSet(lines[0].length) }

fun check(x: Int, y: Int, tallest: Int): Int {
    if (visibilityGrid[y][x]) {
        return max(tallest, grid[y][x])
    }
    if (grid[y][x] > tallest) {
        visibilityGrid[y][x] = true
        return grid[y][x]
    }
    return tallest
}

for (i in grid.indices) {
    var t1 = -1
    var t2 = -1
    var t3 = -1
    var t4 = -1
    for (j in grid.indices) {
        t1 = check(i, j, t1)
        t2 = check(i, grid.size - j - 1, t2)
        t3 = check(j, i, t3)
        t4 = check(grid.size - j - 1, i, t4)
    }
}

// part 1: 1711
val p1 = visibilityGrid.fold(0) { acc, next -> acc + next.cardinality() }
println(p1)

fun scenicScore(x: Int, y: Int): Int {
    var u = 1
    var d = 1
    var l = 1
    var r = 1
    while(y-u > 0 && grid[y-u][x] < grid[y][x]) u++
    while(y+d < grid.size-1 && grid[y+d][x] < grid[y][x]) d++
    while(x-l > 0 && grid[y][x-l] < grid[y][x]) l++
    while(x+r < grid.size-1 && grid[y][x+r] < grid[y][x]) r++
    return u*d*l*r
}

var p2 = 0
for (x in 1 until grid.size - 1) {
    for (y in 1 until grid.size - 1) {
        p2 = max(scenicScore(x, y), p2)
    }
}

// part 2: 301392
println(p2)
