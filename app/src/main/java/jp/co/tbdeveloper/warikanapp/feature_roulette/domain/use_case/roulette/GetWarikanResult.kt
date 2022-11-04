package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.roulette

class GetWarikanResult {
    /**
     * 割り勘の比率に応じてお金を分配
     * 0対応，最後に余ったお金は最大の人に払わす
     *
     * @param total 合計金額
     * @param ratios 割合のリスト
     * @return 支払額のリスト
     */
    operator fun invoke(total: Int, ratios: List<Int>): List<Int> {
        val totalPerRatio: Int = total / ratios.sum()
        val payments = IntArray(ratios.size) { 0 }
        var nowTotal = 0
        val ascendingIndex = ratios.withIndex()
            .sortedBy { it.value }
            .map { it.index }
        for (index in ascendingIndex) {
            payments[index] = totalPerRatio * ratios[index]
            nowTotal += payments[index]
        }
        payments[ascendingIndex.last()] += total - nowTotal
        return payments.toList()
    }
}