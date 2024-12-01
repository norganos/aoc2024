package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.QuizPart
import de.linkel.aoc.utils.lcm
import jakarta.inject.Singleton

@Singleton
class Day20: AbstractLinesAdventDay<Long>() {
    override val day = 20

    override fun process(part: QuizPart, lines: Sequence<String>): Long {
        return lines.count().toLong()
    }
}
