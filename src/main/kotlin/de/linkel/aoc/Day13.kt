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
                val ax = a.x
                val ay = a.y
                val bx = b.x
                val by = b.y
                val mx = p.x
                val my = p.y

                val dab = det(ax, bx, ay, by)
                val dmb = det(mx, bx, my, by)
                val dam = det(ax, mx, ay, my)
                if (dmb % dab == BigDecimal.ZERO && dam % dab == BigDecimal.ZERO) {
                    val a = dmb / dab
                    val b = dam / dab
                    Play(a, b)
                } else null




//                val b = (my - ((ay * mx) / ax)) / (by - ((ay * bx) / ax))
//                val a = (mx - bx * b) / ax
//
//                Play(a, b)
//                    .takeIf { it.a >= BigDecimal.ZERO && it.b >= BigDecimal.ZERO && it.wins(this) }
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
        fun wins(machine: Machine): Boolean
            = machine.a * a + machine.b * b == machine.p
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

    private fun findBestPlay(machine: Machine): Play? {
        var bestPlay: Play? = null
        (0..100).forEach { aPresses ->
            (0..100).forEach { bPresses ->
                val play = Play(aPresses.toBigDecimal(), bPresses.toBigDecimal())
                if (play.wins(machine)) {
                    if (bestPlay?.takeIf { it.cost < play.cost } == null) {
                        bestPlay = play
                    }
                }
            }
        }
        return bestPlay
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
            .mapNotNull {
                val solved = it.solve()
                if (part == QuizPart.A && solved == null) {
                    val brute = findBestPlay(it)
                    if (brute != null) {
                        println("$it -> $solved / $brute")
                        it.solve()
                    }
                }
                solved
            }
            .sumOf { it.cost }
    }
}
