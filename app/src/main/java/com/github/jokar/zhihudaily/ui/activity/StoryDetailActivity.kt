package com.github.jokar.zhihudaily.ui.activity

import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.view.View
import com.github.jokar.zhihudaily.R
import com.github.jokar.zhihudaily.model.entities.story.StoryDetail
import com.github.jokar.zhihudaily.presenter.StoryDetailPresenter
import com.github.jokar.zhihudaily.ui.view.common.SingleDataView
import com.github.jokar.zhihudaily.utils.image.ImageLoader
import com.github.jokar.zhihudaily.utils.rxjava.ViewUtils
import com.github.jokar.zhihudaily.utils.system.JLog
import com.trello.rxlifecycle2.android.ActivityEvent
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_story_detail.*
import kotlinx.android.synthetic.main.common_load.*
import javax.inject.Inject


/**
 * Created by JokAr on 2017/6/25.
 */
class StoryDetailActivity : BaseActivity(), SingleDataView<StoryDetail>, NestedScrollView.OnScrollChangeListener {

    @Inject
    lateinit var presenter: StoryDetailPresenter

    val maxHeight = 600;

    var id: Int = 0
    var data: StoryDetail? = null
    var imageHeight: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_detail)
        initToolbar(toolbar, "")
        id = intent.getIntExtra("id", 0)

        nestedScrollView.setOnScrollChangeListener(this@StoryDetailActivity)
        imageHeight = resources.getDimensionPixelOffset(R.dimen.storyDetailImage)

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

    var lastY=0
    override fun onScrollChange(v: NestedScrollView?, x: Int, y: Int, oldX: Int, oldY: Int) {
        rlTop.scrollTo(x, -y / 2)

        if (y in 0..maxHeight) {
            toolbar.alpha = getAlphaForActionBar(y)
        } else {
            if (lastY - y > 20) {
                //上滑
                lastY = y
                toolbar.alpha = 1f
            } else if (y - lastY > 20) {
                //下滑
                lastY = y
                toolbar.alpha = 0f
            }
        }
    }

    fun getAlphaForActionBar(scrollY: Int): Float {
        val minDist = 0
        if (scrollY > maxHeight) {
            return 0f
        } else if (scrollY < minDist) {
            return 0f
        } else {
            var alpha = 0f
            alpha = 1f - (1f / maxHeight * scrollY)
            return alpha
        }
    }
}



