package com.xingsu.ufuli.perenter;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.xingsu.ufuli.base.Constants;
import com.xingsu.ufuli.ui.login.LoginActivity;
import com.xingsu.ufuli.ui.login.LoginContract;
import com.xingsu.ufuli.ui.login.LoginPersenter;
import com.xingsu.ufuli.utils.RxTools;
import com.xingsu.ufuli.utils.SPUtils;

import org.junit.Assert;
import org.junit.Before;
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
 * Created by HeYingXin on 2017/8/30
 */
@RunWith(AndroidJUnit4.class)
public class LoginPersenterRequestTest {

    private LoginPersenter persenter;

    LoginContract.View view;

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class, true, false);

    static {
        RxTools.setUpRxSchedulers();
        Constants.Server.isUnitTest = true;
    }

    @Before
    public void setupMocksAndView(){
        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);
        view = mActivityRule.getActivity();
        persenter = new LoginPersenter();
        persenter.attachView(view);
    }

    @Test
    public void testLogin_ErrorCode() throws Exception {
        String user_id = (String) SPUtils.get(Constants.UserConfig.USER_ID,"");
        SPUtils.put(Constants.UserConfig.USER_ID,"");
        String phone = "13856975026";
        persenter.start(phone, "111111");
        Assert.assertEquals(SPUtils.get(mActivityRule.getActivity(),Constants.UserConfig.USER_ID,""), "");
        onView(withId(android.R.id.message))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(withText("登录失败")));
        SPUtils.put(Constants.UserConfig.USER_ID,user_id);
    }

    @Test
    public void testRejisterSuccessCode() throws Exception {
        String phone = "13856975026";
        persenter.getCode(phone);
        onView(withId(android.R.id.message))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(withText("验证码已发送")));
    }

}