package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.QuizPart
import de.linkel.aoc.utils.iterables.append
import de.linkel.aoc.utils.iterables.extend
import de.linkel.aoc.utils.iterables.intersects
import de.linkel.aoc.utils.iterables.prepend
import jakarta.inject.Singleton

@Singleton
class Day03: AbstractLinesAdventDay<Long>() {
    override val day = 3

    override fun process(part: QuizPart, lines: Sequence<String>): Long {
        return lines.count().toLong()
    }
}
