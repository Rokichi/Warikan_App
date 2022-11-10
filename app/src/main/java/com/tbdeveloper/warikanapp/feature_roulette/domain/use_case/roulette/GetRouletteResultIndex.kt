package com.tbdeveloper.warikanapp.feature_roulette.domain.use_case.roulette

import com.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Warikan
import com.tbdeveloper.warikanapp.feature_roulette.utils.getMD5HashInt


class GetRouletteResultIndex {
    /**
     * 割り勘のリストからproportionを考慮して
     * ランダムにindexを返却する
     * @param warikans 割り勘のリストデータ
     * @return 抽選されたindex
     */
    operator fun invoke(warikans: List<Warikan>, hash: String): Int {
        val sumOfProportion = warikans.sumOf { it.proportion }
        // 抽選
        // val a = getCalendarStr()
        var result_point = getMD5HashInt(hash) % sumOfProportion
        var result_index = 0

        // 順にみてresult_pointが含まれるindexを検索
        for ((index, warikan) in warikans.withIndex()) {
            result_point -= warikan.proportion
            if (result_point < 0) {
                result_index = index
                break
            }
        }
        return result_index
    }
}