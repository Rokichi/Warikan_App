package com.tbdeveloper.warikanapp.feature_roulette.domain.use_case.roulette

import kotlin.math.pow

class GetWarikanResult {
    /**
     * 割り勘の比率に応じてお金を分配
     * 0対応，最後に余ったお金は最大の人に払わす
     * すべての割合が等しいときは概算をしない
     * @param total 合計金額
     * @param ratios 割合のリスト
     * @param isApproximate キリよく割り勘
     * @return 支払額のリスト
     */
    operator fun invoke(total: Int, ratios: List<Int>, isApproximate: Boolean): List<Int> {
        val numOfDigits = total.toString().length
        val totalPerRatio: Int = total / ratios.sum()
        val payments = IntArray(ratios.size) { 0 }
        var nowTotal = 0
        val ascendingIndex = ratios.withIndex()
            .sortedBy { it.value }
            .map { it.index }
        val isAllElementEqual = ratios.all { it == ratios[0] }
        for (index in ascendingIndex) {
            // 概算を行う
            payments[index] =
                if (numOfDigits > 2 && isApproximate && !isAllElementEqual) getApproximatedPayment(
                    totalPerRatio * ratios[index],
                    numOfDigits
                )
                else totalPerRatio * ratios[index]

            nowTotal += payments[index]
        }
        payments[ascendingIndex.last()] += total - nowTotal
        return payments.toList()
    }

    /**
     * 100円以上の数値に関して桁数-2を切り捨て
     * ex 155 -> 150, 1052 -> 1000
     * @param payment
     * @param numOfDigits
     * @return
     */
    private fun getApproximatedPayment(payment: Int, numOfDigits: Int): Int {
        if (numOfDigits < 3) return payment
        else if(numOfDigits == 3 or 4){
            return ((payment / 10.0.pow((numOfDigits - 2).toDouble())).toInt() * 10.0.pow((numOfDigits - 2))).toInt()
        }
        return ((payment / 10.0.pow((numOfDigits - 3).toDouble())).toInt() * 10.0.pow((numOfDigits - 3))).toInt()
    }
}