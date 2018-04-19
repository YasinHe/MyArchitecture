package com.mazouri.mvpkotlin.daggerTest.module


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.plus.PlusOneButton
import com.mazouri.mvpkotlin.R
import javax.inject.Inject

/**
 * Created by HeYingXin on 2018/4/17.
 * 这种情况是Component依赖Component，情况就是一个跟另外一个提供的依赖有重复，没有必要在写一遍
 * 这种关系就像是extends
 * 使用Dependence实现，需要注意：1 父component要显示的写出需要暴露给子Component的依赖  2：注解使用dependencies连接父component  3：子component的实例化方式
 */
class Main2Fragment : Fragment() {
    private val PLUS_ONE_URL = "http://developer.android.com"
    private var mPlusOneButton: PlusOneButton? = null

    @Inject
    lateinit var retofitManager:RetofitManager
//    @Inject
//    lateinit var user: User

    private lateinit var httpFragmentComponent2: HttpFragmentComponent2
    private lateinit var httpFragmentComponent3Build: HttpFragmentComponent3.NeedDataBulider

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main2, container, false)
        mPlusOneButton = view.findViewById(R.id.plus_one_button) as PlusOneButton

        //正常Dependence(子Component直接依赖于父的)
        DaggerHttpFragmentComponent.builder().httpActivityComponent((activity as HttpActivity).getHttpActivityComponent())
                .build().inject(this)

        //SubComponent（先定义子Component再用SubComponent标注，父Component去获取这个子Component的方法）
        httpFragmentComponent2 = (activity as HttpActivity).getHttpActivityComponent().httpFragmentComponent2()

        //SubComponent子Component需要传值的情况
        httpFragmentComponent3Build = (activity as HttpActivity).getHttpActivityComponent().needDataBulider()
        httpFragmentComponent3Build.needData(DataModule("HeYingXin","https://"))
        httpFragmentComponent3Build.build().inject(this)
        return view
    }

    override fun onResume() {
        super.onResume()
        mPlusOneButton!!.initialize(PLUS_ONE_URL, PLUS_ONE_REQUEST_CODE)
    }

    companion object {
        private val PLUS_ONE_REQUEST_CODE = 0
    }

}
