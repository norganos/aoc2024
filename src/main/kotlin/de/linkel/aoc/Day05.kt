package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.PuzzleRun
import jakarta.inject.Singleton

@Singleton
class Day05: AbstractLinesAdventDay<Long>() {
    override val day = 5

    class StoppingIterator<T>(
        private val parent: Iterator<T>,
        private val end: T
    ): Iterator<T> {
        private var buffer: T? = null
        private var consumed: Boolean = true

        init {
            if (parent.hasNext()) {
                buffer = parent.next()
                if (buffer != end) {
                    consumed = false
                }
            }
        }

        override fun hasNext(): Boolean {
            return !consumed && buffer != end
        }

        override fun next(): T {
            if (hasNext()) {
                val result = buffer!!
                if (parent.hasNext()) {
                    buffer = parent.next()
                } else {
                    consumed = true
                }
                return result
            } else throw NoSuchElementException()
        }
    }
    class IteratorSequence<T>(
        private val iterator: Iterator<T>
    ): Sequence<T> {
        private var consumed = false
        override fun iterator(): Iterator<T> {
            if (consumed) throw NoSuchElementException()
            consumed = true
            return iterator
        }
    }

    fun <A,B,T> Sequence<T>.twoBlocks(delimiter: T, block1: (Sequence<T>) -> A, block2: (buffer: A, Sequence<T>) -> B): B {
        val iterator = this.iterator()

        val first = block1(IteratorSequence(StoppingIterator(iterator, delimiter)))
        return block2(first, IteratorSequence(iterator))
    }

    class PageComparator(
        rules: List<Pair<Long, Long>>
    ): Comparator<Long> {
        private val before = rules.groupBy { (from, _) -> from }.mapValues { (_, v) -> v.map { it.second } }
        private val after = rules.groupBy { (_, to) -> to }.mapValues { (_, v) -> v.map { it.first } }

        override fun compare(o1: Long?, o2: Long?): Int {
            return if (before[o1]?.contains(o2) == true || after[o2]?.contains(o1) == true)
                -1
            else if (before[o2]?.contains(o1) == true || after[o1]?.contains(o2) == true)
                1
            else
                0
        }
    }

    override fun process(puzzle: PuzzleRun, lines: Sequence<String>): Long {
        return lines.twoBlocks("", { rLines ->
            rLines.map { line ->
                line.substringBefore("|").toLong() to line.substringAfterLast("|").toLong()
            }.toList()
        }, { rules, pLines ->
            val comparator = PageComparator(rules)
            if (puzzle.isA()) {
                pLines
                    .map { line -> line.split(",").map(String::toLong) }
                    .filter { pages ->
                        pages
                            .zipWithNext()
                            .all { (a, b) -> comparator.compare(a, b) <= 0 }
                    }
                    .sumOf { it[it.size / 2] }
            } else {
                pLines
                    .map { line -> line.split(",").map(String::toLong) }
                    .mapNotNull { pages ->
                        pages
                            .sortedWith(comparator)
                            .takeIf { it != pages }
                    }
                    .sumOf { it[it.size / 2] }
            }
        })
    }
}
