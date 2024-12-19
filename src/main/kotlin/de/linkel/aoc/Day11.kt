package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.PuzzleRun
import jakarta.inject.Singleton

@Singleton
class Day11: AbstractLinesAdventDay<Long>() {
    override val day = 11
    private val memory = mutableMapOf<Pair<Long, Int>, Long>()

    fun blink(value: Long): List<Long> {
        val strValue = value.toString()
        return if (value == 0L)
            listOf(1L)
        else if(strValue.length % 2 == 0)
            listOf(
                strValue.substring(0, strValue.length / 2).toLong(),
                strValue.substring(strValue.length / 2).toLong()
            )
        else
            listOf(value * 2024L)
    }

    fun blinkN(value: Long, iterations: Int): Long {
        if (iterations < 0) throw IllegalArgumentException("Iterations must be positive.")
        val key = (value to iterations)
        if (key in memory) {
            return memory[key]!!
        }
        val newValues = blink(value)
        val result = if (iterations == 1) newValues.size.toLong()
        else newValues.sumOf { blinkN(it, iterations - 1) }
        memory[key] = result
        return result
    }

    private val whitespace = Regex("\\s+")
    override fun process(puzzle: PuzzleRun, lines: Sequence<String>): Long {
        val iterations = (if (puzzle.isA()) 25 else 75)
        return lines.sumOf { line ->
            line
                .split(whitespace)
                .map { it.toLong() }
                .sumOf { blinkN(it, iterations) }
        }
    }
}
