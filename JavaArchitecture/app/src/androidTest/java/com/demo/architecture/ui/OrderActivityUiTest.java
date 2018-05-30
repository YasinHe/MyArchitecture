package com.xingsu.ufuli.ui;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.xingsu.ufuli.R;
import com.xingsu.ufuli.base.Constants;
import com.xingsu.ufuli.model.ShopInfoModel;
import com.xingsu.ufuli.model.event.MessageEvent;
import com.xingsu.ufuli.ui.order.OrderActivity;
import com.xingsu.ufuli.utils.RxTools;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by HeYingXin on 2017/9/5.
 */
@RunWith(AndroidJUnit4.class)
public class OrderActivityUiTest {
     MessageEvent resultEvent;
     ShopInfoModel shopInfoModel;
     ShopInfoModel.DataBean dataBean;

    @Rule
    public ActivityTestRule<OrderActivity> mActivityRule = new ActivityTestRule<>(OrderActivity.class, true, false);

    static {
        RxTools.setUpRxSchedulers();
        Constants.Server.isUnitTest = true;
    }

    @Before
    public void setupMocksAndView(){
        Constants.Server.isUnitTest = true;
        resultEvent = new MessageEvent(1,0.01,null);
        shopInfoModel  = new ShopInfoModel();
        dataBean = new ShopInfoModel.DataBean();
        dataBean.setStore_name("奥斯卡");
        dataBean.setAddress("帝都");
        shopInfoModel.setData(dataBean);
    }

    @Test(expected=RuntimeException.class)
    public void test_null_data(){
        Constants.Server.isUnitTest = false;
        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);
        onView(withId(android.R.id.message))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(withText("下单失败!")));//这里页面死亡，是弹不出来的才对
    }

    @Test
    public void test_ui_data(){
        Intent intent = new Intent();
        intent.putExtra("resultEvent", resultEvent);
        intent.putExtra("shopInfoModel", shopInfoModel);
        mActivityRule.launchActivity(intent);
        onView(withId(R.id.tv_location_name)).check(matches(withText("奥斯卡")));
        onView(withId(R.id.tv_location_describe)).check(matches(withText("帝都")));
        onView(withId(R.id.tv_goods_price)).check(matches(withText("0.01")));
    }

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    @Test
    public void test_ui_pop1(){
        Intent intent = new Intent();
        intent.putExtra("resultEvent", resultEvent);
        intent.putExtra("shopInfoModel", shopInfoModel);
        mActivityRule.launchActivity(intent);
        onView(withId(R.id.et_order_time)).perform(click());
//        mActivityRule.getActivity().orderChooseTimePopWindow.getTvCancel().callOnClick();
//        Assert.assertFalse(mActivityRule.getActivity().orderChooseTimePopWindow.getPopupWindow().isShowing());
//        onView(withId(R.id.et_order_time)).perform(click());
//        mActivityRule.getActivity().orderChooseTimePopWindow.getTvConfirm().callOnClick();
//        Assert.assertTrue(!mActivityRule.getActivity().orderChooseTimePopWindow.getPopupWindow().isShowing());
//        int [] time = DateUtil.getCurrentHourMin();
//        onView(withId(R.id.et_order_time)).check(matches(withText(time[0]+":"+time[1])));
    }

    @Test
    public void test_ui_pop2(){
        Intent intent = new Intent();
        intent.putExtra("resultEvent", resultEvent);
        intent.putExtra("shopInfoModel", shopInfoModel);
        mActivityRule.launchActivity(intent);
        onView(withId(R.id.et_order_way)).perform(click());
    }

}