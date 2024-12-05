package de.linkel.aoc

class Day05Test: AbstractDayTest<Long>() {
    override val exampleA = """
47|53
97|13
97|61
97|47
75|29
61|13
75|53
29|13
97|29
53|29
61|53
97|53
61|29
47|13
75|47
97|75
47|61
75|61
47|29
75|13
53|13

75,47,61,53,29
97,61,53,29,13
75,29,13
75,97,47,61,53
61,13,29
97,13,75,29,47
        """.trimIndent()
    override val exampleSolutionA = 143L
    override val solutionA = 4959L


//    override val exampleB = """
//        """.trimIndent()
    override val exampleSolutionB = 123L
    override val solutionB = 4655L

    override val implementation = Day05()
}
