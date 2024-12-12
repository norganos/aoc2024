package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.QuizPart
import de.linkel.aoc.utils.geometry.plain.discrete.Point
import de.linkel.aoc.utils.geometry.plain.discrete.Vector
import de.linkel.aoc.utils.grid.Grid
import jakarta.inject.Singleton
import java.util.stream.IntStream.IntMapMultiConsumer

@Singleton
class Day12: AbstractLinesAdventDay<Long>() {
    override val day = 12

    data class Region(
        val value: Char,
        val area: Int,
        val perimeter: Int,
        val sides: Int,
    )
    override fun process(part: QuizPart, lines: Sequence<String>): Long {
        val grid = Grid.parse(lines) { pos, char -> char }
        val regions = mutableListOf<Region>()
        val grid2 = grid.copy()
        while (grid2.size > 0) {
            val start = grid2.getAllData().first()
            val value = start.data
            var boundary = setOf(start.point)
            val current = mutableSetOf<Point>()
            while (boundary.isNotEmpty()) {
                current.addAll(boundary)
                boundary = boundary
                    .flatMap { grid2.getNeighbours(it) }
                    .filter { it.data == value }
                    .map { it.point }
                    .filter { it !in current }
                    .toSet()
            }
            current.forEach { pos -> grid2[pos] = null }
            val area = current.size
            val perimeter = current
                .flatMap { p ->
                    listOf(
                        Vector.NORTH to p + Vector.NORTH,
                        Vector.EAST to p + Vector.EAST,
                        Vector.WEST to p + Vector.WEST,
                        Vector.SOUTH to p + Vector.SOUTH,
                    )
                }
                .filter { (_, o) -> o !in grid.boundingBox || grid[o] != value }
            val perimeter2 = perimeter.toMutableSet()
            val sides = mutableListOf<Pair<Vector, Set<Point>>>()
            while (perimeter2.isNotEmpty()) {
                val current = mutableSetOf<Point>()
                val start = perimeter2.first()
                val side = start.first
                var boundary = setOf(start.second)
                while (boundary.isNotEmpty()) {
                    current.addAll(boundary)
                    boundary = boundary
                        .flatMap {
                            listOf(
                                it + side.turnClockwise(),
                                it + side.turnCounterClockwise(),
                            )
                        }
                        .map { side to it }
                        .filter { it in perimeter2 }
                        .map { it.second }
                        .filter { it !in current }
                        .toSet()
                }
                sides.add(side to current)
                perimeter2.removeAll(current.map { side to it }.toSet())
            }
            regions.add(
                Region(value = value, area = area, perimeter = perimeter.size, sides = sides.size)
            )
            current.clear()
        }
        return if (part == QuizPart.A)
            regions.sumOf { it.area.toLong() * it.perimeter.toLong() }
        else
            regions.sumOf { it.area.toLong() * it.sides.toLong() }
    }
}
