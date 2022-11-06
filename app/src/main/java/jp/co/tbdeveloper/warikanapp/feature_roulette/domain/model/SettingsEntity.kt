package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * DB保存用Entity
 *
 * @property id プライマリキー
 * @property autoSave 自動保存
 * @property isMuted ミュート
 * @property setDarkTheme テーマ
 */
@Entity
data class SettingsEntity(
    @PrimaryKey
    var id: Int = 0,
    val autoSave: Boolean,
    val isMuted: Boolean,
    val setDarkTheme: Int
)