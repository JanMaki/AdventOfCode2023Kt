fun main() {
    /**
     * 行のデータから[GameData]を作成する
     *
     * @param inputLine 行のデータ
     * @return [GameData]
     */
    fun loadGameData(inputLine: String): GameData {
        //ゲームのデータ
        val gameData = GameData()
        //ゲームの番号と要素で分ける
        inputLine.split(": ").withIndex().forEach { segment ->
            //0回目のときはゲームのIDとして記録
            if (segment.index == 0) {
                gameData.gameNumber = segment.value.split(" ")[1].toInt()
            } else {
                //ゲームセットごとに分ける
                segment.value.split("; ").forEach { gameSet ->
                    //ゲームセットのデータ
                    val gameSetData = mutableMapOf<Color, Int>()
                    //ゲームセット内で取り出した玉の情報で分ける
                    gameSet.split(", ").forEach { pickup ->
                        //取り出した玉のデータを記録
                        val splittedPickup = pickup.split(" ")
                        val color = Color.entries.first { it.text == splittedPickup[1] }
                        gameSetData[color] = splittedPickup[0].toInt()
                    }
                    //ゲームセットのデータをゲームのデータに記録
                    gameData.gameset.add(gameSetData)
                }
            }
        }

        return gameData
    }

    fun part1(input: List<String>, filterInput: Map<Color, Int>): Int {
        return input.map {
            //各行を読み込み
            loadGameData(it)
        }.filter { gameData ->
            //条件に満たしているかを確認
            var result = true
            filterInput.forEach { filter ->
                gameData.gameset.forEach { colorCount ->
                    //既定値よりも大きい場合は不合格
                    if (filter.value < colorCount.getOrDefault(filter.key, 0)) {
                        result = false
                    }
                }
            }
            result
        }.sumOf {
            //ゲームの番号で集計
            it.gameNumber
        }
    }

    fun part2(input: List<String>): Int {
        return input.map {
            //各行を読み込み
            loadGameData(it)
        }.sumOf { gameData ->
            //各色の最大値を取得
            val redMax = gameData.gameset.maxOf { it.getOrDefault(Color.Red, 0) }
            val greenMax = gameData.gameset.maxOf { it.getOrDefault(Color.Green, 0) }
            val blueMax = gameData.gameset.maxOf { it.getOrDefault(Color.Blue, 0) }

            //乗算する
            redMax * greenMax * blueMax
        }
    }

    val input = readInput("Day02")

    /*
    //コンソールからの入力を受付
    val consoleInput = readln()
    //スペース区切りで順に赤 緑 青 とする
    val filterInput = consoleInput.split(" ").withIndex().map {
        Pair(Color.entries[it.index], it.value.toInt())
    }.toMap()
    part1(input, filterInput).println()
     */
    part2(input).println()
}


/**
 * ゲームのデータ
 *
 * @property gameNumber ゲームの番号
 * @property gameset ゲーム内のセットごとのデータ
 */
data class GameData(var gameNumber: Int = -1, var gameset: MutableList<MutableMap<Color, Int>> = mutableListOf())

/**
 * 玉の色のデータ
 *
 * @property text テキスト形式
 */
enum class Color(val text: String) {
    Red("red"),
    Green("green"),
    Blue("blue")
}