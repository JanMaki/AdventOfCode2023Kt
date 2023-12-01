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
        return input.sumOf { element ->
            //文字列の検索の情報 Key:数値 Value:<First: 左から何番目にあるか, Second: 右から何番目にあるか>
            val digitsIndexData: MutableMap<Digit, Pair<Int, Int>> = mutableMapOf()

            //すべての数値を確認する
            Digit.entries.forEach Digit@{ digit ->
                //左から何番目に数字の文字列か数値があるかを確認
                val indexOf =
                    arrayOf(element.indexOf(digit.text), element.indexOf(digit.number.toString())).filter { it >= 0 }
                //見つからない場合は終わり
                if (indexOf.isEmpty()) return@Digit

                //右から何番目に数字の文字列か数値があるかを確認。絶対に見つかるので見つからなかった場合の処理は省略
                val lastIndexOf = arrayOf(
                    element.lastIndexOf(digit.text),
                    element.lastIndexOf(digit.number.toString())
                ).filter { it >= 0 }

                //検索の情報を保管
                digitsIndexData[digit] = Pair(indexOf.min(), lastIndexOf.max())
            }

            //最も左にあるものを1つめの数字とする
            val firstNumber = digitsIndexData.minBy {
                it.value.first
            }.key.number
            //最も右にあるものを2つめの数字とする
            val lastNumber = digitsIndexData.maxBy {
                it.value.second
            }.key.number

            //値を作成
            firstNumber * 10 + lastNumber
        }
    }

    val input = readInput("Day01")
    //part1(input).println()
    part2(input).println()
}

/**
 * 数値
 *
 * @property text テキスト表記
 * @property number 対応した[Int]
 */
enum class Digit(val text: String, val number: Int) {
    One("one", 1),
    Two("two", 2),
    Three("three", 3),
    Four("four", 4),
    Five("five", 5),
    Six("six", 6),
    Seven("seven", 7),
    Eight("eight", 8),
    Nine("nine", 9)
}
