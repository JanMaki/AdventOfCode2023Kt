fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { element ->
            //charの配列に
            element.toCharArray()
                //charをIntかnullに
                .map { it.digitToIntOrNull() }
                //nullを排除
                .filterNotNull()
                //最初と最後の要素で値を作成
                .let { it.first() * 10 + it.last() }
        }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput("Day01")
    part1(input).println()
}
