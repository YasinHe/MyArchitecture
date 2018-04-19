package com.mazouri.mvpkotlin.daggerTest.module

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mazouri.mvpkotlin.R
import kotlinx.android.synthetic.main.activity_http.*
import javax.inject.Inject

class HttpActivity : AppCompatActivity() {

    /**
     * 这个情况下OKHttpClientDemo和RetofitManager都是不可改动的，属于架包类，这样我们需要使用Module
     * 不可以使用inject的，就使用module标注，并对需要inject的类型使用Provides
     * 最后是Component连接  modules链接
     * 如果module里面找不到，就会找类构造器有没有被Inject标注
     */

    private lateinit var httpActivityComponent: HttpActivityComponent

    @Inject
    lateinit var okHttpClient: OKHttpClientDemo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_http)
        /**
         * 因为Component构造有参数，所以没有create
         * 这里通过httpModule传入Module，并传入参数8，OKHttpClientDemo在实例化的时候如果发现需要构造参数，回去从HttpModule中找
         * Provides，如果提供类型有，就会注给OKHttpClientDemo
         */
        httpActivityComponent = DaggerHttpActivityComponent.builder().httpModule(HttpModule(8)).build()
        httpActivityComponent.inject(this)
        Log.e("TAG","HTTP---"+okHttpClient.getCacheSize()+"--"+okHttpClient.setCacheSize(1)+"---"+okHttpClient.getCacheSize())
        btn_go.text = "来来来"
    }

    override fun onResume() {
        super.onResume()
        supportFragmentManager.beginTransaction().replace(R.id.fly_fragment,Main2Fragment())
                .commit()
    }

    fun getHttpActivityComponent():HttpActivityComponent{
        return httpActivityComponent
    }
}
