package su.afk.coursecurrencypsbank.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import su.afk.coursecurrencypsbank.data.CourceRepository
import su.afk.coursecurrencypsbank.data.retrofit.ApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRepositoryCource(api: ApiService): CourceRepository {
        return CourceRepository(api)
    }

    @Singleton
    @Provides
    fun provideApiCource(): ApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://www.cbr-xml-daily.ru/")
            .build()
            .create(ApiService::class.java)
    }
}