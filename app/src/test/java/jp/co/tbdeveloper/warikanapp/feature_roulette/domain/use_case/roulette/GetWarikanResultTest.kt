package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.roulette

import org.junit.Assert.assertEquals
import org.junit.Test


class GetWarikanResultTest {
    private val getWarikanResult = GetWarikanResult()
    private val normalTotals = listOf(
        100, 500, 1000, 2000, 5000, 10000,
    )
    private val edgeTotals = listOf(
        1, 13, 557, 617, 2027, 2917, 5737, 9013, 9973
    )
    private val normalRatios = listOf(
        listOf(1, 1),
        listOf(1, 2),
        listOf(2, 1),
        listOf(1, 1, 1),
        listOf(1, 3, 1),
        listOf(3, 2, 1),
    )
    private val edgeRatios = listOf(
        listOf(0, 1),
        listOf(1, 0),
        listOf(100, 100),
        listOf(0, 0, 1),
        listOf(1, 0, 1),
        listOf(1, 0, 0),
        listOf(100, 100, 100),
    )

    /**
     * ノーマルケースの割り勘テスト
     *
     */
    @Test
    fun normalAssert() {
        for (total in normalTotals) {
            for (ratio in normalRatios) {
                val result = getWarikanResult(total, ratio, false).sum()
                val resultIsApproximate = getWarikanResult(total, ratio, true).sum()
                assertEquals(total, result)
                assertEquals(total, resultIsApproximate)
            }
        }
    }

    /**
     * 支払い金額エッジケースのテスト
     * 0より大きいことは保証
     *
     */
    @Test
    fun edgeTotalAssert() {
        for (total in edgeTotals) {
            for (ratio in normalRatios) {
                val result = getWarikanResult(total, ratio,false).sum()
                val resultIsApproximate = getWarikanResult(total, ratio, true).sum()
                assertEquals(total, result)
                assertEquals(total, resultIsApproximate)
            }
        }
    }

    /**
     * 割合エッジケースのテスト
     * すべて0は存在しない
     *
     */
    @Test
    fun edgeRationsAssert() {
        for (total in normalTotals) {
            for (ratio in edgeRatios) {
                val result = getWarikanResult(total, ratio, false).sum()
                val resultIsApproximate = getWarikanResult(total, ratio, true).sum()
                assertEquals(total, result)
                assertEquals(total, resultIsApproximate)
            }
        }
    }

    /**
     * 支払い金額エッジケース
     * 割合エッジケースの混合テスト
     *
     */
    @Test
    fun edgeTotalsAndRationsAssert() {
        for (total in edgeTotals) {
            for (ratio in edgeRatios) {
                val result = getWarikanResult(total, ratio,false).sum()
                val resultIsApproximate = getWarikanResult(total, ratio, true).sum()
                assertEquals(total, result)
                assertEquals(total, resultIsApproximate)
            }
        }
    }
}

