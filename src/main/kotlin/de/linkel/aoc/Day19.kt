package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.QuizPart
import de.linkel.aoc.utils.iterables.inTwoBlocks
import jakarta.inject.Singleton

@Singleton
class Day19: AbstractLinesAdventDay<Long>() {
    override val day = 19

    override fun process(part: QuizPart, lines: Sequence<String>): Long {
        return lines
            .inTwoBlocks(
                delimiterPredicate = { it.isEmpty() },
                block1 = { blockLines ->
                    blockLines.first()
                        .split(", ")
                        .toSet()
                },
                block2 = { towels, blockLines ->
                    if (part == QuizPart.A)
                        blockLines
                            .count {
                                this.testDesign(towels, it) >= 1L
                            }
                            .toLong()
                    else
                        blockLines
                            .sumOf {
                                this.testDesign(towels, it)
                            }
                }
            )
    }

    private var cache = mutableMapOf<String,Long>()
    private fun testDesign(towels: Set<String>, pattern: String): Long {
        return if (pattern.isEmpty()) 1L
        else
            cache[pattern] ?:
            towels
                .filter { pattern.startsWith(it) }
                .sumOf {
                    this.testDesign(towels, pattern.drop(it.length))
                }
                .also {
                    cache[pattern] = it
                }
    }
}
