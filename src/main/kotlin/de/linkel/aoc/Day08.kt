package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.QuizPart
import de.linkel.aoc.utils.geometry.plain.discrete.Point
import de.linkel.aoc.utils.geometry.plain.discrete.Vector
import de.linkel.aoc.utils.grid.Grid
import de.linkel.aoc.utils.grid.inside
import de.linkel.aoc.utils.iterables.permutations
import de.linkel.aoc.utils.math.CommonMath
import jakarta.inject.Singleton
import kotlin.math.sign

@Singleton
class Day08: AbstractLinesAdventDay<Long>() {
    override val day = 8

    override fun process(part: QuizPart, lines: Sequence<String>): Long {
        // Grid.parse currently auto-crops :-/
        return lines
            .filter { it.isNotEmpty() }
            .flatMapIndexed { y, line ->
                line.toCharArray()
                    .mapIndexed { x, c -> Point(x, y) to c }
            }
            .fold(Grid<Char>()) { grid, (point, char) ->
                grid.stretchTo(point)
                if (char != '.')
                    grid[point] = char
                grid
            }
            .let { grid ->
                grid.getAllData()
                    .groupBy { it.data }
                    .mapValues { (_, values) -> values.map { it.point } }
                    .flatMap { (c, antennas) ->
                        antennas
                            .permutations(2)
                            .filter { it.size == 2 }
                            .map { it.toSet() }
                            .toSet()
                            .map { it.first() to it.last() }
                            .flatMap { (a, b) ->
                                if (part == QuizPart.A)
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

private fun Vector.min(): Vector {
    return if (deltaX == 0) Vector(deltaX = 0, deltaY = deltaY.sign)
    else if (deltaY == 0) Vector(deltaX = deltaX.sign, deltaY = 0)
    else {
        val gcd = CommonMath.gcd(deltaX, deltaY)
        Vector(deltaX / gcd, deltaY / gcd)
    }
}
private fun Grid<Char>.straightLine(start: Point, direction: Vector): List<Point> {
    val result = mutableListOf<Point>()
    var pos = start
    while (pos in boundingBox) {
        result.add(pos)
        pos += direction
    }
    return result
}
