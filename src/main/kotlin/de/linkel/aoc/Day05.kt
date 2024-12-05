package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.QuizPart
import jakarta.inject.Singleton

@Singleton
class Day05: AbstractLinesAdventDay<Long>() {
    override val day = 5

    data class Buffer(
        val rules: List<Pair<Long, Long>> = emptyList(),
        val pages: List<List<Long>> = emptyList(),
    )

    class PageComparator(
        rules: List<Pair<Long, Long>>
    ): Comparator<Long> {
        val before = rules.groupBy { (from, _) -> from }.mapValues { (_, v) -> v.map { it.second } }
        val after = rules.groupBy { (_, to) -> to }.mapValues { (_, v) -> v.map { it.first } }

        override fun compare(o1: Long?, o2: Long?): Int {
            return if (before[o1]?.contains(o2) == true || after[o2]?.contains(o1) == true)
                -1
            else if (before[o2]?.contains(o1) == true || after[o1]?.contains(o2) == true)
                1
            else
                0
        }
    }

    override fun process(part: QuizPart, lines: Sequence<String>): Long {
        return lines.fold(Buffer()) { buffer, line ->
            if (line.contains("|")) {
                buffer.copy(
                    rules = buffer.rules
                            + (line.substringBefore("|").toLong() to line.substringAfterLast("|").toLong())
                )
            } else if (line.isNotEmpty()) {
                buffer.copy(
                    pages = buffer.pages + listOf(line.split(",").map(String::toLong))
                )
            } else buffer
        }.let { buffer ->
            if (part == QuizPart.A) {
                buffer.pages.filter { pages ->
                    pages == pages.sortedWith(PageComparator(buffer.rules))
                }
                    .sumOf { it[it.size / 2] }
            } else {
                buffer.pages.mapNotNull { pages ->
                    pages.sortedWith(PageComparator(buffer.rules))
                        .takeIf { it != pages }
                }
                    .sumOf { it[it.size / 2] }
            }
        }
    }
}
