package com.task.actionlist.data.network

import android.util.Log
import com.task.actionlist.data.model.ActionList
import com.task.actionlist.data.model.PostBody
import com.task.actionlist.data.model.PostStatus
import kotlinx.coroutines.flow.flow
import okhttp3.CipherSuite.Companion.TLS_DHE_RSA_WITH_AES_128_CBC_SHA
import okhttp3.CipherSuite.Companion.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
import okhttp3.CipherSuite.Companion.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256
import okhttp3.CipherSuite.Companion.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256
import okhttp3.CipherSuite.Companion.TLS_RSA_WITH_3DES_EDE_CBC_SHA
import okhttp3.CipherSuite.Companion.TLS_RSA_WITH_AES_128_CBC_SHA
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


private interface RetrofitNetworkApi {

    //    @GET(value = "musicInfo")
//    suspend fun getMusicInfo(@Query("mood") mood: String): SongInfo
    @GET(value = "todos")
    suspend fun getActionList(): ActionList

    @POST(value = "todos")
    suspend fun postAction(@Body postBody: PostBody): PostStatus

    @PUT(value = "todos/{id}/status/{status}")
    suspend fun putAction(@Path("id") id: String, @Path("status") status: Int): PostStatus

    //    @HTTP(method = "DELETE", path = "todos/{id}", hasBody = false)
    @DELETE(value = "todos/{id}")
    suspend fun deleteAction(@Path("id") id: String): PostStatus
}

//Adresse de la passerelle dans l'émulateur Wifi
private const val BaseUrl = "https://mbds.glitch.me/"

//@Serializable
//private data class NetworkResponse<T>(
//    val data: T
//)

@Singleton
class RetrofitNetwork  {
//    private var spec: ConnectionSpec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
//        .tlsVersions(TlsVersion.TLS_1_0)
//        .cipherSuites(
//            TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
//            TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
//            TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
//            TLS_DHE_RSA_WITH_AES_128_CBC_SHA,
//            TLS_RSA_WITH_AES_128_CBC_SHA,
//            TLS_RSA_WITH_3DES_EDE_CBC_SHA
//        )
//        .build()
    private val networkApi = Retrofit.Builder().baseUrl(BaseUrl).client(
        OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
//            .connectionSpecs(Collections.singletonList(spec))
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }).build()
    )
        .addConverterFactory(GsonConverterFactory.create())
//        .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
        .build().create(RetrofitNetworkApi::class.java)

    suspend fun getActionList() = flow {
        //Si la demande échoue, elle sera prise en compte par le système de capture en aval.
        val list = networkApi.getActionList()
        Log.i("TAG", "getActionList: ")
        emit(list)
    }

    suspend fun postAction(postBody: PostBody) = flow {
        //Si la demande échoue, elle sera prise en compte par le système de capture en aval.
        val status = networkApi.postAction(postBody)
        emit(status)
    }

    suspend fun putAction(id: String, status: Int) = flow {
        //Si la demande échoue, elle sera prise en compte par le système de capture en aval.
        val status = networkApi.putAction(id, status)
        emit(status)
    }

    suspend fun deleteAction(id: String) = flow {
        //Si la demande échoue, elle sera prise en compte par le système de capture en aval.
        val status = networkApi.deleteAction(id)
        emit(status)
    }
}