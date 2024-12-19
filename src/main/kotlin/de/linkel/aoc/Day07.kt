package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.PuzzleRun
import jakarta.inject.Singleton

@Singleton
class Day07: AbstractLinesAdventDay<Long>() {
    override val day = 7

    interface Operator {
        fun calculate(a: Long, b: Long): Long
    }
    object Plus: Operator {
        override fun calculate(a: Long, b: Long): Long {
            return a + b
        }
        override fun toString(): String {
            return "+"
        }
    }
    object Times: Operator {
        override fun calculate(a: Long, b: Long): Long {
            return a * b
        }
        override fun toString(): String {
            return "*"
        }
    }
    object Concat: Operator {
        override fun calculate(a: Long, b: Long): Long {
            return (a.toString() + b.toString()).toLong()
        }
        override fun toString(): String {
            return "||"
        }
    }

    fun testCalculation(operators: List<Operator>, desired: Long, result: Long, input: List<Long>): Boolean {
        val tail = input.drop(1)
        val operand = input.first()
        for (operator in operators) {
            val newResult = operator.calculate(result, operand)
            if (newResult == desired && tail.isEmpty()) {
                return true
            } else if (newResult <= desired && tail.isNotEmpty() && testCalculation(operators, desired, newResult, tail)) {
                return true
            }
        }
        return false
    }

    override fun process(puzzle: PuzzleRun, lines: Sequence<String>): Long {
        val operators = if (puzzle.isA()) listOf(Plus, Times) else listOf(Plus, Times, Concat)
        return lines
            .map { line ->
                val tv = line.substringBefore(":").toLong()
                val others = line.substringAfter(": ").split(" ").map { it.toLong() }
                tv to others
            }
            .filter { (tv, vs) ->
                testCalculation(operators, tv, vs.first(), vs.drop(1))
            }
            .sumOf { (tv, _) -> tv }
    }
}
