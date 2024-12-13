package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.QuizPart
import de.linkel.aoc.utils.geometry.plain.discrete.Point
import de.linkel.aoc.utils.geometry.plain.discrete.Vector
import de.linkel.aoc.utils.iterables.split
import jakarta.inject.Singleton
import java.math.BigDecimal

@Singleton
class Day13: AbstractLinesAdventDay<BigDecimal>() {
    override val day = 13

    private val buttonRegex = Regex("Button [AB]: X([-+]\\d+), Y([-+]\\d+)")
    private val prizeRegex = Regex("Prize: X=(\\d+), Y=(\\d+)")

    data class LongVector(
        val x: BigDecimal,
        val y: BigDecimal
    ) {
        operator fun plus(other: LongVector) = LongVector(x + other.x, y + other.y)
        operator fun times(amount: BigDecimal) = LongVector(x * amount, y * amount)
    }

    data class Machine(
        val a: LongVector,
        val b: LongVector,
        val p: LongVector
    ) {
        fun solve(): Play? {
            fun det(a: BigDecimal, b: BigDecimal, c: BigDecimal, d: BigDecimal)
                = a * d - b * c
            return try {
                val dab = det(a.x, b.x, a.y, b.y)
                val dmb = det(p.x, b.x, p.y, b.y)
                val dam = det(a.x, p.x, a.y, p.y)
                if (dmb % dab == BigDecimal.ZERO && dam % dab == BigDecimal.ZERO) {
                    Play(dmb / dab, dam / dab)
                } else null
            } catch (e: Exception) {
                null
            }
        }
    }
    data class Play(
        val a: BigDecimal,
        val b: BigDecimal
    ) {
        val cost = a + a + a + b
    }

    private fun parseButton(input: String): LongVector {
        return buttonRegex.matchEntire(input)!!
            .groupValues
            .let { (_, x, y) -> LongVector(x.toBigDecimal(), y.toBigDecimal()) }
    }
    private fun parsePrize(input: String): LongVector {
        return prizeRegex.matchEntire(input)!!
            .groupValues
            .let { (_, x, y) -> LongVector(x.toBigDecimal(), y.toBigDecimal()) }
    }

    override fun process(part: QuizPart, lines: Sequence<String>): BigDecimal {
        val offset = if (part == QuizPart.B) LongVector(BigDecimal("10000000000000"), BigDecimal("10000000000000")) else LongVector(
            BigDecimal.ZERO, BigDecimal.ZERO)
        return lines
            .split { it.isEmpty() }
            .map { machineLines ->
                require(machineLines.size == 3)
                require(machineLines[0].startsWith("Button A: "))
                require(machineLines[1].startsWith("Button B: "))
                require(machineLines[2].startsWith("Prize: "))
                Machine(
                    a = parseButton(machineLines[0]),
                    b = parseButton(machineLines[1]),
                    p = parsePrize(machineLines[2]) + offset
                )
            }
            .mapNotNull { it.solve() }
            .sumOf { it.cost }
    }
}
