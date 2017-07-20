package com.github.jokar.zhihudaily.di.module.network

import android.util.Log
import com.github.jokar.zhihudaily.di.module.NetworkModule
import com.github.jokar.zhihudaily.model.entities.story.LatestStory
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

/**
 * Created by JokAr on 2017/7/20.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(25))
class BeforeModuleTest {
    lateinit var retrofit: Retrofit

    lateinit  var model: BeforeModule

    @Before
    fun setUp() {
        ShadowLog.stream = System.out

        model = BeforeModule()
        retrofit = NetworkModule().providerRetrofit()
    }

    @Test
    fun beforeProvider() {

        val services = model.beforeProvider(retrofit).getStories(20170720)
        var latch = CountDownLatch(1)
        services.compose(SchedulersUtil.applySchedulersIO())
                .subscribe({
                    t: LatestStory ->
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