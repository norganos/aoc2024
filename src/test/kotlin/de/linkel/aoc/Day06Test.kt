package de.linkel.aoc

class Day06Test: AbstractDayTest<Long>() {
    override val exampleA = """
....#.....
.........#
..........
..#.......
.......#..
..........
.#..^.....
........#.
#.........
......#...
        """.trimIndent()
    override val exampleSolutionA = 41L
    override val solutionA = 5564L


//    override val exampleB = """
//        """.trimIndent()
    override val exampleSolutionB = 6L
    override val solutionB = 0L

    override val implementation = Day06()
}
