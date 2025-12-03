package com.example.gestionresiduos.repository.remote

import com.example.gestionresiduos.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Objeto Singleton para configurar y proveer una instancia de Retrofit y ApiService.
 * Es la única puerta de entrada para todas las llamadas de red de la aplicación.
 */
object ApiClient {

    // 1. URL base del servidor. Para desarrollo local (emulador Android), usa 10.0.2.2.
    // Cambia a tu IP LAN si pruebas en dispositivo físico (ej. http://192.168.x.x:8080/).
    private const val BASE_URL = "http://10.0.2.2:8080/"

    // 2. Interceptor para ver los logs de las peticiones en modo DEBUG.
    //    Esto es increíblemente útil para depurar y ver qué está pasando.
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    // 3. Cliente OkHttp configurado solo con el interceptor de logs.
    //    Se han eliminado las referencias a 'authInterceptor'.
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    // 4. Instancia de Retrofit.
    //    CORRECCIÓN CLAVE: Ahora usa la constante BASE_URL en lugar de BuildConfig.BASE_URL.
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        // Gson respeta @SerializedName, que ya usamos en los DTOs.
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    /**
     * Instancia única y perezosa (lazy) del servicio de la API.
     * Se crea solo la primera vez que se necesita.
     */
    val api: ResiduosApi by lazy {
        retrofit.create(ResiduosApi::class.java)
    }
}
