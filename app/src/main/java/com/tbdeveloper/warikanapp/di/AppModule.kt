package com.tbdeveloper.warikanapp.di

import android.app.Application
import androidx.room.Room
import com.tbdeveloper.warikanapp.feature_roulette.data.data_source.MemberDatabase
import com.tbdeveloper.warikanapp.feature_roulette.data.data_source.RouletteDatabase
import com.tbdeveloper.warikanapp.feature_roulette.data.data_source.SettingsDatabase
import com.tbdeveloper.warikanapp.feature_roulette.data.data_source.WarikanDatabase
import com.tbdeveloper.warikanapp.feature_roulette.data.repository.MemberRepositoryImpl
import com.tbdeveloper.warikanapp.feature_roulette.data.repository.RouletteRepositoryImpl
import com.tbdeveloper.warikanapp.feature_roulette.data.repository.SettingsRepositoryImpl
import com.tbdeveloper.warikanapp.feature_roulette.data.repository.WarikanRepositoryImpl
import com.tbdeveloper.warikanapp.feature_roulette.domain.repository.MemberRepository
import com.tbdeveloper.warikanapp.feature_roulette.domain.repository.RouletteRepository
import com.tbdeveloper.warikanapp.feature_roulette.domain.repository.SettingsRepository
import com.tbdeveloper.warikanapp.feature_roulette.domain.repository.WarikanRepository
import com.tbdeveloper.warikanapp.feature_roulette.domain.use_case.member.*
import com.tbdeveloper.warikanapp.feature_roulette.domain.use_case.roulette.*
import com.tbdeveloper.warikanapp.feature_roulette.domain.use_case.settings.GetSettings
import com.tbdeveloper.warikanapp.feature_roulette.domain.use_case.settings.SettingsUseCases
import com.tbdeveloper.warikanapp.feature_roulette.domain.use_case.settings.UpdateSettings
import com.tbdeveloper.warikanapp.feature_roulette.domain.use_case.warikan.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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

    @Provides
    @Singleton
    fun provideSettingsUseCases(repository: SettingsRepository): SettingsUseCases {
        return SettingsUseCases(
            getSettings = GetSettings(repository),
            updateSettings = UpdateSettings(repository)
        )
    }

    @Provides
    @Singleton
    fun provideSettingsDatabase(app: Application): SettingsDatabase {
        return Room.databaseBuilder(
            app,
            SettingsDatabase::class.java,
            SettingsDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideSettingRepository(db: SettingsDatabase): SettingsRepository {
        return SettingsRepositoryImpl(db.settingsDao)
    }
}