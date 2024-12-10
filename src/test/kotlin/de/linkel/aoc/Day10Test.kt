package de.linkel.aoc

class Day10Test: AbstractDayTest<Long>() {
    override val exampleA = """
89010123
78121874
87430965
96549874
45678903
32019012
01329801
10456732
        """.trimIndent()
    override val exampleSolutionA = 36L
    override val solutionA = 811L


//    override val exampleB = """
//        """.trimIndent()
    override val exampleSolutionB = 81L
    override val solutionB = 1794L

    override val implementation = Day10()
}
