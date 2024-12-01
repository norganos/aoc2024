package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.QuizPart
import de.linkel.aoc.utils.iterables.combineWith
import de.linkel.aoc.utils.iterables.split
import de.linkel.aoc.utils.replaceIndex
import jakarta.inject.Singleton
import kotlin.math.min

@Singleton
class Day13: AbstractLinesAdventDay<Long>() {
    override val day = 13

    override fun process(part: QuizPart, lines: Sequence<String>): Long {
        return lines.count().toLong()
    }
}
