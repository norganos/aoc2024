package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.PuzzleRun
import de.linkel.aoc.utils.geometry.plain.discrete.Point
import de.linkel.aoc.utils.geometry.plain.discrete.Vector
import de.linkel.aoc.utils.grid.Grid
import jakarta.inject.Singleton

@Singleton
class Day04: AbstractLinesAdventDay<Long>() {
    override val day = 4

    override fun process(puzzle: PuzzleRun, lines: Sequence<String>): Long {
        return if (puzzle.isA())
            partA(lines)
        else
            partB(lines)
    }

    private fun partA(lines: Sequence<String>): Long {
        val trails = mutableListOf<List<Pair<Point, Char>>>()
        val grid = Grid.parse(lines) { pos, char ->
            char.also { if (char == 'X') trails.add(listOf(pos to char)) }
        }
        val results = mutableListOf<List<Pair<Point, Char>>>()
        while (trails.isNotEmpty()) {
            val trail = trails.removeFirst()
            val (pos, _) = trail.last()
            if (trail.size == 1) {
                grid.getNeighbours(pos, true).forEach { neighbour ->
                    if (neighbour.data == 'M') {
                        trails.add(trail + listOf(neighbour.point to neighbour.data))
                    }
                }
            } else {
                val searchPoint = pos + (pos - trail[trail.size - 2].first)
                if (trail.size == 2) {
                    if (searchPoint in grid && grid[searchPoint] == 'A') {
                        trails.add(trail + listOf(searchPoint to 'A'))
                    }
                } else {
                    if (searchPoint in grid && grid[searchPoint] == 'S') {
                        results.add(trail + listOf(searchPoint to 'S'))
                    }
                }
            }
        }
        return results.size.toLong()
    }

    private fun partB(lines: Sequence<String>): Long {
        val aList = mutableSetOf<Point>()
        val grid = Grid.parse(lines) { pos, char ->
            char.also { if (char == 'A') aList.add(pos) }
        }
        return aList
            .filter { pos -> listOf(
                pos + Vector.NORTH_EAST,
                pos + Vector.NORTH_WEST,
                pos + Vector.SOUTH_EAST,
                pos + Vector.SOUTH_WEST,
            ).all { it in grid } }
            .count { pos ->
                listOf(
                    Vector.NORTH_EAST to Vector.SOUTH_WEST,
                    Vector.NORTH_WEST to Vector.SOUTH_EAST
                )
                    .map { (a, b) -> setOf(grid[pos + a], grid[pos + b]) }
                    .all {
                        'M' in it && 'S' in it
                    }
            }
            .toLong()
    }
}
