import kotlin.math.abs
import kotlin.math.max

val lines = java.io.File("../resources/input-09.txt").readLines().asSequence()
//val lines = java.io.File("../resources/test.txt").readLines().asSequence()

fun Int.sign() = if (this < 0) -1 else if (this > 0) 1 else 0

data class Knot(var x: Int = 0, var y: Int = 0) {
    operator fun minus(o: Knot) = Knot(this.x - o.x, this.y - o.y)

    fun move(dir: Char, dist: Int) {
        when (dir) {
            'U' -> this.y += dist
            'D' -> this.y -= dist
            'L' -> this.x -= dist
            'R' -> this.x += dist
        }
    }

    fun isTouching(o: Knot) = max(abs((this - o).x), abs((this - o).y)) <= 1
}

data class Rope(val length: Int) {
    private val knots: List<Knot> = IntRange(0, length - 1).map { Knot() }

    fun process(lines: Sequence<String>): Int {
        val tailVisited = mutableSetOf(Knot())

        lines.forEach { line ->
            val dir = line[0]
            val dist = line.substring(2).toInt()

            repeat(dist) {
                knots.first().move(dir, 1)
                knots.windowed(2).forEach { (k1, k2) ->
                    if (!k1.isTouching(k2)) {
                        val d = k1 - k2

                        // "line up" k2 with k1 if k1 is on a different rank or file
                        if (abs(d.x) < abs(d.y)) k2.x += d.x
                        else if (abs(d.y) < abs(d.x)) k2.y += d.y

                        // move k2 closer to k1 (that sign/abs stuff was when I
                        // originally wrote this to handle more than 1 dist move
                        // at a time, but then that made the visited set addition
                        // more complicated so now I just repeat this whole block
                        // [dist] times and move 1 unit each time through
                        if (abs(d.x) > 1) k2.x += d.x.sign() * (abs(d.x) - 1)
                        if (abs(d.y) > 1) k2.y += d.y.sign() * (abs(d.y) - 1)
                    }
                }
                tailVisited.add(knots.last().copy())
            }
        }

        return tailVisited.size
    }
}

// part 1: 5619
println(Rope(2).process(lines))

// part 2: 2376
println(Rope(10).process(lines))
