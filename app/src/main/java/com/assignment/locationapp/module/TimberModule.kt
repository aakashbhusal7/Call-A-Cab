package com.assignment.locationapp.module

import android.util.Log
import com.assignment.locationapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import javax.inject.Singleton

/**
 * https://github.com/JakeWharton/timber
 */

/**@Module to notify that we receive dependencies from there
 * @Singleton to ensure only a single instance is created and used over the entire app
 * @Provides to notify that a function will provide a dependency that is stated
 */
@Module
@InstallIn(SingletonComponent::class)
object TimberModule {
    @Provides
    @Singleton
    fun provideTimberTree(): Timber.Tree =
        object : Timber.DebugTree() {
            //timber for debuggable use case only
            override fun isLoggable(tag: String?, priority: Int) =
                BuildConfig.DEBUG || priority >= Log.INFO
        }
}
