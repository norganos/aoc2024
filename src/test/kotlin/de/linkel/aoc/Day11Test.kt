package de.linkel.aoc

class Day11Test: AbstractDayTest<Long>() {
    override val exampleA = """
125 17
        """.trimIndent()
    override val exampleSolutionA = 55312L
    override val solutionA = 185894L


//    override val exampleB = """
//        """.trimIndent()
    override val exampleSolutionB = 65601038650482L
    override val solutionB = 221632504974231L

    override val implementation = Day11()
}
