package com.xingsu.ufuli.ui;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.xingsu.ufuli.base.Constants;
import com.xingsu.ufuli.ui.shop.ShopActivity;
import com.xingsu.ufuli.utils.RxTools;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
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
public class ShopActivityUiTest {

    @Rule
    public ActivityTestRule<ShopActivity> mActivityRule = new ActivityTestRule<>(ShopActivity.class, true, false);

    static {
        RxTools.setUpRxSchedulers();
        Constants.Server.isUnitTest = true;
    }

    @Test(expected=RuntimeException.class)
    public void test_null_data(){
        Intent intent = new Intent();
        intent.putExtra("store_id","");
        mActivityRule.launchActivity(intent);
        onView(withId(android.R.id.message))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(withText("大清还没亡")));
    }
}