package com.github.jokar.zhihudaily.ui.fragment


import android.support.v4.widget.NestedScrollView
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.github.jokar.zhihudaily.R
import com.github.jokar.zhihudaily.widget.LazyFragment
import com.github.jokar.zhihudaily.widget.LoadLayout

/**
 * 主题
 * Created by JokAr on 2017/7/2.
 */
class ThemeFragment : LazyFragment() {
    @BindView(R.id.swipeRefreshLayout)
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    @BindView(R.id.nestedScrollView)
    lateinit var nestedScrollView: NestedScrollView
    @BindView(R.id.image)
    lateinit var image: ImageView
    @BindView(R.id.tvTiTle)
    lateinit var tvTiTle: TextView
    @BindView(R.id.recyclerView)
    lateinit var recyclerView: RecyclerView
    @BindView(R.id.loadView)
    lateinit var loadView: LoadLayout

    var bind:Unbinder? = null
    override fun getView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.fragment_theme, container, false)
    }

    override fun initViews(view: View) {
         bind = ButterKnife.bind(this, view)
    }

    override fun loadData() {
        super.loadData()
        
    }
    override fun onDestroyView() {
        super.onDestroyView()
        bind?.unbind()
    }

}