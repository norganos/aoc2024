package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.PuzzleRun
import de.linkel.aoc.utils.geometry.plain.discrete.Point
import de.linkel.aoc.utils.geometry.plain.discrete.Rectangle
import de.linkel.aoc.utils.geometry.plain.discrete.Vector
import de.linkel.aoc.utils.grid.Grid
import de.linkel.aoc.utils.grid.inside
import jakarta.inject.Singleton
import java.util.*

@Singleton
class Day18: AbstractLinesAdventDay<String>() {
    override val day = 18

    override fun process(puzzle: PuzzleRun, lines: Sequence<String>): String {
        val bytes = lines.toList()
        val area = if (puzzle.example) Rectangle(0, 0, 7, 7) else Rectangle(0,0, 71, 71)
        return if (puzzle.isA()) {
            val steps = if (bytes.count() < 30) 12 else 1024
            simulate(bytes, area, steps).toString()
        } else {
            var free = if (bytes.count() < 30) 12 else 1024
            var blocked = bytes.count()
            while (true) {
                if (free + 1 == blocked) break
                val steps = free + (blocked - free) / 2
                val res = simulate(bytes, area, steps)
                if (res == -1L)
                    blocked = steps
                else free = steps
            }
            return bytes[free]
        }
    }

    private fun simulate(lines: List<String>, area: Rectangle, steps: Int): Long {
        return lines
            .take(steps)
            .fold(Grid<Boolean>(origin = area.origin, dimension = area.dimension)) { grid, line ->
                grid[Point(line.substringBefore(",").toInt(), line.substringAfter(",").toInt())] = true
                grid
            }
            .let {
                dijkstra(it, area.northWest, area.southEast)
            }
    }

    private fun dijkstra(grid: Grid<Boolean>, start: Point, end: Point): Long {
        val queue = PriorityQueue<Pair<Point, Long>>(compareBy { it.second })
        queue.add(Pair(start, 0L))
        val queued = mutableSetOf(start)
        val visited = mutableSetOf<Point>()
        while (queue.isNotEmpty()) {
            val (point, cost) = queue.poll()
            queued.remove(point)
            if (point == end) {
                return cost
            }
            if (point in visited)
                continue
            visited.add(point)
            listOf(
                Vector.NORTH,
                Vector.WEST,
                Vector.EAST,
                Vector.SOUTH,
            )
                .map { dir -> point + dir }
                .filter { it inside grid }
                .filter { it !in grid }
                .filter { it !in visited }
                .filter { it !in queued }
                .forEach {
                    queue.add(Pair(it, cost + 1))
                    queued.add(it)
                }
        }
        return -1L
    }
}
