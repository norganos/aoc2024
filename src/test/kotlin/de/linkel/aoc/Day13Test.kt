package de.linkel.aoc

import java.math.BigDecimal

class Day13Test: AbstractDayTest<BigDecimal>() {
    override val exampleA = """
Button A: X+94, Y+34
Button B: X+22, Y+67
Prize: X=8400, Y=5400

Button A: X+26, Y+66
Button B: X+67, Y+21
Prize: X=12748, Y=12176

Button A: X+17, Y+86
Button B: X+84, Y+37
Prize: X=7870, Y=6450

Button A: X+69, Y+23
Button B: X+27, Y+71
Prize: X=18641, Y=10279
        """.trimIndent()
    override val exampleSolutionA = BigDecimal("480")
    override val solutionA = BigDecimal("27105")


//    override val exampleB = """
//        """.trimIndent()
    override val exampleSolutionB = BigDecimal("875318608908")
    override val solutionB = BigDecimal("101726882250942")

    override val implementation = Day13()
}
