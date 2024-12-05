package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.QuizPart
import jakarta.inject.Singleton

@Singleton
class Day11: AbstractLinesAdventDay<Long>() {
    override val day = 11

    override fun process(part: QuizPart, lines: Sequence<String>): Long {
        return lines.count().toLong()
    }
}
