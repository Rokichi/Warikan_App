package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * DB保存用Entity
 *
 * @property id プライマリキー
 * @property RouletteId ひも付きルーレットID
 * @property ratios わりかんの割合 "x:x:..."
 * @property proportion ルーレットの割合
 * @property color 背景色
 */
@Entity
data class WarikanEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val RouletteId:Long,
    val ratios: String,
    val proportion: Int,
    val color: Int,
)

class InvalidWarikanException(message: String) : Exception(message)