package de.linkel.aoc

class Day03Test: AbstractDayTest<Long>() {
    override val exampleA = """
xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))
        """.trimIndent()
    override val exampleSolutionA = 161L
    override val solutionA = 174336360L


    override val exampleB = """
xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))
        """.trimIndent()
    override val exampleSolutionB = 48L
    override val solutionB = 88802350L

    override val implementation = Day03()
}
