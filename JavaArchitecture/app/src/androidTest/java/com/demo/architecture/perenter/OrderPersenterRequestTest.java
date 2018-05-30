package com.xingsu.ufuli.perenter;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.xingsu.ufuli.base.Constants;
import com.xingsu.ufuli.model.ShopInfoModel;
import com.xingsu.ufuli.ui.order.OrderActivity;
import com.xingsu.ufuli.ui.order.OrderContract;
import com.xingsu.ufuli.ui.order.OrderPersenter;
import com.xingsu.ufuli.utils.RxTools;
import com.xingsu.ufuli.utils.SPUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by HeYingXin on 2017/9/9
 */
@RunWith(AndroidJUnit4.class)
public class OrderPersenterRequestTest {

    private OrderPersenter persenter;

    OrderContract.View view;

    @Rule
    public ActivityTestRule<OrderActivity> mActivityRule = new ActivityTestRule<>(OrderActivity.class, true, false);

    static {
        RxTools.setUpRxSchedulers();
        Constants.Server.isUnitTest = true;
    }

    @Before
    public void setupMocksAndView(){
        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);
        view = mActivityRule.getActivity();
        persenter = new OrderPersenter();
        persenter.attachView(view);
    }

    @Test
    public void testCommiteOrderSucc() throws Exception {
        String id = "1";

        List<ShopInfoModel.DataBean.ListBean.GoodsBean> getmList = new ArrayList<>();
        ShopInfoModel.DataBean.ListBean.GoodsBean goodsBean = new ShopInfoModel.DataBean.ListBean.GoodsBean();
        goodsBean.setGoods_id("1");
        goodsBean.setCount(1);
        getmList.add(goodsBean);

        int way = 2;
        String time = "2:34";
//        persenter.placeOrder(id,getmList,way,time,"",null,null);
        Assert.assertTrue(persenter.placeOrderModel.getData().getOrder().getPay_amount()==0.01);
    }

    @Test
    public void testCommiteOrder_needLogin() throws Exception {
        String id1 = (String) SPUtils.get(Constants.UserConfig.USER_ID,"");
        String token = (String) SPUtils.get(Constants.UserConfig.TOKEN,"");

        SPUtils.put(Constants.UserConfig.USER_ID,"");
        SPUtils.put(Constants.UserConfig.TOKEN,"");

        String id = "1";
        List<ShopInfoModel.DataBean.ListBean.GoodsBean> getmList = new ArrayList<>();
        ShopInfoModel.DataBean.ListBean.GoodsBean goodsBean = new ShopInfoModel.DataBean.ListBean.GoodsBean();
        goodsBean.setGoods_id("1");
        goodsBean.setCount(1);
        getmList.add(goodsBean);
        int way = 2;
        String time = "2:34";
//        persenter.placeOrder(id,getmList,way,time,"",null,null);

        onView(withId(android.R.id.message))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(withText("您尚未登录，无法为您下单")));
        SPUtils.put(Constants.UserConfig.USER_ID,id1);
        SPUtils.put(Constants.UserConfig.TOKEN,token);
    }

    @Test
    public void testCommiteOrder_null_id() throws Exception {
        String id = "";
        List<ShopInfoModel.DataBean.ListBean.GoodsBean> getmList = new ArrayList<>();
        ShopInfoModel.DataBean.ListBean.GoodsBean goodsBean = new ShopInfoModel.DataBean.ListBean.GoodsBean();
        goodsBean.setGoods_id("1");
        goodsBean.setCount(1);
        getmList.add(goodsBean);
        int way = 2;
        String time = "2:34";
//        persenter.placeOrder(id,getmList,way,time,"",null,null);
        Assert.assertTrue(persenter.placeOrderModel==null);
    }

}