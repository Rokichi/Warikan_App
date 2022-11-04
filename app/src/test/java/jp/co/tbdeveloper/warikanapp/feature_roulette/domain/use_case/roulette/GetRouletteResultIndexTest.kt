package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.roulette

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Warikan
import org.junit.Assert.*

import org.junit.Test

internal class GetRouletteResultIndexTest {
    val getRouletteResultIndex = GetRouletteResultIndex()

    /**
     * 割り勘の比率に近い値に収束しているか
     *
     */
    @Test
    fun proportionAssert() {
        val warikans = listOf(
            Warikan(ratios = listOf(), 5, 0),
            Warikan(ratios = listOf(), 1, 0),
            Warikan(ratios = listOf(), 3, 0),
            Warikan(ratios = listOf(), 1, 0),
            Warikan(ratios = listOf(), 2, 0),
            Warikan(ratios = listOf(), 1, 0),
            Warikan(ratios = listOf(), 1, 0),
        )
        val answers = MutableList(warikans.size) { 0 }
        for(i in 0..10000){
            val index = getRouletteResultIndex(warikans, i.toString())
            answers[index]++
        }
        val p = answers[0].toDouble() / warikans[0].proportion
        for(answer in answers) println("${answer.toDouble()/p}")
    }
}