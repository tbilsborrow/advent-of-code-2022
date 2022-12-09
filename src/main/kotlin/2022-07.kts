val lines = java.io.File("../resources/input-07.txt").readLines().asSequence()

data class FD(
    val name: String,
    var size: Long,
    val parent: FD?,
    val children: MutableList<FD> = mutableListOf()
) {
    fun ensureSubdir(name: String): FD {
        val sd = children.find { it.name == name}
        if (sd != null) return sd
        val d = FD(name, 0, this)
        children.add(d)
        return d
    }

    // it's a fold over a tree of FD
    fun <R> traverse(initial: R, operation: (acc: R, FD) -> R): R {
        var accumulator = operation(initial, this)
        children.forEach { accumulator = it.traverse(accumulator, operation) }
        return accumulator
    }

    fun print(level: Int = 0) {
        println("${"".padEnd(level, ' ')} $name ($size)")
        children.forEach { it.print(level + 1) }
    }
}

val regexCd = Regex("\\$ cd (.*)")
val regexDir = Regex("dir (.*)")
val regexFile = Regex("(\\d*) (.*)")

val root = FD("/", 0, null)
var current = root

lines.forEach { line ->
    if (regexCd.matches(line)) {
        // command
        val (dirName) = regexCd.find(line)!!.destructured
        current = when (dirName) {
            "/" -> root
            ".." -> current.parent ?: throw Exception("${current.name} has no parent")
            else -> current.children.find { it.name == dirName } ?: throw Exception("subdir $dirName of ${current.name} doesn't exist")
        }

    } else if (regexDir.matches(line)) {
        // subdir ls entry
        val (dirName) = regexDir.find(line)!!.destructured
        current.ensureSubdir(dirName)

    } else if (regexFile.matches(line)) {
        // file ls entry
        val (fileSize, _) = regexFile.find(line)!!.destructured
        var fd: FD? = current
        while (fd != null) {
            fd.size += fileSize.toLong()
            fd = fd.parent
        }
    }
}

root.print()

// part 1: 1390824
val p1 = root.traverse(0L) { acc, next ->
    if (next.size <= 100000) acc + next.size else acc
}
println("part 1: $p1")

// part 2: 7490863
val needed = root.size - 40000000
val p2 = root.traverse(Long.MAX_VALUE) { acc, next ->
    if (next.size in needed until acc) next.size else acc
}
println("part 2: $p2")
