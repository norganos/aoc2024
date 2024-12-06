package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.QuizPart
import de.linkel.aoc.utils.geometry.plain.discrete.Point
import de.linkel.aoc.utils.geometry.plain.discrete.Vector
import de.linkel.aoc.utils.grid.Grid
import de.linkel.aoc.utils.grid.inside
import jakarta.inject.Singleton
import kotlin.jvm.Throws
import kotlin.math.round

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

    override fun process(part: QuizPart, lines: Sequence<String>): Long {
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
        return if (part == QuizPart.A) {
            path.map { it.position }.toSet().size.toLong()
        } else {
            val trail = path.foldIndexed(
                Grid<List<Pair<Vector, Int>>>(grid.boundingBox.origin, grid.boundingBox.dimension)
            ) { index, guardGrid, guard ->
                guardGrid[guard.position] = (guardGrid[guard.position] ?: emptyList()) + listOf(guard.direction to index)
                guardGrid
            }
            try {
                path
                    .withIndex()
                    .filter { (_, g) -> g.forward.position !in grid }
                    .filter { (_, g) -> g.forward.position inside grid }
                    .count { (index, guard) ->
                        val (loop, rerouted) = guard.position.walk(guard.turn.direction) { pos, dir ->
                            if (!(pos inside grid)) null
                            else if (pos in grid) dir.turnClockwise()
                            else if (pos in trail && trail[pos]!!.any { it.first == dir && it.second < index }) null
                            else dir
                        }
                        loop || rerouted.last().first inside grid
                    }
                    .toLong()
            } catch(e: Throwable) {
                e.printStackTrace()
                0L
            }
//            val intersections = path.groupingBy { it.position }.eachCount().filterValues { it > 1 }
//            path.withIndex()
//                .filter { (_, guard) -> guard.position in intersections }
//                // .filter { (_, guard) -> guard.forward.position !in grid } // should not happen
//                .sumOf { (index, guard) ->
//                    path
//                        .filterIndexed { i, g -> i < index && g.position == guard.position }
//                        .count { g ->
//                            g.direction == guard.turn.direction && guard.forward.position inside grid
//                        }
//                }
//                .toLong()
        }
    }

    private fun Point.runUntil(vector: Vector, stop: (point: Point) -> Boolean): Point {
        var steps = 0
        var pos = this
        while (true) {
            pos += vector
            if (stop(pos)) return pos
        }
    }

    private fun Point.walk(vector: Vector, next: (point: Point, direction: Vector) -> Vector?): Pair<Boolean, List<Pair<Point, Vector>>> {
        val path = mutableListOf<Pair<Point, Vector>>(this to vector)
        var direction = vector
        var pos = this
        while (true) {
            pos += direction
            if (pos to direction in path) {
                return true to path
            }
            path.add(pos to direction)
            val newDir = next(pos, direction)
            if (newDir == null) {
                return false to path
            } else {
                direction = newDir
            }
        }
    }
}
