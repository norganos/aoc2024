package de.linkel.aoc

class Day09Test: AbstractDayTest<Long>() {
    override val exampleA = """
2333133121414131402
        """.trimIndent()
    /*
    00...111...2...333.44.5555.6666.777.888899
     */
    override val exampleSolutionA = 1928L
    override val solutionA = 6332189866718L


//    override val exampleB = """
//        """.trimIndent()
    override val exampleSolutionB = 2858L
    override val solutionB = 6353648390778L

    override val implementation = Day09()
}
