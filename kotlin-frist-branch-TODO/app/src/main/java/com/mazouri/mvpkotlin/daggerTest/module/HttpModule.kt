package com.mazouri.mvpkotlin.daggerTest.module

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by HeYingXin on 2018/4/16.
 * 这种情况的Module比较复杂，第一可以传入构造，这个构造会在Dagger的builder中写入
 * 第二其中一个依赖又要依赖另一个（比如fun2就依赖fun1），如果@Provides标注的方法
 * 有参数，那就会自动寻找本Module其他返回值类型为参数类型且也被@Provides标注的方法
 * 如果本Module没有，那就得看该类构造器有没有被@Inject标注(一般情况下本Module中方法的返回值都不能相同，实际也可以)
 */
@Module
class HttpModule (size:Int){

    private var cacheSize:Int = size

    @Provides
    fun cache():Int{
        return cacheSize
    }

    //一般简单的Module返回类型就是Inject需要注入的类型
    @Provides
    fun provideOKHttpClientDemo(cacheSize:Int): OKHttpClientDemo {
        val client = OKHttpClientDemo()
        client.setCacheSize(cacheSize)
        return client
    }

    //这个方法参数的OKHttpClientDemo是上面的方法返回的，所以dagger会去找上面的那个方法们找不到就看构造有没有Inject
    @Provides
    fun provideRetofitManager(client:OKHttpClientDemo):RetofitManager{
        return RetofitManager(client)
    }
}