package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.service

import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.BuildConfig
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.model.catalog.CatalogResponse
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.model.category.CategoryResponse
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.model.checkout.CheckoutRequestResponse
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.model.checkout.CheckoutResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiService {
    @GET("category")
    suspend fun getCategory(): CategoryResponse

    @GET("listmenu")
    suspend fun getCatalog(
        @Query("c") category: String? = null,
    ): CatalogResponse

    @POST("order")
    suspend fun createOrder(
        @Body checkoutRequest: CheckoutRequestResponse,
    ): CheckoutResponse

    companion object {
        @JvmStatic
        operator fun invoke(): ApiService {
            val levelInterceptor = HttpLoggingInterceptor.Level.BODY
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(levelInterceptor)
            val okHttpClient =
                OkHttpClient.Builder()
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(100, TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor)
                    .build()
            val retrofit =
                Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}
