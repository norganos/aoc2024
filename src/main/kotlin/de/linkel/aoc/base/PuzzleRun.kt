package de.linkel.aoc.base

data class PuzzleRun(
    val part: QuizPart,
    val example: Boolean
) {
    fun isA() = part == QuizPart.A
    fun isB() = part == QuizPart.B
}
