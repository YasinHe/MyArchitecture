package com.mazouri.mvpkotlin.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.util.LongSparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.mazouri.mvpkotlin.MVPApplication
import com.mazouri.mvpkotlin.injection.component.ConfigPersistentComponent
import com.mazouri.mvpkotlin.injection.component.DaggerConfigPersistentComponent
import com.mazouri.mvpkotlin.injection.component.FragmentComponent
import com.mazouri.mvpkotlin.injection.module.FragmentModule
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by HeYingXin on 2018/4/13.
 */
abstract class BaseFragment<C>: Fragment() {

    private var mFragmentComponent: FragmentComponent? = null
    private var mFragmentId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFragmentId = savedInstanceState?.getLong(KEY_FRAGMENT_ID) ?: NEXT_ID.getAndIncrement()
        val configPersistentComponent: ConfigPersistentComponent
        if (sComponentsArray.get(mFragmentId) == null) {
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .applicationComponent(MVPApplication[activity as Context].component)
                    .build()
            sComponentsArray.put(mFragmentId, configPersistentComponent)
        } else {
            configPersistentComponent = sComponentsArray.get(mFragmentId)
        }
        mFragmentComponent = configPersistentComponent.fragmentComponent(FragmentModule(this))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(layout, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    abstract val layout: Int

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(KEY_FRAGMENT_ID, mFragmentId)
    }

    override fun onDestroy() {
        if (!activity!!.isChangingConfigurations) {
            sComponentsArray.remove(mFragmentId)
        }
        super.onDestroy()
    }

    fun fragmentComponent(): FragmentComponent {
        return mFragmentComponent as FragmentComponent
    }

    companion object {
        private val KEY_FRAGMENT_ID = "KEY_FRAGMENT_ID"
        private val sComponentsArray = LongSparseArray<ConfigPersistentComponent>()
        private val NEXT_ID = AtomicLong(0)
    }
}