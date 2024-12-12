package de.linkel.aoc

class Day12Test: AbstractDayTest<Long>() {
    override val exampleA = """
RRRRIICCFF
RRRRIICCCF
VVRRRCCFFF
VVRCCCJFFF
VVVVCJJCFE
VVIVCCJJEE
VVIIICJJEE
MIIIIIJJEE
MIIISIJEEE
MMMISSJEEE
        """.trimIndent()
    override val exampleSolutionA = 1930L
    override val solutionA = 1449902L


//    override val exampleB = """
//        """.trimIndent()
    override val exampleSolutionB = 1206L
    override val solutionB = 908042L

    override val implementation = Day12()
}
