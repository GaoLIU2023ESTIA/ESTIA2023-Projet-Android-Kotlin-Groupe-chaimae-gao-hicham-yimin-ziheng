package com.task.actionlist.data.db

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun providesDiaryDao(
        database: ActionDatabase,
    ): ActionDao = database.actionDao()

}