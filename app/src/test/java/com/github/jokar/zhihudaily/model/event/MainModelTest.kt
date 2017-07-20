package com.github.jokar.zhihudaily.model.event

import android.util.Log
import com.github.jokar.zhihudaily.BuildConfig
import com.github.jokar.zhihudaily.model.entities.MainMenu
import com.github.jokar.zhihudaily.model.event.callback.ListDataCallBack
import com.github.jokar.zhihudaily.ui.activity.MainActivity
import com.trello.rxlifecycle2.android.ActivityEvent
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog
import java.util.concurrent.CountDownLatch

/**
 * Created by JokAr on 2017/7/20.
 */
@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
class MainModelTest {

    @InjectMocks
    lateinit internal var model: MainModel

    lateinit var activity: MainActivity
    @Before
    fun setUp() {
        ShadowLog.stream = System.out
        //初始化Mock
        MockitoAnnotations.initMocks(this)

        activity = Robolectric.buildActivity(MainActivity::class.java).create().start().get()
    }

    @Test
    fun getThemes() {
        var latch = CountDownLatch(1)

        model.getThemes(activity.bindUntilEvent(ActivityEvent.DESTROY),
                object : ListDataCallBack<MainMenu> {
                    override fun data(data: ArrayList<MainMenu>) {
                        Log.d("test", data.toString())
                        latch.countDown()
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        latch.countDown()
                    }
                })

        latch.await()

    }

}