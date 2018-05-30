package com.xingsu.ufuli.perenter;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.xingsu.ufuli.R;
import com.xingsu.ufuli.base.Constants;
import com.xingsu.ufuli.ui.shop.ShopActivity;
import com.xingsu.ufuli.ui.shop.ShopContract;
import com.xingsu.ufuli.ui.shop.ShopPersenter;
import com.xingsu.ufuli.utils.RxTools;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by HeYingXin on 2017/9/5.
 */
@RunWith(AndroidJUnit4.class)
public class ShopPersenterTest {

    private ShopPersenter persenter;

    ShopContract.View view;

    @Rule
    public ActivityTestRule<ShopActivity> mActivityRule = new ActivityTestRule<>(ShopActivity.class, true, false);

    static {
        RxTools.setUpRxSchedulers();
        Constants.Server.isUnitTest = true;
    }

    @Test
    public void testSearchShop_ErrorCode() throws Exception {
        Intent intent = new Intent();
        intent.putExtra("store_id","3");
        mActivityRule.launchActivity(intent);
        view = mActivityRule.getActivity();
        persenter = new ShopPersenter();
        persenter.attachView(view);

        String store_id = "-1";
        persenter.getShopDetails(store_id);
        Assert.assertTrue(persenter.shopInfoModel==null);
    }

    @Test
    public void testSearchShopSuccessCode() throws Exception {

        Intent intent = new Intent();
        intent.putExtra("store_id","-1");
        mActivityRule.launchActivity(intent);
        view = mActivityRule.getActivity();
        persenter = new ShopPersenter();
        persenter.attachView(view);

        onView(withId(R.id.tv_shop_name)).check(matches(withText("早餐店")));
        onView(withId(R.id.tv_shop_notice)).check(matches(withText("公告:都是新鲜的早餐，请尽快食用哦！")));
        onView(withId(R.id.tv_shop_address)).check(matches(withText("店铺地址:null")));

        String store_id = "3";
        persenter.getShopDetails(store_id);
        Assert.assertTrue(persenter.shopInfoModel!=null);

        onView(withId(R.id.tv_shop_name)).check(matches(withText(persenter.shopInfoModel.getData().getStore_name())));
        onView(withId(R.id.tv_shop_notice)).check(matches(withText("公告："+persenter.shopInfoModel.getData().getNotice())));
        onView(withId(R.id.tv_shop_address)).check(matches(withText("店铺地址："+persenter.shopInfoModel.getData().getAddress())));
    }

}