fun main() {
    //ファイルから読み込み
    val input = readInput("Day03")

    //フィールドのデータ
    val fieldData = FieldData(input[0].length, input.size)

    //集計用変数
    var result = 0

    //すべての行を確認
    input.withIndex().forEach {
        //各行のすべての文字を確認
        it.value.withIndex().forEach char@{ char ->
            //数値のときは除外
            if (char.value == '.' || char.value.isDigit()) return@char
            //記号のときはフィールドのデータに保管。*はギアとして扱う
            fieldData.putMark(char.index, it.index, char.value == '*')
        }
    }

    //すべての行を確認
    input.withIndex().forEach { row ->
        //行の文字列
        var rowValue = row.value

        //数値だけの配列を作成する
        val numbers = String(row.value.map {
            //数値以外を.に置き換え
            if (it.isDigit()) {
                it
            } else {
                '.'
            }
        }.toCharArray())
            //.で配列に切る
            .split(".")
            //空かnullの要素を削除
            .filter { it != "" && it.toIntOrNull() != null }

        //数値をすべて確認
        numbers.forEach { number ->
            //数値のある先頭を取得
            val index = rowValue.indexOf(number)
            //数値の桁だけ、場所を作成
            val positions = number.withIndex().map { Pair(index + it.index, row.index) }
            //フィールドに問い合わせ
            if (fieldData.checkAndPutGear(*positions.toTypedArray(), number = number.toInt())) {
                //結果用変数に追加
                result += number.toInt()
            }
            //同じ数字があると困るので適当な記号で置き換え
            rowValue = rowValue.replaceFirst(number, "□".repeat(number.length))
        }
    }

    //Part1
    result.println()
    //Part2
    fieldData.getGearResult().println()
}

/**
 * フィールドの状態を司る
 *
 * @param rowSize 列数
 * @param columnSIze 行数
 */
class FieldData(rowSize: Int, columnSIze: Int) {
    //記号のの位置
    private val marks = Array(columnSIze) { arrayOfNulls<Int?>(rowSize) }

    //ギアの位置
    private val gears = Array(columnSIze) { arrayOfNulls<MutableList<Int>?>(rowSize) }

    /**
     * 記号を設置する
     *
     * @param x x座標
     * @param y y座標
     * @param isGear ギアかどうか
     */
    fun putMark(x: Int, y: Int, isGear: Boolean = false) {
        //記号の位置を保管
        marks[y][x] = 1
        if (isGear) {
            //ギアの対象の変数を初期化
            gears[y][x] = mutableListOf()
        }
    }

    /**
     * 確認する
     * ギアとの隣接のときはそれも記録する
     *
     * @param pair チェックする配置
     * @param number 対象の番号
     * @return 記号が隣接しているときは true
     */
    fun checkAndPutGear(vararg pair: Pair<Int, Int>, number: Int): Boolean {
        pair.forEach {
            for (y in it.second - 1..it.second + 1) {
                //範囲確認
                if (y < 0 || y >= marks.size) continue
                for (x in it.first - 1..it.first + 1) {
                    //範囲確認
                    if (x < 0 || x >= marks[y].size) continue

                    val mark = marks.getOrNull(y)?.getOrNull(x)
                    if (mark != null) {
                        gears.getOrNull(y)?.getOrNull(x)?.add(number)
                        return true
                    }
                }
            }
        }

        return false
    }

    /**
     * ギアの結果を取得
     *
     * @return 結果を取得
     */
    fun getGearResult(): Int{
        return gears.sumOf {
            it.filterNotNull().filter { it.size > 1 }.sumOf { it[0] * it[1] }
        }
    }
}