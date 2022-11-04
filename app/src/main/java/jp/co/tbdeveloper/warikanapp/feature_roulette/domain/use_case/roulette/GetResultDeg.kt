package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.roulette

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Warikan

class GetResultDeg {
    /**
     * 抽選されたインデックスに対して，ルーレットを止める角度を求める
     *
     * @param warikans 割り勘リスト
     * @param drawnIndex　抽選インデックス
     * @return 抽選インデックスに対応した角度
     */
    operator fun invoke(warikans: List<Warikan>, drawnIndex: Int): Float {
        val sumOfProportion = warikans.sumOf { it.proportion }
        val deg: Float = 360f / sumOfProportion
        // ルーレット開始角度
        var resultDeg = warikans.slice(0 until drawnIndex).sumOf { it.proportion } * deg
        // 答えの中で角度を調整
        resultDeg += Math.random().toFloat() * warikans[drawnIndex].proportion * deg
        return (360f - resultDeg)
    }
}