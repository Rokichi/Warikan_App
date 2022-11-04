package jp.co.tbdeveloper.warikanapp.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.tbdeveloper.warikanapp.feature_roulette.data.data_source.MemberDatabase
import jp.co.tbdeveloper.warikanapp.feature_roulette.data.data_source.RouletteDatabase
import jp.co.tbdeveloper.warikanapp.feature_roulette.data.data_source.WarikanDatabase
import jp.co.tbdeveloper.warikanapp.feature_roulette.data.repository.MemberRepositoryImpl
import jp.co.tbdeveloper.warikanapp.feature_roulette.data.repository.RouletteRepositoryImpl
import jp.co.tbdeveloper.warikanapp.feature_roulette.data.repository.WarikanRepositoryImpl
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.MemberRepository
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.RouletteRepository
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.WarikanRepository
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.member.*
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.roulette.*
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.warikan.*
import javax.inject.Singleton

/**
 * 各インスタンスをDI
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRouletteDatabase(app: Application): RouletteDatabase {
        return Room.databaseBuilder(
            app,
            RouletteDatabase::class.java,
            RouletteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideRouletteRepository(db: RouletteDatabase): RouletteRepository {
        return RouletteRepositoryImpl(db.rouletteDao)
    }

    @Provides
    @Singleton
    fun provideRouletteUseCases(repository: RouletteRepository): RouletteUseCases {
        return RouletteUseCases(
            getRoulettes = GetRoulettes(repository),
            deleteRoulette = DeleteRoulette(repository),
            addRoulette = AddRoulette(repository),
            getRouletteById = GetRouletteById(repository),
            rouletteValidation = RouletteValidation(),
            getRouletteResultIndex = GetRouletteResultIndex(),
            getResultDeg = GetResultDeg(),
            getWarikanResult = GetWarikanResult(),
        )
    }

    @Provides
    @Singleton
    fun provideMemberDatabase(app: Application): MemberDatabase {
        return Room.databaseBuilder(
            app,
            MemberDatabase::class.java,
            MemberDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideMemberRepository(db: MemberDatabase): MemberRepository {
        return MemberRepositoryImpl(db.memberDao)
    }

    @Provides
    @Singleton
    fun provideMemberUseCases(repository: MemberRepository): MemberUseCases {
        return MemberUseCases(
            getMembersById = GetMembersById(repository),
            deleteMember = DeleteMember(repository),
            deleteMembers = DeleteMembers(repository),
            addMember = AddMember(repository),
            getAllMembers = GetAllMembers(repository),
            memberValidation = MemberValidation()
        )
    }


    @Provides
    @Singleton
    fun provideWarikanDatabase(app: Application): WarikanDatabase {
        return Room.databaseBuilder(
            app,
            WarikanDatabase::class.java,
            WarikanDatabase.DATABASE_NAME
        ).build()
    }


    @Provides
    @Singleton
    fun provideWarikanRepository(db: WarikanDatabase): WarikanRepository {
        return WarikanRepositoryImpl(db.warikanDao)
    }


    @Provides
    @Singleton
    fun provideWarikanUseCases(repository: WarikanRepository): WarikanUseCases {
        return WarikanUseCases(
            getWarikansById = GetWarikansById(repository),
            getAllWarikans = GetAllWarikans(repository),
            deleteWarikan = DeleteWarikan(repository),
            deleteWarikans = DeleteWarikans(repository),
            addWarikan = AddWarikan(repository),
            warikanValidation = WarikanValidation()
        )
    }
}