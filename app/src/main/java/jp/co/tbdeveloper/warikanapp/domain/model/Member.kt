package jp.co.tbdeveloper.warikanapp.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Member(
    val name: String,
    val color: Int,
    @PrimaryKey val id: Int? = null
){
    companion object {
        val memberColors = listOf(Color.Red, Color.Blue, Color.Green, Color.DarkGray)
    }
}
