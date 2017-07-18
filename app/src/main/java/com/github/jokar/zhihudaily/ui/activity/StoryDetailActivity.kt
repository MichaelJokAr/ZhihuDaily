package com.github.jokar.zhihudaily.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.github.jokar.zhihudaily.R
import com.github.jokar.zhihudaily.model.entities.story.StoryEntity
import com.github.jokar.zhihudaily.model.rxbus.RxBus
import com.github.jokar.zhihudaily.model.rxbus.event.UpdateCollectionEvent
import com.github.jokar.zhihudaily.presenter.StoryDetailPresenter
import com.github.jokar.zhihudaily.ui.view.common.SingleDataView
import com.github.jokar.zhihudaily.utils.image.ImageLoader
import com.github.jokar.zhihudaily.widget.LoadLayout
import com.github.jokar.zhihudaily.widget.likeButton
import com.github.jokar.zhihudaily.widget.loadLayout
import com.like.LikeButton
import com.like.OnLikeListener
import com.trello.rxlifecycle2.android.ActivityEvent
import dagger.android.AndroidInjection
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.themedToolbar
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.support.v4.nestedScrollView
import javax.inject.Inject


/**
 * Created by JokAr on 2017/6/25.
 */
class StoryDetailActivity : BaseActivity(), SingleDataView<StoryEntity>,
        NestedScrollView.OnScrollChangeListener {

    @Inject
    lateinit var presenter: StoryDetailPresenter

    val maxHeight = 600;

    var id: Int = 0
    var data: StoryEntity? = null
    var imageHeight: Int? = null

    var nestedScrollView: NestedScrollView? = null
    var rlTop: RelativeLayout? = null
    var image: ImageView? = null
    var tvTiTle: TextView? = null
    var tvAuthor: TextView? = null
    var webView: WebView? = null
    var toolbar: Toolbar? = null
    var likeCollect: LikeButton? = null
    var likeButton: LikeButton? = null
    var loadView: LoadLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        createView()
        initToolbar(toolbar, "")
        id = intent.getIntExtra("id", 0)

        init()
    }


    override fun onWindowInitialized() {
        super.onWindowInitialized()
        getData()
    }

    private fun getData() {
        presenter.getStoryDetail(id, bindUntilEvent(ActivityEvent.DESTROY))
    }

    fun init() {
        imageHeight = resources.getDimensionPixelOffset(R.dimen.storyDetailImage)
    }

    /**
     * 更新数据
     */
    fun update() {
        presenter.updateStory(data!!, bindUntilEvent(ActivityEvent.DESTROY))
    }

    override fun onResume() {
        super.onResume()
        webView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        webView?.onPause()
    }

    override fun getDataStart() {
        loadView?.visibility = View.VISIBLE
        nestedScrollView?.visibility = View.GONE
        loadView?.showLoad()
    }

    override fun loadData(result: StoryEntity) {

        runOnUiThread({
            data = result
            if (!TextUtils.isEmpty(data?.image)) {
                ImageLoader.loadImage(this,
                        data?.image!!,
                        R.mipmap.image_small_default,
                        image)
                tvAuthor?.text = data?.image_source
                tvTiTle?.text = data?.title
            } else {
                rlTop?.visibility = View.GONE
            }
            //判断是否已经喜欢了
            if (data?.like == 1) {
                likeButton?.isLiked = true
            }

            //判断是否已经收藏了
            if (data?.collection == 1) {
                likeCollect?.isLiked = true
            }

            webView?.loadDataWithBaseURL("", data?.body, "text/html", "utf-8", null)
        })
    }

    override fun loadComplete() {
        runOnUiThread {
            loadView?.visibility = View.GONE
            nestedScrollView?.visibility = View.VISIBLE
        }
    }

    override fun fail(e: Throwable) {
        runOnUiThread {
            loadView?.showError(e.message!!)
        }
    }

    var lastY = 0
    override fun onScrollChange(v: NestedScrollView?, x: Int, y: Int, oldX: Int, oldY: Int) {
        rlTop?.scrollTo(x, -y / 2)

        if (y in 0..maxHeight) {
            toolbar?.alpha = getAlphaForActionBar(y)
        } else {
            if (lastY - y > 20) {
                //上滑
                lastY = y
                if (toolbar?.visibility == View.GONE) {
                    toolbar?.visibility = View.VISIBLE
                }
                toolbar?.alpha = 1f
            } else if (y - lastY > 20) {
                //下滑
                lastY = y
                toolbar?.alpha = 0f
                if (toolbar?.visibility == View.VISIBLE) {
                    toolbar?.visibility = View.GONE
                }
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

    override fun onDestroy() {
        super.onDestroy()
        ImageLoader.clear(this,image)
        data = null
        presenter?.destroy()
        webView?.destroy()
        webView = null
        loadView?.retryListener = null
        loadView = null
        nestedScrollView?.setOnClickListener { null }
        nestedScrollView = null
        rlTop = null
        tvTiTle = null
        tvAuthor = null
        image = null
        toolbar = null
        likeButton?.setOnLikeListener(null)
        likeButton = null
        likeCollect?.setOnLikeListener(null)
        likeCollect = null
    }

    fun createView() {
        coordinatorLayout {
            nestedScrollView = nestedScrollView {
                setOnScrollChangeListener(this@StoryDetailActivity)

                linearLayout {
                    orientation = LinearLayout.VERTICAL

                    //view
                    view {
                    }.lparams(width = matchParent, height = dip(48))
                    //
                    rlTop = relativeLayout {
                        gravity = Gravity.BOTTOM
                        //image
                        image = imageView {
                            imageResource = R.mipmap.splash
                            scaleType = ImageView.ScaleType.FIT_XY
                        }.lparams(width = matchParent, height = matchParent)
                        //
                        view {
                            backgroundColor = Color.parseColor("#000000")
                            alpha = 0.15f
                        }.lparams(width = matchParent, height = matchParent)
                        //title
                        tvTiTle = textView {
                            gravity = Gravity.CENTER or Gravity.LEFT
                            textSize = 23f
                            textColor = Color.WHITE
                            setPadding(dip(10), dip(10), dip(10), dip(10))
                        }.lparams(width = matchParent, height = wrapContent) {
                            above(R.id.tvAuthor)
                        }
                        //author
                        tvAuthor = textView {
                            id = R.id.tvAuthor
                            gravity = Gravity.CENTER or Gravity.RIGHT
                            textSize = 13f
                            textColor = Color.WHITE
                            setPadding(dip(10), 0, dip(10), dip(10))
                        }.lparams(width = matchParent, height = wrapContent) {
                            addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                        }

                    }.lparams(width = matchParent, height = dip(200))
                    //webView
                    webView = webView {
                    }.lparams(width = matchParent, height = matchParent)

                }.lparams(width = matchParent, height = matchParent)

            }.lparams(width = matchParent, height = matchParent)
            //loadLayout
            loadView = loadLayout {
                backgroundColor = Color.parseColor("#eeeeee")
                retryListener = LoadLayout.RetryListener { getData() }
            }.lparams(width = matchParent, height = matchParent)

            //toolbar
            toolbar = themedToolbar(R.style.Base_ThemeOverlay_AppCompat_Dark_ActionBar) {
                backgroundColor = ContextCompat.getColor(this@StoryDetailActivity, R.color.colorPrimary)

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.RIGHT
                    //collect
                    likeCollect = likeButton {
                        setIconSizeDp(35)
                        setLikeDrawableRes(R.mipmap.collected)
                        setUnlikeDrawableRes(R.mipmap.collect)
                        setOnLikeListener(object :OnLikeListener{
                            override fun liked(p0: LikeButton?) {
                                data?.collection = 1
                                update()
                                //通知收藏页面刷新
                                RxBus.getInstance().post(UpdateCollectionEvent())
                            }

                            override fun unLiked(p0: LikeButton?) {
                                data?.collection = 0
                                update()
                                //通知收藏页面刷新
                                RxBus.getInstance().post(UpdateCollectionEvent())
                            }
                        })
                    }.lparams(width = dip(50), height = matchParent)
                    //like
                    likeButton = likeButton {
                        setIconSizeDp(25)
                        setLikeDrawableRes(R.mipmap.ic_liked)
                        setUnlikeDrawableRes(R.mipmap.ic_like)
                        setOnLikeListener(object :OnLikeListener{
                            override fun liked(p0: LikeButton?) {
                                data?.like = 1
                                update()
                            }

                            override fun unLiked(p0: LikeButton?) {
                                data?.like = 0
                                update()
                            }
                        })
                    }.lparams(width = dip(50), height = matchParent) {
                        rightMargin = dip(5)
                    }

                }.lparams(width = matchParent, height = matchParent)

            }.lparams(width = matchParent, height = dip(48))

        }
    }
}



