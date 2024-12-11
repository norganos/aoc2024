package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.QuizPart
import jakarta.inject.Singleton

@Singleton
class Day11: AbstractLinesAdventDay<Long>() {
    override val day = 11

    fun blink(value: Long): List<Long> {
        val strValue = value.toString()
        return if (value == 0L)
            listOf(1L)
        else if(strValue.length % 2 == 0)
            listOf(
                strValue.substring(0, strValue.length / 2).toLong(),
                strValue.substring(strValue.length / 2).toLong()
            )
        else
            listOf(value * 2024L)
    }

    private val whitespace = Regex("\\s+")
    override fun process(part: QuizPart, lines: Sequence<String>): Long {
        return (if (part == QuizPart.A) (0 until 25) else (0 until 75))
            .fold(
                lines.flatMap { line ->
                    line.split(whitespace).map { it.toLong() }
                }
                    .toList()
            ) { stones, _ ->
                stones.flatMap { blink(it) }
            }
            .size.toLong()
    }
}
