package jp.co.tbdeveloper.warikanapp.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.tbdeveloper.warikanapp.feature_roulette.data.data_source.RouletteDatabase
import jp.co.tbdeveloper.warikanapp.feature_roulette.data.repository.RouletteRepositoryImpl
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.RouletteRepository
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.roulette.AddRoulette
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.roulette.DeleteRoulette
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.roulette.GetRoulettes
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.roulette.RouletteUseCases
import javax.inject.Singleton

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
    fun provideRouletteRepository(db: RouletteDatabase):RouletteRepository{
        return RouletteRepositoryImpl(db.rouletteDao)
    }

    @Provides
    @Singleton
    fun provideRouletteUseCases(repository: RouletteRepository): RouletteUseCases {
        return RouletteUseCases(
            getRoulettes = GetRoulettes(repository),
            deleteRoulette = DeleteRoulette(repository),
            addRoulette = AddRoulette(repository)
        )
    }
}