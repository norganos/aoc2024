package de.linkel.aoc

class Day02Test: AbstractDayTest<Long>() {
    override val exampleA = """
7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9
        """.trimIndent()
    override val exampleSolutionA = 2L
    override val solutionA = 287L


//    override val exampleB = """
//        """.trimIndent()
    override val exampleSolutionB = 4L
    override val solutionB = 354L

    override val implementation = Day02()
}
