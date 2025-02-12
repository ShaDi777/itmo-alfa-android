package com.shadi777.currency.rate.tracker.di

import com.shadi777.currency.rate.tracker.data.datasource.CurrencyRateDataSource
import com.shadi777.currency.rate.tracker.data.datasource.CurrencyRateDataSourceImpl
import com.shadi777.currency.rate.tracker.data.datasource.remote.MockCurrencyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Singleton
    @Provides
    fun provideCurrencyRateDataSource(currencyApi: MockCurrencyApi): CurrencyRateDataSource {
        return CurrencyRateDataSourceImpl(currencyApi)
    }
}
