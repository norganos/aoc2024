package de.linkel.aoc

class Day19Test: AbstractDayTest<Long>() {
    override val exampleA = """
r, wr, b, g, bwu, rb, gb, br

brwrr
bggr
gbbr
rrbgbr
ubwu
bwurrg
brgr
bbrgwb
        """.trimIndent()
    override val exampleSolutionA = 6L
    override val solutionA = 367L


//    override val exampleB = """
//        """.trimIndent()
    override val exampleSolutionB = 16L
    override val solutionB = 724388733465031L

    override val implementation = Day19()
}
