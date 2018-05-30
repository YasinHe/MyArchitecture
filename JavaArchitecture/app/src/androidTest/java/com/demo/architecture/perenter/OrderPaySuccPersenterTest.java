package com.xingsu.ufuli.perenter;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.xingsu.ufuli.base.Constants;
import com.xingsu.ufuli.ui.ordeinfo.OrderPaySuccActivity;
import com.xingsu.ufuli.ui.ordeinfo.OrderPaySuccContract;
import com.xingsu.ufuli.ui.ordeinfo.OrderPaySuccPersenter;
import com.xingsu.ufuli.utils.RxTools;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by HeYingXin on 2017/9/11
 */
@RunWith(AndroidJUnit4.class)
public class OrderPaySuccPersenterTest {

    private OrderPaySuccPersenter persenter;

    OrderPaySuccContract.View view;

    @Rule
    public ActivityTestRule<OrderPaySuccActivity> mActivityRule = new ActivityTestRule<>(OrderPaySuccActivity.class, true, false);

    static {
        RxTools.setUpRxSchedulers();
        Constants.Server.isUnitTest = true;
    }

    @Before
    public void setupMocksAndView(){
        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);
        view = mActivityRule.getActivity();
        persenter = new OrderPaySuccPersenter();
        persenter.attachView(view);
    }

    @Test
    public void testPaySuccessCode() throws Exception {
        String id = "65";
        persenter.getOrder(id);
        Assert.assertTrue(persenter.paySuccModel!=null);
    }

}