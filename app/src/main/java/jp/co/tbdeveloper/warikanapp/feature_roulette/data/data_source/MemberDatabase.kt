package jp.co.tbdeveloper.warikanapp.feature_roulette.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.MemberEntity


@Database(
    entities = [MemberEntity::class],
    version = 1
)

abstract class MemberDatabase() : RoomDatabase() {
    abstract val memberDao: MemberDao

    companion object {
        const val DATABASE_NAME = "members_db"
    }
}