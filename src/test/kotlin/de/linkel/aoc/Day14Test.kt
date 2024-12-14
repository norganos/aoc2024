package de.linkel.aoc

class Day14Test: AbstractDayTest<Long>() {
    override val exampleA = """
p=0,4 v=3,-3
p=6,3 v=-1,-3
p=10,3 v=-1,2
p=2,0 v=2,-1
p=0,0 v=1,3
p=3,0 v=-2,-2
p=7,6 v=-1,-3
p=3,0 v=-1,-2
p=9,3 v=2,3
p=7,3 v=-1,2
p=2,4 v=2,-3
p=9,5 v=-3,-3
        """.trimIndent()
    override val exampleSolutionA = 12L
    override val solutionA = 229980828L

//    override val exampleB = """
//        """.trimIndent() 79952 90355 7131
    override val exampleSolutionB = 7132L
    override val solutionB = 7132L

    override val implementation = Day14()
}
