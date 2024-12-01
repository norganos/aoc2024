package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.QuizPart
import de.linkel.aoc.utils.grid.Area
import de.linkel.aoc.utils.grid.Point
import jakarta.inject.Singleton
import java.util.*
import kotlin.time.measureTimedValue

@Singleton
class Day21: AbstractLinesAdventDay<Long>() {
    override val day = 21

    override fun process(part: QuizPart, lines: Sequence<String>): Long {
        return lines.count().toLong()
    }
}
