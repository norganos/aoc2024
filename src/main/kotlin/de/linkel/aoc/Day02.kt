package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.QuizPart
import de.linkel.aoc.utils.iterables.withoutIndex
import jakarta.inject.Singleton
import kotlin.math.abs

@Singleton
class Day02: AbstractLinesAdventDay<Long>() {
    override val day = 2

    private fun isReportSafe(levels: List<Long>): Boolean {
        return (levels == levels.sorted() || levels == levels.sortedDescending())
                && levels.zipWithNext { a, b -> abs(a-b) }.all { it in 1..3 }
    }

    override fun process(part: QuizPart, lines: Sequence<String>): Long {
        return if (part == QuizPart.A)
            lines
                .map { line -> line.split(" ").map { it.toLong() } }
                .count { isReportSafe(it) }.toLong()
        else
            lines
                .map { line -> line.split(" ").map { it.toLong() } }
                .count { levels ->
                    isReportSafe(levels) ||
                            levels.indices.any { idx ->
                                isReportSafe(levels.withoutIndex(idx))
                            }
                }.toLong()
    }
}
