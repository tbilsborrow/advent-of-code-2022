val lines = java.io.File("../resources/input-05.txt").readLines().asSequence().iterator()
//val lines = java.io.File("../resources/test.txt").readLines().asSequence().iterator()

// initial state
val stacksLines = mutableListOf<String>()
do {
    val line = lines.next()
    if (line.isNotBlank()) stacksLines.add(0, line)
} while(line.isNotBlank())

val numStacks = stacksLines[0].trim().split(Regex("\\s+")).size
val stacks = mutableListOf<MutableList<Char>>()
repeat(numStacks) { stacks.add(mutableListOf()) }
for(i in 1 until stacksLines.size) {
    for(j in 0 until numStacks) {
        val pos = 4 * j + 1
        if (pos < stacksLines[i].length) {
            val crate = stacksLines[i][pos]
            if (crate != ' ') {
                stacks[j].add(0, crate)
            }
        }
    }
}

fun MutableList<MutableList<Char>>.copy(): MutableList<MutableList<Char>> {
    val copy = mutableListOf<MutableList<Char>>()
    for(stack in this) copy.add(stack.toMutableList())
    return copy
}

// moves
val stacks1 = stacks.copy()
val stacks2 = stacks.copy()

val re = Regex("move (\\d*) from (\\d*) to (\\d*)")
while(lines.hasNext()) {
    val x = re.find(lines.next())!!.groupValues
    val (count, fromStackNum, toStackNum) = x.subList(1, x.size).map { it.toInt() }
    // part 1: pop, push individually
    for (i in 0 until count) {
        val crate = stacks1[fromStackNum-1].removeAt(0)
        stacks1[toStackNum-1].add(0, crate)
    }
    // part 2: pop, push in bulk
    val sub = stacks2[fromStackNum-1].subList(0, count)
    stacks2[toStackNum-1].addAll(0, sub)
    sub.clear()
}

var p1 = stacks1.fold("") { acc, next -> acc + next[0] }
var p2 = stacks2.fold("") { acc, next -> acc + next[0] }

// part 1: JRVNHHCSJ
println(p1)

// part 2: GNFBSBJLH
println(p2)
