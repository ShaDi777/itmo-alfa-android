package com.shadi777.currency.rate.tracker.di

import com.shadi777.currency.rate.tracker.domain.repository.CurrencyRateRepository
import com.shadi777.currency.rate.tracker.domain.usecase.CurrencyRateUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideCurrencyRateUseCase(currencyRateRepository: CurrencyRateRepository): CurrencyRateUseCase {
        return CurrencyRateUseCase(currencyRateRepository)
    }
}
