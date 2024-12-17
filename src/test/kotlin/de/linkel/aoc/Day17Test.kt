package de.linkel.aoc

class Day17Test: AbstractDayTest<String>() {
    override val exampleA = """
Register A: 729
Register B: 0
Register C: 0

Program: 0,1,5,4,3,0
        """.trimIndent()
    override val exampleSolutionA = "4,6,3,5,6,3,5,2,1,0"
    override val solutionA = "1,5,0,3,7,3,0,3,1"


    override val exampleB = """
Register A: 2024
Register B: 0
Register C: 0

Program: 0,3,5,4,3,0
        """.trimIndent()
    override val exampleSolutionB = "117440"
    override val solutionB = "105981155568026"

    override val implementation = Day17()
}
