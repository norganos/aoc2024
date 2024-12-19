package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.PuzzleRun
import de.linkel.aoc.utils.geometry.plain.discrete.Point
import de.linkel.aoc.utils.geometry.plain.discrete.Vector
import de.linkel.aoc.utils.grid.Grid
import de.linkel.aoc.utils.grid.inside
import jakarta.inject.Singleton

@Singleton
class Day06: AbstractLinesAdventDay<Long>() {
    override val day = 6

    data class Guard(
        val position: Point,
        val direction: Vector,
    ) {
        val forward get() = copy(position = position + direction)
        val turn get() = copy(direction = direction.turnClockwise())
    }
    class Obstacle


    override fun process(puzzle: PuzzleRun, lines: Sequence<String>): Long {
        val path = mutableListOf<Guard>()
        val grid = Grid.parse(lines) { pos, char ->
            when (char) {
                '^' -> Vector.NORTH
                '<' -> Vector.WEST
                '>' -> Vector.EAST
                'v' -> Vector.SOUTH
                else -> null
            }
                ?.also {
                    path.add(
                        Guard(position = pos, direction = it)
                    )
                }
            if (char == '#') Obstacle() else null
        }
        while (path.last().position inside grid) {
            path.last()
                .let { current ->
                    current.forward
                        .takeIf { it.position !in grid }
                        ?: current.turn
                }
                .also {
                    path.add(it)
                }
        }
        path.removeLast()
        return if (puzzle.isA()) {
            path.map { it.position }.toSet().size.toLong()
        } else {
            try {
                path
                    .drop(1)
                    .map { it.position }
                    .toSet()
                    .count { pos ->
                        grid[pos] = Obstacle()
                        val cycle = detectCycle(grid, path.first())
                        grid[pos] = null
                        cycle
                    }
                    .toLong()
            } catch(e: Throwable) {
                e.printStackTrace()
                0L
            }
        }
    }

//    private fun Point.runUntil(vector: Vector, stop: (point: Point) -> Boolean): Point {
//        var steps = 0
//        var pos = this
//        while (true) {
//            pos += vector
//            if (stop(pos)) return pos
//        }
//    }
//
//    private fun Point.walk(vector: Vector, next: (point: Point, direction: Vector) -> Vector?): Pair<Boolean, List<Pair<Point, Vector>>> {
//        val path = mutableListOf<Pair<Point, Vector>>(this to vector)
//        var direction = vector
//        var pos = this
//        while (true) {
//            pos += direction
//            if (pos to direction in path) {
//                return true to path
//            }
//            path.add(pos to direction)
//            val newDir = next(pos, direction)
//            if (newDir == null) {
//                return false to path
//            } else {
//                direction = newDir
//            }
//        }
//    }

    private fun detectCycle(grid: Grid<Obstacle>, start: Guard): Boolean {
        val visited = mutableSetOf<Guard>()
        var current = start
        while (current.position inside grid) {
            visited.add(current)
            current = current.forward
                .takeIf { it.position !in grid }
                ?: current.turn
            if (current in visited) {
                return true
            }
        }
        return false
    }
}
