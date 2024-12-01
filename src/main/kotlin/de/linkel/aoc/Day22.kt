package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.QuizPart
import de.linkel.aoc.utils.grid.Point
import de.linkel.aoc.utils.space.discrete.Point3d
import jakarta.inject.Singleton

@Singleton
class Day22: AbstractLinesAdventDay<Long>() {
    override val day = 22

    override fun process(part: QuizPart, lines: Sequence<String>): Long {
        return lines.count().toLong()
    }
}
