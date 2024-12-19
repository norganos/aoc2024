package de.linkel.aoc.base

import java.io.BufferedReader

abstract class AbstractLinesAdventDay<T>: AbstractFileAdventDay<T>() {
    override fun process(puzzle: PuzzleRun, reader: BufferedReader): T {
        return reader.useLines { sequence ->
            process(puzzle, sequence)
        }
    }

    protected abstract fun process(puzzle: PuzzleRun, lines: Sequence<String>): T
}
