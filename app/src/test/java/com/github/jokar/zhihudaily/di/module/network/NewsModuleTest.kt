package com.github.jokar.zhihudaily.di.module.network

import android.util.Log
import com.github.jokar.zhihudaily.BuildConfig
import com.github.jokar.zhihudaily.di.module.NetworkModule
import com.github.jokar.zhihudaily.model.entities.story.StoryDetail
import com.sunagy.mazcloud.utlis.rxjava.SchedulersUtil
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
import java.util.concurrent.CountDownLatch
import android.security.NetworkSecurityPolicy
import org.robolectric.annotation.Implementation
import org.robolectric.annotation.Implements


/**
 * Created by JokAr on 2017/7/20.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(23))
class NewsModuleTest {

    lateinit var retrofit: Retrofit
    @Mock
    lateinit internal var news: NewsModule

    @Before
    fun before() {
        ShadowLog.stream = System.out
        //初始化Mock
        MockitoAnnotations.initMocks(this)

        retrofit = NetworkModule().providerRetrofit()

    }

    @Test
    fun provider() {
        val services  = Mockito.verify(news).provider(retrofit).getNews(3892357)
        var latch = CountDownLatch(1)
        services.compose(SchedulersUtil.applySchedulersIO())
                .subscribe({
                    t: StoryDetail ->
                    Log.w("test", t.toString())
                    latch.countDown()
                }, {
                    error ->
                    error.printStackTrace()
                    latch.countDown()
                }, {

                }, {
                    Log.d("test", "onSatrt")
                })

        latch.await()
    }

}