fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            //左の数値
            val firstDigit: Int
            //0から始める
            var index = 0
            while (true) {
                //1文字抽出
                val value = it.substring(index, index + 1).toIntOrNull()
                if (value != null) {
                    firstDigit = value
                    break
                }

                //indexを進める
                index++
            }

            //右の数値
            val lastDigit: Int
            //後ろから始める
            var indexFromLast = it.length - 1
            while (true) {
                //1文字抽出
                val value = it.substring(indexFromLast, indexFromLast + 1).toIntOrNull()
                if (value != null) {
                    lastDigit = value
                    break
                }

                //indexを後退
                indexFromLast--
            }

            //2桁の数字にする
            firstDigit * 10 + lastDigit
        }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput("Day01")
    part1(input).println()
}
