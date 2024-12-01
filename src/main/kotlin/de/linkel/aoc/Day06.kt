package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.QuizPart
import jakarta.inject.Singleton
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

@Singleton
class Day06: AbstractLinesAdventDay<Long>() {
    override val day = 6

    override fun process(part: QuizPart, lines: Sequence<String>): Long {
        return lines.count().toLong()
    }
}
