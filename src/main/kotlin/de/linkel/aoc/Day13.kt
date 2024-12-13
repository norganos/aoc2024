package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.QuizPart
import de.linkel.aoc.utils.iterables.split
import de.linkel.aoc.utils.math.algebra.Equations
import de.linkel.aoc.utils.math.algebra.Matrix
import jakarta.inject.Singleton
import java.math.BigDecimal

@Singleton
class Day13: AbstractLinesAdventDay<BigDecimal>() {
    override val day = 13

    private val regex = Regex("(?:Prize|Button [AB]): X[=+](\\d+), Y[=+](\\d+)")

    private fun parseValues(input: String): Pair<BigDecimal, BigDecimal> {
        return regex.matchEntire(input)!!
            .groupValues
            .let { (_, x, y) -> x.toBigDecimal() to y.toBigDecimal() }
    }

    private val three = BigDecimal("3")
    override fun process(part: QuizPart, lines: Sequence<String>): BigDecimal {
        val offset = if (part == QuizPart.B) BigDecimal("10000000000000") else BigDecimal.ZERO
        return lines
            .split { it.isEmpty() }
            .map { machineLines ->
                require(machineLines.size == 3)
                require(machineLines[0].startsWith("Button A: "))
                require(machineLines[1].startsWith("Button B: "))
                require(machineLines[2].startsWith("Prize: "))
                val a = parseValues(machineLines[0])
                val b = parseValues(machineLines[1])
                val p = parseValues(machineLines[2])
                Matrix(2, 2, listOf(a.first, b.first, a.second, b.second)) to listOf(p.first + offset, p.second + offset)
            }
            .mapNotNull { (coefficients, results) -> Equations.cramer(coefficients, results) }
            .sumOf { (a, b) -> a * three + b }
    }
}
