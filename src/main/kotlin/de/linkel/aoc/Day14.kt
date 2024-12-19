package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.PuzzleRun
import de.linkel.aoc.utils.geometry.plain.discrete.Vector
import de.linkel.aoc.utils.geometry.plain.discrete.Point
import de.linkel.aoc.utils.geometry.plain.discrete.Rectangle
import jakarta.inject.Singleton

@Singleton
class Day14: AbstractLinesAdventDay<Long>() {
    override val day = 14

    private val regex = Regex("""^p=(-?\d+),\s?(-?\d+)\s?v=(-?\d+),\s?(-?\d+)$""")
    override fun process(puzzle: PuzzleRun, lines: Sequence<String>): Long {
        return lines
            .map { line ->
                regex.matchEntire(line)!!
                    .groupValues
                    .let {
                        Point(it[1].toInt(), it[2].toInt()) to Vector(it[3].toInt(), it[4].toInt())
                    }
            }
            .toList()
            .let { robots ->
                val area = if (puzzle.example)
                    Rectangle(0, 0, 11, 7)
                else
                    Rectangle(0, 0, 101, 103)
                val destAreas = listOf(
                    Rectangle(
                        area.x, area.y,
                        area.width / 2, area.height / 2
                    ),
                    Rectangle(
                        area.x + (area.width + 1) / 2, area.y,
                        area.width / 2, area.height / 2
                    ),
                    Rectangle(
                        area.x, area.y + (area.height + 1) / 2,
                        area.width / 2, area.height / 2
                    ),
                    Rectangle(
                        area.x + (area.width + 1) / 2, area.y + (area.height + 1) / 2,
                        area.width / 2, area.height / 2
                    ),
                )
                val evolve: (Pair<Point, Vector>) -> Pair<Point, Vector> = { (p, v) ->
                    (p + v) % area to v
                }

                if (puzzle.isA()) {
                    robots
                        .transform(100) { list, _ ->
                            list.map(evolve)
                        }
                        .safetyFactor(destAreas)
                } else {
                    data class State(
                        val robots: List<Pair<Point, Vector>>,
                        val lowestSafetyFactor: Pair<Int, Long> = 0 to robots.safetyFactor(destAreas)
                    )
                    (0 until area.area)
                        .fold(State(robots)) { state, i ->
                            val next = state.robots.map(evolve)
                            val sf = next.safetyFactor(destAreas)
                            State(
                                robots = next,
                                lowestSafetyFactor = if (sf < state.lowestSafetyFactor.second) (i+1) to sf else state.lowestSafetyFactor
                            )
                        }
                        .lowestSafetyFactor.first.toLong()
                }
            }
    }

    private fun List<Pair<Point, Vector>>.safetyFactor(destAreas: List<Rectangle>): Long {
        return this
            .mapNotNull { (p, _) ->
                destAreas.firstOrNull { p in it }
            }
            .groupingBy { it }
            .eachCount()
            .entries
            .fold(1L) { p, e -> p * e.value }
    }

    private fun <T> T.transform(times: Int, lambda: (T, Int) -> T): T {
        return (0 until times)
            .fold(this) { acc, i ->
            lambda(acc, i)
        }
    }

    private operator fun Point.rem(rect: Rectangle): Point {
        return if (this in rect) this else
            Point(
                x = (((this.x - rect.x) % rect.width) + rect.width) % rect.width + rect.x,
                y = (((this.y - rect.y) % rect.height) + rect.height) % rect.height  + rect.y
            )
    }
}
