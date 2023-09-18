package com.amora.storyappcompose.di

import android.content.Context
import com.amora.storyappcompose.data.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SessionModule {

	@Provides
	@Singleton
	fun provideSessionManager(
		@ApplicationContext context: Context
	): SessionManager = SessionManager.getInstance(context)
}