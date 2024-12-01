package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.QuizPart
import de.linkel.aoc.utils.grid.Area
import de.linkel.aoc.utils.grid.Point
import jakarta.inject.Singleton
import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.collections.List
import kotlin.collections.Set
import kotlin.collections.filter
import kotlin.collections.first
import kotlin.collections.forEach
import kotlin.collections.isNotEmpty
import kotlin.collections.last
import kotlin.collections.lastIndex
import kotlin.collections.listOf
import kotlin.collections.mutableSetOf
import kotlin.collections.plus
import kotlin.collections.setOf
import kotlin.collections.sumOf
import kotlin.math.max

@Singleton
class Day23: AbstractLinesAdventDay<Long>() {
    override val day = 23

    override fun process(part: QuizPart, lines: Sequence<String>): Long {
        return lines.count().toLong()
    }
}
