package com.github.jokar.zhihudaily.ui.activity

import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import com.github.jokar.zhihudaily.R
import com.github.jokar.zhihudaily.model.entities.MainMenu
import com.github.jokar.zhihudaily.presenter.MainPresenter
import com.github.jokar.zhihudaily.ui.view.MainView
import com.github.jokar.zhihudaily.utils.system.JLog
import com.trello.rxlifecycle2.android.ActivityEvent
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.common_toolbar.*
import kotlinx.android.synthetic.main.nav_main_content.*
import javax.inject.Inject


class MainActivity : BaseActivity(), MainView {


    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    fun initView() {
        //initToolBar
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawerLayout.setDrawerListener(toggle)
        toggle.syncState()
        //
        recyclerView.layoutManager = LinearLayoutManager(this)


    }

    override fun onWindowInitialized() {
        super.onWindowInitialized()
        presenter.getThemes(bindUntilEvent(ActivityEvent.DESTROY))
    }

    override fun loadThemes(data: ArrayList<MainMenu>) {
        JLog.d("loadThemes")
    }

}
