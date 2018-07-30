package com.mazouri.mvpkotlin.task.main

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.mazouri.mvpkotlin.R
import com.mazouri.mvpkotlin.base.BaseActivity
import com.mazouri.mvpkotlin.base.ComponentHolder
import com.mazouri.mvpkotlin.base.Constants
import com.mazouri.mvpkotlin.data.model.Repository
import com.mazouri.mvpkotlin.utils.L
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**
 * Created by HeYingXin on 2018/4/16.
 */
@Route(path = Constants.Route.MAIN_ACTIVITY, group = Constants.Route.GROUP_ONE)
class MainActivity : BaseActivity(), MainContract.View {

    private var mAdapter: RepositoriesAdapter? = null

    @Inject lateinit var mMainPresenter: MainPresenter

    override val layout: Int
        get() = R.layout.activity_main

    @Autowired
    internal var key1: Long = 0

    @Autowired
    internal var key3: String? = null

    fun startMainActivity() {
        ARouter.getInstance().build(Constants.Route.MAIN_ACTIVITY, Constants.Route.GROUP_ONE).withLong("key1", 666L)
                .withString("key3", "888").navigation(ComponentHolder.getAppComponent().context(), object : NavigationCallback {
                    override fun onFound(postcard: Postcard) {
                        L.e("zhao", "onArrival: 找到了 ")
                    }

                    override fun onLost(postcard: Postcard) {
                        L.e("zhao", "onArrival: 找不到了 ")
                    }

                    override fun onArrival(postcard: Postcard) {
                        L.e("zhao", "onArrival: 跳转完了 ")
                    }

                    override fun onInterrupt(postcard: Postcard) {
                        L.e("zhao", "onArrival: 被拦截了 ")
                    }
                })
    }

    fun startMainActivity2(activity: Activity) {
        //直接通过所有拦截器并且制定requestCode
        ARouter.getInstance().build(Constants.Route.MAIN_ACTIVITY, Constants.Route.GROUP_ONE).withLong("key1", 666L)
                .withString("key3", "888").greenChannel().navigation(activity, Constants.IntentRequest.MAIN_CODE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)
        mMainPresenter.attachView(this)

        setupViews()

        showProgress()
        fab.setOnClickListener {
            showProgress()
            mMainPresenter.loadRepositories(getRepoUser())
        }
        mMainPresenter.loadRepositories(getRepoUser())
    }

    private fun getRepoUser(): String {
        return edit.text.toString().trim()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMainPresenter.detachView()
    }

    private fun setupViews() {
        mAdapter = RepositoriesAdapter(ArrayList<Repository>(), {
            Toast.makeText(this, "您选择了" + it.name, Toast.LENGTH_SHORT).show()
        })

        recyclerViewRepositories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewRepositories.adapter = mAdapter
    }

    private fun showProgress() {
        recyclerViewRepositories.visibility = View.GONE
        progress_bar.visibility = View.VISIBLE
        fab.isEnabled = false
    }

    private fun hideProgress() {
        recyclerViewRepositories.visibility = View.VISIBLE
        progress_bar.visibility = View.GONE
        fab.isEnabled = true
    }

    override fun showOrganizations(repositories: MutableList<Repository>) {
        mAdapter?.addRepositories(repositories)
        mAdapter?.notifyDataSetChanged()
        hideProgress()
    }

    override fun showLoadReposFailed(error: String?) {

        hideProgress()
    }

    override fun showError(error: String?) {

    }

    override fun showError(stringResId: Int) {

    }


}
