package de.linkel.aoc

class Day04Test: AbstractDayTest<Long>() {
    override val exampleA = """
MMMSXXMASM
MSAMXMSMSA
AMXSXMAAMM
MSAMASMSMX
XMASAMXAMM
XXAMMXXAMA
SMSMSASXSS
SAXAMASAAA
MAMMMXMMMM
MXMXAXMASX
        """.trimIndent()
    override val exampleSolutionA = 18L
    override val solutionA = 2603L


//    override val exampleB = """
//        """.trimIndent()
    override val exampleSolutionB = 9L
    override val solutionB = 1965L

    override val implementation = Day04()
}
