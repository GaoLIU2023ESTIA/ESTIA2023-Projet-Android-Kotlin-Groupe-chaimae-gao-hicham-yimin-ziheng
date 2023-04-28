package com.task.actionlist.data.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    /**
     *tell dependency injection how to implement this class
     **/
    @Provides
    @Singleton
    fun providesDiaryDatabase(
        @ApplicationContext context: Context,
    ): ActionDatabase = Room.databaseBuilder(
        context,
        ActionDatabase::class.java,
        "action-db"
    ).build()
}