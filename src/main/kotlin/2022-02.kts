val lines = java.io.File("../resources/input-02.txt").readLines().asSequence()

val ROCK = 1
val PAPER = 2
val SCISSORS = 3

val codes = mapOf(
    "A" to ROCK,
    "B" to PAPER,
    "C" to SCISSORS,
    "X" to ROCK,
    "Y" to PAPER,
    "Z" to SCISSORS,
)

fun score(me: Int, them: Int): Int =
    if (((me % 3) + 1) == them) 0
    else if (me == them) 3
    else 6

val score1 = lines.fold(0) { acc, next ->
    val (them, me) = next.split(" ").map { codes[it]!! }
    acc + me + score(me, them)
}

// part 1: 13268
println(score1)

fun choose(them: Int, outcome: String): Int =
    when (outcome) {
        "X" -> if (them - 1 == 0) 3 else them - 1
        "Y" -> them
        else -> (them % 3) + 1
    }

val score2 = lines.fold(0) { acc, next ->
    val (themCode, outcome) = next.split(" ")
    val them = codes[themCode]!!
    val me = choose(them, outcome)
//    println("they played $them and I need $outcome so I play $me and get ${me + score(me, them)}")
    acc + me + score(me, them)
}

// part 2: 15508
println(score2)
