package com.mazouri.mvpkotlin.daggerTest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mazouri.mvpkotlin.R
import javax.inject.Inject

class Main2Activity : AppCompatActivity() {

    /**
     * 注意这里的Factory他的构造穿的是product，不是他去实例化的product，而是现在activity
     * 他实例化了factory，然后发现了参数product，他就又实例化了product，交给factory去使用
     */
    @Inject
    lateinit var factory:Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        DaggerMain2ActivityComponent.create().inject(this)
        factory.needProduct(3)
    }
}
