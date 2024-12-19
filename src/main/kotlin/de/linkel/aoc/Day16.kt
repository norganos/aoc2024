package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.PuzzleRun
import de.linkel.aoc.utils.geometry.plain.discrete.Point
import de.linkel.aoc.utils.geometry.plain.discrete.Vector
import de.linkel.aoc.utils.grid.Grid
import jakarta.inject.Singleton
import java.util.*

@Singleton
class Day16: AbstractLinesAdventDay<Long>() {
    override val day = 16

    override fun process(puzzle: PuzzleRun, lines: Sequence<String>): Long {
        var start: Point = Point.ZERO
        var end: Point = Point.ZERO
        val maze = Grid
            .parse(lines) { pos, char ->
                char
                    .also { if (it == 'S') start = pos }
                    .also { if (it == 'E') end = pos }
                    .takeIf { it == '#' }
            }
        return dijkstra(maze, start, end)
            .let { (paths, cost) ->
                if (puzzle.isA())
                    cost
                else
                    paths.flatten()
                        .toSet()
                        .size
                        .toLong()
            }
    }

    private fun dijkstra(grid: Grid<Char>, start: Point, end: Point): Pair<Set<List<Point>>, Long> {
        val queue = PriorityQueue<Triple<List<Point>, Vector, Long>>(compareBy { it.third })
        queue.add(Triple(listOf(start), Vector.EAST, 0L))
        // if I remove the Vector from the key, it produces wrong result on my input (example works though)
        // took me 45min to pinpoint that problem, yet not sure, why...
        val visited = mutableMapOf<Pair<Point, Vector>, Long>()
        val bestPaths = mutableSetOf<List<Point>>()
        var bestCost = Long.MAX_VALUE
        while (queue.isNotEmpty()) {
            val (trail, direction, cost) = queue.poll()
            val point = trail.last()
            if (point == end) {
                // we will never have late arrivals at the end with a higher score, so no the < is the first arrival case
                if (cost <= bestCost) {
                    bestPaths.add(trail)
                    bestCost = cost
                } else
                    break
            }
            if (visited[point to direction] != null && visited[point to direction]!! < cost)
                continue
            visited[point to direction] = cost
            listOf(
                direction to 1L,
                direction.turnClockwise() to 1001L,
                direction.turnCounterClockwise() to 1001L
            )
                .map { (dir, stepCost) -> Triple(trail + (point + dir), dir, cost + stepCost) }
                .filter { grid[it.first.last()] != '#' }
                .filter { it.first.last() to it.second !in visited }
                .forEach {
                    queue.add(it)
                }
        }
        return bestPaths to bestCost
    }

}
