package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.QuizPart
import de.linkel.aoc.utils.geometry.plain.discrete.Point
import de.linkel.aoc.utils.geometry.plain.discrete.Vector
import de.linkel.aoc.utils.grid.Grid
import de.linkel.aoc.utils.iterables.inTwoBlocks
import jakarta.inject.Singleton

@Singleton
class Day15: AbstractLinesAdventDay<Long>() {
    override val day = 15

    override fun process(part: QuizPart, lines: Sequence<String>): Long {
        return lines
            .inTwoBlocks(
                delimiterPredicate = { it.isEmpty() },
                block1 = { mapLines ->
                    var start: Point = Point.ZERO
                    val map = Grid.parse(
                        mapLines
                            .map { line ->
                                if (part == QuizPart.A)
                                    line
                                else
                                    buildString {
                                        line.toCharArray()
                                            .map {
                                                when(it) {
                                                    '#' -> "##"
                                                    'O' -> "[]"
                                                    '.' -> ".."
                                                    '@' -> "@."
                                                    else -> throw IllegalArgumentException("unknown char: $it")
                                                }
                                            }
                                            .onEach { append(it) }
                                    }
                            }
                    ) { pos, char ->
                        char
                            .also { if (it == '@') start = pos }
                            .takeIf { it != '.' && it != '@' }
                    }
                    map to start
                },
                block2 = { (map, start), movementLines ->
                    movementLines
                        .flatMap { it.toCharArray().asSequence() }
                        .map {
                            when(it) {
                                '<' -> Vector.WEST
                                '>' -> Vector.EAST
                                '^' -> Vector.NORTH
                                'v' -> Vector.SOUTH
                                else -> throw IllegalArgumentException("unknown direction: $it")
                            }
                        }
                        .fold(map to start) { (map, robot), dir ->
                            move(map, robot, dir)
                        }
                        .let { (map, robot) ->
                            map[robot] = '@'
                            map
                        }
                        .also {

                            map.print { it ?: '.' }
                        }
                        .getAllData()
                        .sumOf {
                            if (it.data == 'O' || it.data == '[') it.point.y * 100L + it.point.x
                            else 0L
                        }
                }
            )
    }

    private fun move(map: Grid<Char>, robot: Point, dir: Vector): Pair<Grid<Char>, Point> {
        val toMove = getObjectsToMove(map, robot, dir)
        return if (toMove.isNotEmpty()) {
            toMove
                .drop(1)
                .reversed()
                .forEach {
                    map[it+dir] = map[it]
                    map[it] = null
                }
            map to robot + dir
        } else map to robot
    }

    private fun getObjectsToMove(map: Grid<Char>, start: Point, vector: Vector): List<Point> {
        val result = mutableListOf(start)
        var front = listOf(start)
        while (true) {
            front = front
                .map { it + vector }
            if (vector.isVertical) {
                front = front + front
                    .mapNotNull { if (map[it] == '[') it + Vector.EAST else if (map[it] == ']') it + Vector.WEST else null }
                    .filter { it !in front }
            }
            front = front.sortedWith(compareBy({ it.y }, { it.x }))
            if (front.any { map[it] == '#' }) return emptyList()
            else {
                front = front.filter { it in map }
                if (front.isEmpty()) return result
                result.addAll(front)
            }
        }
    }

    private fun <T: Any> Grid<T>.print(lambda: (T?) -> Char) {
        for (y in (0 until this.height)) {
            for (x in (0 until this.width)) {
                print(lambda(this[Point(x, y)]))
            }
            println()
        }
    }
}
