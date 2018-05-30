package com.xingsu.ufuli.view;

import com.xingsu.ufuli.BuildConfig;
import com.xingsu.ufuli.base.Constants;
import com.xingsu.ufuli.utils.RxTools;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by HeYingXin on 2017/9/5.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class , sdk = 21)
public class ShopActivityShadowsUiTest {


    static {
        RxTools.setUpRxSchedulers();
        Constants.Server.isUnitTest = true;
    }

    @Before
    public void setUp() {
    }

    /**
     * 测试
     */
    @Test
    public void testActivity() {
    }

    /**
     * 生命周期测试
     */
    @Test
    public void testLifecycle() {
    }

    /**
     * 测试控件状态
     */
    @Test
    public void testViewState() {
    }

    /**
     * 测试Fragment
     */
    @Test
    public void testFragment() {

    }

}