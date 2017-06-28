package com.github.jokar.zhihudaily.ui.activity

import android.R.attr.scrollY
import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.view.View
import com.github.jokar.zhihudaily.R
import com.github.jokar.zhihudaily.model.entities.story.StoryDetail
import com.github.jokar.zhihudaily.presenter.StoryDetailPresenter
import com.github.jokar.zhihudaily.ui.view.common.SingleDataView
import com.github.jokar.zhihudaily.utils.image.ImageLoader
import com.github.jokar.zhihudaily.utils.system.ScrollUtils
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

    override fun onScrollChange(v: NestedScrollView?, x: Int, y: Int, oldX: Int, oldY: Int) {
        rlTop.scrollTo(x, -y / 2)

        val baseColor = resources.getColor(R.color.colorPrimary)
        val height = resources.getDimensionPixelSize(R.dimen.storyDetailImage)
        val alpha: Float = Math.min(1, y / height).toFloat()
        toolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor))

//        appBarLayout.background.alpha = 125
    }

    fun getAlphaForActionBar(scrollY: Int): Float {
        val minDist = 0
        val maxDist = 600
        if (scrollY > maxDist) {
            return 0f
        } else if (scrollY < minDist) {
            return 0f
        } else {
            var alpha = 0f
            alpha = 1f - (1f / maxDist * scrollY)
            return alpha
        }
    }
}



