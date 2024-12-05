package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.QuizPart
import jakarta.inject.Singleton

@Singleton
class Day25: AbstractLinesAdventDay<Long>() {
    override val day = 25

    override fun process(part: QuizPart, lines: Sequence<String>): Long {
        return lines.count().toLong()
    }
}
