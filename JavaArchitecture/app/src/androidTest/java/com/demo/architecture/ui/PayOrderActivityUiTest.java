package com.xingsu.ufuli.ui;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.xingsu.ufuli.R;
import com.xingsu.ufuli.base.Constants;
import com.xingsu.ufuli.model.event.PlaceOrderModel;
import com.xingsu.ufuli.ui.pay.PayOrderActivity;
import com.xingsu.ufuli.utils.RxTools;
import com.xingsu.ufuli.utils.SPUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by HeYingXin on 2017/9/5.
 */
@RunWith(AndroidJUnit4.class)
public class PayOrderActivityUiTest {

    PlaceOrderModel placeOrderModel;
    PlaceOrderModel.DataBean bean;
    PlaceOrderModel.DataBean.OrderBean orderBean;
    List<PlaceOrderModel.DataBean.GoodsListBean> goodsListBeans = new ArrayList<>();

    @Rule
    public ActivityTestRule<PayOrderActivity> mActivityRule = new ActivityTestRule<>(PayOrderActivity.class, true, false);

    static {
        RxTools.setUpRxSchedulers();
        Constants.Server.isUnitTest = true;
    }

    @Before
    public void setupMocksAndView(){
        Constants.Server.isUnitTest = true;
        placeOrderModel = new PlaceOrderModel();
        bean = new PlaceOrderModel.DataBean();
        bean.setGoods_list(goodsListBeans);
        orderBean = new PlaceOrderModel.DataBean.OrderBean();
        orderBean.setStore_name("飞机家");
        orderBean.setOrder_id("1");
        orderBean.setOrder_sn("xn2017");
        orderBean.setPay_amount(0.01);
        bean.setOrder(orderBean);
        placeOrderModel.setData(bean);
    }

    @Test(expected=NullPointerException.class)
    public void test_null_data() throws Exception{
        Constants.Server.isUnitTest = false;
        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);
        onView(withId(android.R.id.message))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(withText("下单失败!")));
    }

    @Test
    public void test_ui_data(){
        Intent intent = new Intent();
        intent.putExtra("PlaceOrderModel", placeOrderModel);
        mActivityRule.launchActivity(intent);
        onView(withId(R.id.tv_all_size)).check(matches(withText("共0件")));
        onView(withId(R.id.tv_shop_name)).check(matches(withText("飞机家")));
        onView(withId(R.id.tv_shop_number)).check(matches(withText("订单编号：xn2017")));
        onView(withId(R.id.tv_goods_price)).check(matches(withText("￥0.01")));
        onView(withId(R.id.tv_pay)).check(matches(withText("确认支付 ￥0.01")));
    }

    @Test
    public void test_Pay_User(){
        Intent intent = new Intent();
        intent.putExtra("PlaceOrderModel", placeOrderModel);
        mActivityRule.launchActivity(intent);
        int way = (int) SPUtils.get(Constants.SharedPreferences.LAST_PAY_WAY,0);
        if(way==0){
            onView(withId(R.id.cb_alibaba)).check(matches(isChecked()));
            onView(withId(R.id.cb_wechat)).check(matches(not(isChecked())));
        }else{
            onView(withId(R.id.cb_wechat)).check(matches(isChecked()));
            onView(withId(R.id.cb_alibaba)).check(matches(not(isChecked())));
        }
    }

    @Test
    public void test_ui_pay(){
        Intent intent = new Intent();
        intent.putExtra("PlaceOrderModel", placeOrderModel);
        mActivityRule.launchActivity(intent);

        onView(withId(R.id.cb_alibaba)).perform(click());
        onView(withId(R.id.cb_alibaba)).check(matches(isChecked()));
        onView(withId(R.id.cb_wechat)).check(matches(not(isChecked())));

        onView(withId(R.id.cb_wechat)).perform(click());
        onView(withId(R.id.cb_wechat)).check(matches(isChecked()));
        onView(withId(R.id.cb_alibaba)).check(matches(not(isChecked())));

        onView(withId(R.id.lly_alibaba)).perform(click());
        onView(withId(R.id.cb_alibaba)).check(matches(isChecked()));
        onView(withId(R.id.cb_wechat)).check(matches(not(isChecked())));

        onView(withId(R.id.lly_wechat)).perform(click());
        onView(withId(R.id.cb_wechat)).check(matches(isChecked()));
        onView(withId(R.id.cb_alibaba)).check(matches(not(isChecked())));
    }

    @Test
    public void test_weChat_pay(){
        Intent intent = new Intent();
        intent.putExtra("PlaceOrderModel", placeOrderModel);
        mActivityRule.launchActivity(intent);

        onView(withId(R.id.cb_alibaba)).perform(click());
        onView(withId(R.id.tv_pay)).perform(click());
    }

    @Test
    public void test_AILBaBa_pay(){
        Intent intent = new Intent();
        intent.putExtra("PlaceOrderModel", placeOrderModel);
        mActivityRule.launchActivity(intent);

        onView(withId(R.id.lly_wechat)).perform(click());
        onView(withId(R.id.tv_pay)).perform(click());
    }

}