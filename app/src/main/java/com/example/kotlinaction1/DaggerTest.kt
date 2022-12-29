package com.example.kotlinaction1

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

/**
 * <code>DaggerTest</code>
 *
 * @version $
 * @author lsc
 * @since 2022/12/28
 */

class Heater

class Pump(val heater: Heater)

@Module
@InstallIn(ActivityComponent::class)
class CoffeeMakerModule{
    @Provides
    @ActivityScoped
    fun provideHeater(): Heater = Heater()

    @Provides
    @ActivityScoped
    fun providePump(heater: Heater): Pump = Pump(heater)
}

class CoffeeMaker @Inject constructor(
    private val heater: Heater,
    private val pump: Pump
)

