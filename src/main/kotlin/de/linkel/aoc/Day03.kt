package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.PuzzleRun
import jakarta.inject.Singleton

@Singleton
class Day03: AbstractLinesAdventDay<Long>() {
    override val day = 3

    override fun process(puzzle: PuzzleRun, lines: Sequence<String>): Long {
        return if (puzzle.isA()) {
            val regex = """mul\((\d+),(\d+)\)""".toRegex()
            lines.flatMap { line ->
                val matches = regex.findAll(line)
                matches.map { match -> match.groupValues[1].toLong() * match.groupValues[2].toLong() }
            }.sum()
        } else {
            val regex = """do\(\)|don't\(\)|mul\((\d+),(\d+)\)""".toRegex()
            lines.flatMap { line ->
                val matches = regex.findAll(line)
                matches
            }.fold(0L to true) { (sum, enabled), match ->
                when (match.groupValues[0]) {
                    "do()" -> sum to true
                    "don't()" -> sum to false
                    else -> sum + (if (enabled) match.groupValues[1].toLong() * match.groupValues[2].toLong() else 0) to enabled
                }
            }.first
        }
    }
}
