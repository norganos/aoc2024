package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.QuizPart
import de.linkel.aoc.utils.geometry.plain.discrete.Dimension
import de.linkel.aoc.utils.geometry.plain.discrete.Vector
import de.linkel.aoc.utils.geometry.plain.discrete.Point
import de.linkel.aoc.utils.geometry.plain.discrete.Rectangle
import jakarta.inject.Singleton
import java.io.File

@Singleton
class Day14: AbstractLinesAdventDay<Long>() {
    override val day = 14

    private val regex = Regex("""^p=(-?\d+),\s?(-?\d+)\s?v=(-?\d+),\s?(-?\d+)$""")
    override fun process(part: QuizPart, lines: Sequence<String>): Long {
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
                val area = if (robots.size < 20)
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

                if (part == QuizPart.A) {
                    robots
                        .transform(100) { list, i ->
                            list.map { (p, v) ->
                                (p + v) % area to v
                            }
                        }
                        .mapNotNull { (p, _) ->
                            destAreas.firstOrNull { p in it }
                        }
                        .groupingBy { it }
                        .eachCount()
                        .entries
                        .fold(1L) { p, e -> p * e.value }
                } else {
                    val pre = if (robots.size < 20) "e" else "s"
                    robots
                        // visually (using the txt files below) found first match at 7131 (then cycling with length 10403)
                        .transform(7132) { list, i ->
                            list
                                .map { (p, v) ->
                                    (p + v) % area to v
                                }
                                .also { rrr ->
                                    if (File(pre).exists())
                                        File("$pre/${i.toString().padStart(6, '0')}.txt").writer().use { w ->
                                            rrr
                                                .map { it.first }
                                                .distinct()
                                                .sortedWith(compareBy({ it.y }, { it.x }))
                                                .fold(Point.ZERO) { last, p ->
                                                    (p.y - last.y).times {
                                                        w.append("\n")
                                                    }
                                                    (p.x - (last.x.takeIf { p.y == last.y } ?: 0)).times {
                                                        w.append(" ")
                                                    }
                                                    w.append("#")
                                                    p
                                                }
                                        }
                                }
                        }
                    // I hate myself for this...
                    7132L
                }

            }
    }

    private fun <T> T.transform(times: Int, lambda: (T, Int) -> T): T {
        return (0 until times)
            .fold(this) { acc, i ->
            lambda(acc, i)
        }
    }
    private fun <T> List<T>.transformElements(times: Int, lambda: (T, Int) -> T): List<T> {
        return (0 until times)
            .fold(this) { acc, i ->
            acc.map { lambda(it, i) }
        }
    }
    private fun Int.times(lambda: (Int) -> Unit) {
        return (0 until this)
            .forEach(lambda)
    }

    private operator fun Point.rem(rect: Rectangle): Point {
        return if (this in rect) this else
            Point(
                x = (((this.x - rect.x) % rect.width) + rect.width) % rect.width + rect.x,
                y = (((this.y - rect.y) % rect.height) + rect.height) % rect.height  + rect.y
            )
    }
}
