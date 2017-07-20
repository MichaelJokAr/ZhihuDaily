package com.github.jokar.zhihudaily.presenter

import android.util.Log
import com.github.jokar.zhihudaily.BuildConfig
import com.github.jokar.zhihudaily.model.event.MainModel
import com.github.jokar.zhihudaily.ui.activity.MainActivity
import com.github.jokar.zhihudaily.ui.view.MainView
import com.trello.rxlifecycle2.android.ActivityEvent
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog

/**
 * Created by JokAr on 2017/7/20.
 */
@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class,
        sdk = intArrayOf(23))
class MainPresenterTest {
    /**
     * 用于测试真实接口返回的数据
     */
    var presenter: MainPresenter? = null
    /**
     * 用于测试模拟接口返回的数据
     */
    var mockPresenter: MainPresenter? = null

    @Mock
    lateinit internal var view: MainView

    @InjectMocks
    lateinit internal var model: MainModel

    var controller: ActivityController<MainActivity>? = null
    @Before
    fun setupMocksAndView() {
        ShadowLog.stream = System.out
        //初始化Mock
        MockitoAnnotations.initMocks(this)

        presenter = MainPresenter(model, view)
        mockPresenter = MainPresenter(model, view)

        controller = Robolectric.buildActivity(MainActivity::class.java)
                .create()
                .start()
                .resume()
    }

    @Test
    fun getThemes() {
        presenter?.getThemes(controller?.get()?.bindUntilEvent(ActivityEvent.DESTROY)!!)
        Log.d("test",controller?.get()?.menuList.toString())
    }

    @Test
    fun destroy() {
        controller?.destroy()

    }

}