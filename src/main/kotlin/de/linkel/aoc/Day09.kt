package de.linkel.aoc

import de.linkel.aoc.base.AbstractFileAdventDay
import de.linkel.aoc.base.PuzzleRun
import jakarta.inject.Singleton
import java.io.BufferedReader
import kotlin.math.min

@Singleton
class Day09: AbstractFileAdventDay<Long>() {
    override val day = 9


    interface Blocks {
        val startIndex: Long
        val length: Int
        fun checksum(): Long
    }
    data class FreeBlock(
        override val startIndex: Long,
        override val length: Int
    ): Blocks {
        override fun checksum() = 0L
        fun fillUp(file: FileBlock): Pair<List<Blocks>, List<Blocks>> {
            val l = min(file.length, length)
            return listOfNotNull(
                FileBlock(startIndex, l, file.id),
                if (l < length) FreeBlock(startIndex + l, length - l) else null,
            ) to listOfNotNull(
                if (l < file.length) FileBlock(file.startIndex, file.length - l, file.id) else null,
                // we don't have to create FreeBlock at the end of the input file block
            )
        }

        override fun toString(): String
            = ".".repeat(length)
    }
    data class FileBlock(
        override val startIndex: Long,
        override val length: Int,
        val id: Int
    ): Blocks {
        override fun checksum(): Long {
            return (startIndex until (startIndex + length))
                .sumOf { it * id }
        }

        override fun toString(): String
                = (id % 10).toString().repeat(length)
    }

    override fun process(puzzle: PuzzleRun, reader: BufferedReader): Long {
        val disk = reader
            .charSequence()
            .foldIndexed(0L to emptyList<Blocks>()) { index, (offset, list), c ->
                if (c.isDigit()) {
                    val len = c.digitToInt()
                    val block = if (index % 2 == 0) {
                        FileBlock(offset, len, index / 2)
                    } else {
                        FreeBlock(offset, len)
                    }
                    (offset + len) to (list + block)
                } else {
                    offset to list
                }
            }.second.toMutableList()
        if (puzzle.isA()) {
            while (true) {
                while (disk.last() is FreeBlock) {
                    disk.removeLast()
                }
                val freeIndex = disk.indexOfFirst { it is FreeBlock }
                val fileIndex = disk.indexOfLast { it is FileBlock }
                if (freeIndex == -1 || fileIndex == -1 || freeIndex > fileIndex)
                    break
                val file = disk.removeAt(fileIndex) as FileBlock
                val free = disk.removeAt(freeIndex) as FreeBlock
                val (insteadFree, insteadFile) = free.fillUp(file)
                insteadFree.reversed().forEach { disk.add(freeIndex, it) }
                insteadFile.forEach { disk.add(it) }
            }
        } else {
            while (true) {
                while (disk.last() is FreeBlock) {
                    disk.removeLast()
                }
                var changed = false
                for (fileIndex in disk.indices.reversed()) {
                    if (changed) break
                    if (disk[fileIndex] is FileBlock) {
                        for (freeIndex in 0 until fileIndex) {
                            if (disk[freeIndex] is FreeBlock) {
                                if (disk[freeIndex].length >= disk[fileIndex].length) {
                                    changed = true
                                    val file = disk.removeAt(fileIndex) as FileBlock
                                    val free = disk.removeAt(freeIndex) as FreeBlock
                                    disk.add(freeIndex, FileBlock(free.startIndex, file.length, file.id))
                                    if (free.length > file.length) {
                                        disk.add(freeIndex + 1, FreeBlock(free.startIndex + file.length, free.length - file.length))
                                    }
                                    break
                                }
                            }
                        }
                    }
                }
                if (!changed)
                    break
            }
        }
        return disk.sumOf { it.checksum() }
    }

    fun BufferedReader.charSequence(): Sequence<Char> {
        val reader = this
        return sequence {
            while (true) {
                val i = reader.read()
                if (i >= 0) yield(i.toChar())
                else break
            }
        }
    }
}
