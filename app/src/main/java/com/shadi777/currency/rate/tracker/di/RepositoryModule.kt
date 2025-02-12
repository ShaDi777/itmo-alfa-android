package com.shadi777.currency.rate.tracker.di

import com.shadi777.currency.rate.tracker.data.datasource.CurrencyRateDataSource
import com.shadi777.currency.rate.tracker.data.repository.CurrencyRateRepositoryImpl
import com.shadi777.currency.rate.tracker.domain.repository.CurrencyRateRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providesCurrencyRateRepository(currencyRateDataSource: CurrencyRateDataSource): CurrencyRateRepository {
        return CurrencyRateRepositoryImpl(currencyRateDataSource)
    }
}
