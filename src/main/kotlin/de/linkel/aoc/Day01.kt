package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.QuizPart
import jakarta.inject.Singleton
import java.lang.Exception
import kotlin.math.abs
import kotlin.math.min

@Singleton
class Day01: AbstractLinesAdventDay<Long>() {
    override val day = 1
    private val whitespace = Regex("[ \t]+")

    override fun process(part: QuizPart, lines: Sequence<String>): Long {
        val pairs = lines
            .map { it.split(whitespace) }
            .map { Pair(it[0].toLong(), it[1].toLong()) }
            .toList()
        val lefts = pairs.map { it.first }.sorted()
        val rights = pairs.map { it.second }.sorted()
        return if (part == QuizPart.A)
            lefts.zip(rights).sumOf { (l, r) -> abs(l - r) }
        else {
            val counts = rights.groupBy { it }.mapValues { it.value.count() }
            lefts.sumOf { (counts[it]?:0) * it }
        }
    }
}
