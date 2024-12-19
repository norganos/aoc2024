package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.PuzzleRun
import jakarta.inject.Singleton

@Singleton
class Day25: AbstractLinesAdventDay<Long>() {
    override val day = 25

    override fun process(puzzle: PuzzleRun, lines: Sequence<String>): Long {
        return lines.count().toLong()
    }
}
