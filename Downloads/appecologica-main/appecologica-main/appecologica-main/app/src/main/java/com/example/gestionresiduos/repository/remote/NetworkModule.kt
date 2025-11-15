package com.example.gestionresiduos.repository.remote

import com.example.gestionresiduos.BuildConfig
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {

    // Interceptor de log (útil en debug para inspeccionar requests/responses)
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // verificar en backen el token con JWT, aquí añadir un interceptor Authorization.
    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)                  // Quita esto en release si no quieres logs
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()

    private val moshi = Moshi.Builder().build()

    // Usa BASE_URL definida en build.gradle.kts (termina con '/')
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)           // <- ingresar aqui url (desde Gradle)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    // Instancia única de ApiService para toda la app
    val api: ApiService by lazy { retrofit.create(ApiService::class.java) }
}