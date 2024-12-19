package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.PuzzleRun
import de.linkel.aoc.utils.geometry.plain.discrete.Point
import de.linkel.aoc.utils.grid.Grid
import jakarta.inject.Singleton

@Singleton
class Day10: AbstractLinesAdventDay<Long>() {
    override val day = 10

    override fun process(puzzle: PuzzleRun, lines: Sequence<String>): Long {
        return Grid.parse(lines) { pos, char -> char.digitToInt() }
            .let { grid ->
                grid.getAllData()
                    .filter { it.data == 0 }
                    .flatMap { hike(grid, listOf(it.point)) }
                    .let { trails ->
                        if (puzzle.isA())
                            trails
                                .map { it.first() to it.last() }
                                .toSet()
                                .groupingBy { it.first }.eachCount()
                                .entries
                                .sumOf { it.value.toLong() }
                        else
                            trails
                                .groupingBy { it.first() }
                                .eachCount()
                                .entries
                                .sumOf { it.value.toLong() }
                    }
            }
    }

    private fun hike(grid: Grid<Int>, trail: List<Point>): Set<List<Point>> {
        val height = grid[trail.last()]!!
        return grid.getNeighbours(trail.last(), diagonal = false)
            .filter { it.data == height + 1 }
            .map { it to trail + it.point }
            .flatMap { (dp, nt) ->
                if (dp.data == 9) setOf(nt)
                else hike(grid, nt)
            }
            .toSet()
    }
}
