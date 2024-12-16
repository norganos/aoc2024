package de.linkel.aoc

class Day16Test: AbstractDayTest<Long>() {
    override val exampleA = """
#################
#...#...#...#..E#
#.#.#.#.#.#.#.#.#
#.#.#.#...#...#.#
#.#.#.#.###.#.#.#
#...#.#.#.....#.#
#.#.#.#.#.#####.#
#.#...#.#.#.....#
#.#.#####.#.###.#
#.#.#.......#...#
#.#.###.#####.###
#.#.#...#.....#.#
#.#.#.#####.###.#
#.#.#.........#.#
#.#.#.#########.#
#S#.............#
#################
        """.trimIndent()
    override val exampleSolutionA = 11048L
    override val solutionA = 73404L


//    override val exampleB = """
//        """.trimIndent()
    override val exampleSolutionB = 0L
    override val solutionB = 0L

    override val implementation = Day16()
}
