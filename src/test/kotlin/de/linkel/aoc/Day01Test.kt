package de.linkel.aoc

class Day01Test: AbstractDayTest<Long>() {
    override val exampleA = """
3   4
4   3
2   5
1   3
3   9
3   3
        """.trimIndent()
    override val exampleSolutionA = 11L
    override val solutionA = 1388114L


//    override val exampleB = """
//        """.trimIndent()
    override val exampleSolutionB = 31L
    override val solutionB = 23529853L

    override val implementation = Day01()
}
