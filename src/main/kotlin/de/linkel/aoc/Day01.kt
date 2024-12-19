package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.PuzzleRun
import jakarta.inject.Singleton
import kotlin.math.abs

@Singleton
class Day01: AbstractLinesAdventDay<Long>() {
    override val day = 1
    private val whitespace = Regex("[ \t]+")

    override fun process(puzzle: PuzzleRun, lines: Sequence<String>): Long {
        val (lefts, rights) = lines
            .map { it.split(whitespace) }
            .map { Pair(it[0].toLong(), it[1].toLong()) }
            .toList()
            .unzip()
        return if (puzzle.isA())
            lefts.sorted().zip(rights.sorted())
                .sumOf { (l, r) -> abs(l - r) }
        else {
            val counts = rights
                .groupingBy { it }.eachCount()
            lefts.sumOf { (counts[it]?:0) * it }
        }
    }
}
