package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.QuizPart
import de.linkel.aoc.utils.iterables.product
import jakarta.inject.Singleton
import org.jgrapht.alg.clustering.GirvanNewmanClustering
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph

@Singleton
class Day25: AbstractLinesAdventDay<Long>() {
    override val day = 25

    override fun process(part: QuizPart, lines: Sequence<String>): Long {
        return lines.count().toLong()
    }
}
