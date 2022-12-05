val lines = java.io.File("../resources/input-04.txt").readLines().asSequence()

fun IntRange.contains(other: IntRange) = this.first <= other.first && this.last >= other.last
fun IntRange.overlaps(other: IntRange) = this.contains(other.first) || this.contains(other.last)

fun parse(s: String): Pair<IntRange, IntRange> {
    val (e1, e2) = s.split(",")
    val (start1, end1) = e1.split("-").map { it.toInt() }
    val (start2, end2) = e2.split("-").map { it.toInt() }
    val r1 = IntRange(start1, end1)
    val r2 = IntRange(start2, end2)
    return Pair(r1, r2)
}

val p1 = lines.count { line ->
    val (r1, r2) = parse(line)
    r1.contains(r2) || r2.contains(r1)
}

val p2 = lines.count { line ->
    val (r1, r2) = parse(line)
    r1.overlaps(r2) || r2.overlaps(r1)
}

// part 1: 567
println(p1)

// part 2: 907
println(p2)
