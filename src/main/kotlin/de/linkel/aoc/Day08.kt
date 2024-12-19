package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.PuzzleRun
import de.linkel.aoc.utils.grid.Grid
import de.linkel.aoc.utils.grid.inside
import de.linkel.aoc.utils.iterables.permutations
import jakarta.inject.Singleton

@Singleton
class Day08: AbstractLinesAdventDay<Long>() {
    override val day = 8

    override fun process(puzzle: PuzzleRun, lines: Sequence<String>): Long {
        return Grid.parse(lines, crop = false) { _, char -> char.takeIf { it != '.' } }
            .let { grid ->
                grid.getAllData()
                    .groupBy { it.data }
                    .mapValues { (_, values) -> values.map { it.point } }
                    .flatMap { (_, antennas) ->
                        antennas
                            .permutations(2)
                            .filter { it.size == 2 }
                            .map { it.toSet() }
                            .toSet()
                            .map { it.first() to it.last() }
                            .flatMap { (a, b) ->
                                if (puzzle.isA())
                                    listOf(
                                        a + (a - b),
                                        b + (b - a),
                                    )
                                else {
                                    (a - b).min()
                                        .let {
                                            grid.straightLine(a, it) + grid.straightLine(a, it.turnAround())
                                        }
                                }
                            }
                            .filter { it inside grid }
                    }
                    .toSet()
                    .size.toLong()
            }
    }
}

