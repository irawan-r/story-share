package com.amora.storyappcompose.di

import com.amora.storyappcompose.network.ApiService
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val BASE_URL = "https://story-api.dicoding.dev/v1/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

	@Provides
	@Singleton
	fun provideOkHttpClient(): OkHttpClient {
		val loggingInterceptor =
			HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
		return OkHttpClient.Builder()
			.addInterceptor(loggingInterceptor)
			.build()
	}

	@Provides
	@Singleton
	fun provideApiServices(client: OkHttpClient): ApiService {
		val moshi = Moshi.Builder()
			.add(KotlinJsonAdapterFactory())
			.build()

		return Retrofit.Builder()
			.client(client)
			.addConverterFactory(MoshiConverterFactory.create(moshi))
			.addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
			.baseUrl(BASE_URL)
			.build()
			.create(ApiService::class.java)
	}
}
