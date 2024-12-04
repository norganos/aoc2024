package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.QuizPart
import de.linkel.aoc.utils.grid.Grid
import de.linkel.aoc.utils.grid.Point
import de.linkel.aoc.utils.grid.Vector
import jakarta.inject.Singleton
import kotlin.math.pow

@Singleton
class Day04: AbstractLinesAdventDay<Long>() {
    override val day = 4

    override fun process(part: QuizPart, lines: Sequence<String>): Long {
        return if (part == QuizPart.A)
            partA(lines)
        else
            partB(lines)
    }

    fun partA(lines: Sequence<String>): Long {
        val trails = mutableListOf<List<Pair<Point, Char>>>()
        val grid = Grid.parse(lines) { pos, char ->
            char.also { if (char == 'X') trails.add(listOf(pos to char)) }
        }
        val results = mutableListOf<List<Pair<Point, Char>>>()
        while (trails.isNotEmpty()) {
            val trail = trails.removeFirst()
            val (pos, char) = trail.last()
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

    fun partB(lines: Sequence<String>): Long {
        val aList = mutableSetOf<Point>()
        val grid = Grid.parse(lines) { pos, char ->
            char.also { if (char == 'A') aList.add(pos) }
        }
        return aList
            .map { pos ->
                pos to listOf(
                    pos + Vector(-1,-1),
                    pos + Vector(1,-1),
                    pos + Vector(1,1),
                    pos + Vector(-1,1),
                )
            }
            .filter { (_, neighbours) -> neighbours.all { it in grid } }
            .count { (_, neighbours) ->
                neighbours
                    .map { grid[it] }
                    .groupingBy { it }
                    .eachCount()
                    .let {
                        it['M'] == 2 && it['S'] == 2
                    }
            }
            .toLong()
    }
}
