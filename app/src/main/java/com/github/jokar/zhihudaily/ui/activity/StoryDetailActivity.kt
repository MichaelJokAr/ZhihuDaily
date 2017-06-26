package com.github.jokar.zhihudaily.ui.activity

import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.view.View
import com.github.jokar.zhihudaily.R
import com.github.jokar.zhihudaily.model.entities.story.StoryDetail
import com.github.jokar.zhihudaily.presenter.StoryDetailPresenter
import com.github.jokar.zhihudaily.ui.view.common.SingleDataView
import com.github.jokar.zhihudaily.utils.image.ImageLoader
import com.trello.rxlifecycle2.android.ActivityEvent
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_story_detail.*
import kotlinx.android.synthetic.main.common_load.*
import javax.inject.Inject

/**
 * Created by JokAr on 2017/6/25.
 */
class StoryDetailActivity : BaseActivity(), SingleDataView<StoryDetail> {

    @Inject
    lateinit var presenter: StoryDetailPresenter

    var id: Int = 0
    var data: StoryDetail? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_detail)
        id = intent.getIntExtra("id", 0)

        nestedScrollView.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {

            override fun onScrollChange(v: NestedScrollView?, scrollX: Int, scrollY: Int,
                                        oldScrollX: Int, oldScrollY: Int) {
                webView.scrollTo(scrollX, oldScrollY / 3)
            }
        })
    }

    override fun onWindowInitialized() {
        super.onWindowInitialized()
        presenter.getStoryDetail(id, bindUntilEvent(ActivityEvent.DESTROY))
    }

    override fun getDataStart() {
        loadView.visibility = View.VISIBLE
        nestedScrollView.visibility = View.GONE
        loadView.showLoad()
    }

    override fun loadData(result: StoryDetail) {
        data = result
        ImageLoader.loadImage(this,
                data?.image!!,
                R.mipmap.image_small_default,
                image)
        tvAuthor.text = data?.image_source

        webView.loadDataWithBaseURL("", data?.body, "text/html", "utf-8", null)
    }

    override fun loadComplete() {
        loadView.visibility = View.GONE
        nestedScrollView.visibility = View.VISIBLE
    }

    override fun fail(e: Throwable) {
        loadView.showError(e.message!!)
    }

}