package de.linkel.aoc

import de.linkel.aoc.base.AbstractLinesAdventDay
import de.linkel.aoc.base.QuizPart
import de.linkel.aoc.utils.iterables.inTwoBlocks
import jakarta.inject.Singleton

@Singleton
class Day17: AbstractLinesAdventDay<String>() {
    override val day = 17

    class Registers {
        var a = 0L
        var b = 0L
        var c = 0L
        private var ip = 0
        private val out = mutableListOf<Int>()
        val output: List<Int> = out

        private fun combo(operand: Int): Int = when(operand) {
            in (0..3) -> operand
            4 -> a.toInt()
            5 -> b.toInt()
            6 -> c.toInt()
            7 -> throw IllegalArgumentException("reserved")
            else -> throw IllegalArgumentException("invalid combo operand $operand")
        }
        fun run(prg: List<Int>) {
            ip = 0
            out.clear()
            while (ip in prg.indices) {
                val opcode = prg[ip]
                val operand = prg[ip + 1]
                ip += 2
                runCommand(opcode, operand)
            }
        }
        private fun runCommand(opcode: Int, op: Int) {
            when (opcode) {
                0 -> a = a shr combo(op)
                1 -> b = b xor op.toLong()
                2 -> b = (combo(op) and 7).toLong()
                3 -> if (a != 0L) ip = op
                4 -> b = b xor c
                5 -> out.add(combo(op) and 7)
                6 -> b = a shr combo(op)
                7 -> c = a shr combo(op)
                else -> throw IllegalArgumentException("invalid opcode $opcode")
            }
        }
    }

    override fun process(part: QuizPart, lines: Sequence<String>): String {
        return lines.inTwoBlocks(
            delimiterPredicate = { it.isEmpty() },
            block1 = { block ->
                val reg = Registers()
                block.forEach { line ->
                    when(line.substringBefore(":")) {
                        "Register A" -> reg.a = line.substringAfter(":").trim().toLong()
                        "Register B" -> reg.b = line.substringAfter(":").trim().toLong()
                        "Register C" -> reg.c = line.substringAfter(":").trim().toLong()
                        else -> throw IllegalArgumentException("invalid reg line $line")
                    }
                }
                reg
            },
            block2 = { reg, block ->
                val prg =
                    block
                        .first()
                        .substringAfter("Program: ")
                        .split(",")
                        .map { it.toInt() }
                        .toList()
                if (part == QuizPart.A) {
                    reg.run(prg)
                    reg.output.joinToString(",")
                } else {
                    search(reg, prg, 0, 0L).toString()
                }
            }
        )
    }

    private fun search(reg: Registers, prg: List<Int>, idx: Int, baseA: Long): Long? {
        if (idx == prg.size) return baseA
        val expected = prg.subList(prg.size - idx - 1, prg.size)
        return (0 until 8)
            .map { (baseA shl 3) + it }
            .filter {
                reg.a = it
                reg.run(prg)
                reg.output == expected
            }
            .firstNotNullOfOrNull {
                search(reg, prg, idx + 1, it)
            }
    }
}
