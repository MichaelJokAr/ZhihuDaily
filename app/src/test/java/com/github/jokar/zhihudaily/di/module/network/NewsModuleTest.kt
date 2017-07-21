package com.github.jokar.zhihudaily.di.module.network

import android.util.Log
import com.github.jokar.zhihudaily.model.entities.story.StoryDetail
import com.sunagy.mazcloud.utlis.rxjava.SchedulersUtil
import io.reactivex.Scheduler
import io.reactivex.functions.Function
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import okhttp3.CipherSuite
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


/**
 * Created by JokAr on 2017/7/20.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(23))
class NewsModuleTest {
    val DEFAULT_TIMEOUT = 15
    val BASE_URL = "http://news-at.zhihu.com/api/4/"

    lateinit var retrofit: Retrofit
    @Mock
    lateinit internal var news: NewsModule

    @Before
    fun before() {
        ShadowLog.stream = System.out
        //初始化Mock
        MockitoAnnotations.initMocks(this)
        //init Retrofit

        val client = OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .build()

        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        RxJavaPlugins.setIoSchedulerHandler(Function<Scheduler, Scheduler> {
            Schedulers.single()
        })


    }

    @Test
    fun provider() {
        val services = Mockito.verify(news).provider(retrofit).getNews(3892357)
        val latch = CountDownLatch(1)
        services.compose(SchedulersUtil.applySchedulersIO())
                .subscribe({
                    t: StoryDetail ->
                    Log.d("test", t.toString())
                    latch.countDown()
                }, {
                    e ->
                    e.printStackTrace()
                    latch.countDown()
                })
        latch.await()
    }

}