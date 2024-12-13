package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.QuizPart
import de.linkel.aoc.utils.geometry.plain.discrete.Point
import de.linkel.aoc.utils.geometry.plain.discrete.Vector
import de.linkel.aoc.utils.iterables.split
import jakarta.inject.Singleton

@Singleton
class Day13: AbstractLinesAdventDay<Long>() {
    override val day = 13

    private val buttonRegex = Regex("Button [AB]: X([-+]\\d+), Y([-+]\\d+)")
    private val prizeRegex = Regex("Prize: X=(\\d+), Y=(\\d+)")

    data class Machine(
        val a: Vector,
        val b: Vector,
        val prize: Point
    )
    data class Play(
        val a: Int,
        val b: Int
    ) {
        val cost = 3 * a + b
        fun wins(machine: Machine): Boolean
            = Point.ZERO + machine.a * a + machine.b * b == machine.prize
    }

    private fun parseButton(input: String): Vector {
        return buttonRegex.matchEntire(input)!!
            .groupValues
            .let { (_, x, y) -> Vector(x.toInt(), y.toInt()) }
    }
    private fun parsePrize(input: String): Point {
        return prizeRegex.matchEntire(input)!!
            .groupValues
            .let { (_, x, y) -> Point(x.toInt(), y.toInt()) }
    }

    private fun findBestPlay(machine: Machine): Play? {
        var bestPlay: Play? = null
        (0..100).forEach { aPresses ->
            (0..100).forEach { bPresses ->
                val play = Play(aPresses, bPresses)
                if (play.wins(machine)) {
                    if (bestPlay?.takeIf { it.cost < play.cost } == null) {
                        bestPlay = play
                    }
                }
            }
        }
        return bestPlay
    }

    override fun process(part: QuizPart, lines: Sequence<String>): Long {
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
                    prize = parsePrize(machineLines[2]),
                )
            }
            .mapNotNull { findBestPlay(it)?.cost }
            .sumOf { it.toLong() }
    }
}
