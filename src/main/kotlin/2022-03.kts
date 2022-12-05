import java.util.BitSet

val lines = java.io.File("../resources/input-03.txt").readLines().asSequence()

// assumptions:
// - each input line is of an even length
// - each input line contains only chars [a-z|A-Z]
// - total number of input lines is divisible by 3

fun Char.priority() = if (this in 'a'..'z') this.code - 96 else this.code - 38

fun BitSet.load(s: String): BitSet {
    s.forEach { this.set(it.priority()) }
    return this
}

// bitset with set bits representing priorities of all chars in s
fun container(s: String): BitSet = BitSet(52).load(s)

fun part1() =
    lines.map {
        val c1 = it.substring(0, it.length / 2)
        val c2 = it.substring(it.length / 2)

        val bs = container(c1)
        bs.and(container(c2))

        bs.nextSetBit(0)
    }.sum()

fun part2() =
    lines.windowed(3, 3).map {
        val bs = container(it[0])
        bs.and(container(it[1]))
        bs.and(container(it[2]))
        bs.nextSetBit(0)
    }.sum()

// part 1: 7817
println(part1())

// part 2: 2444
println(part2())
