package de.linkel.aoc

class Day18Test: AbstractDayTest<String>() {
    override val exampleA = """
5,4
4,2
4,5
3,0
2,1
6,3
2,4
1,5
0,6
3,3
2,6
5,1
1,2
5,5
2,5
6,5
1,4
0,4
6,4
1,1
6,1
1,0
0,5
1,6
2,0
        """.trimIndent()
    override val exampleSolutionA = "22"
    override val solutionA = "270"


//    override val exampleB = """
//        """.trimIndent()
    override val exampleSolutionB = "6,1"
    override val solutionB = "51,40"

    override val implementation = Day18()
}
