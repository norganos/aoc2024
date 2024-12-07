package de.linkel.aoc

class Day07Test: AbstractDayTest<Long>() {
    override val exampleA = """
190: 10 19
3267: 81 40 27
83: 17 5
156: 15 6
7290: 6 8 6 15
161011: 16 10 13
192: 17 8 14
21037: 9 7 18 13
292: 11 6 16 20
        """.trimIndent()
    override val exampleSolutionA = 3749L
    override val solutionA = 1582598718861L
                           //1582196515960L


//    override val exampleB = """
//        """.trimIndent()
    override val exampleSolutionB = 11387L
    override val solutionB = 165278151522644L

    override val implementation = Day07()
}
