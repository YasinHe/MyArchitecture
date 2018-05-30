package com.xingsu.ufuli.ui;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.xingsu.ufuli.R;
import com.xingsu.ufuli.base.Constants;
import com.xingsu.ufuli.ui.login.LoginActivity;
import com.xingsu.ufuli.utils.RxTools;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by HeYingXin on 2017/8/30
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityUiTest {

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
    }

    @Test
    public void testRejisterNullPhoneError() throws Exception {
        onView(withId(R.id.et_phone)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.btn_code)).perform(click());
        onView(withId(android.R.id.message))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(withText("请输入手机号")));
    }

    @Test
    public void testRejisterFailurePhoneError() throws Exception {
        onView(withId(R.id.et_phone)).perform(typeText("123456789"), closeSoftKeyboard());
        onView(withId(R.id.btn_code)).perform(click());
        onView(withId(android.R.id.message))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(withText("请输入正确手机号")));
    }

    @Test
    public void testRejisterNullCodeError() throws Exception {
        onView(withId(R.id.et_phone)).perform(typeText("12345678901"), closeSoftKeyboard());
        onView(withId(R.id.et_verification_code)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.btn_start)).check(matches(isEnabled()));
        onView(withId(R.id.btn_start)).perform(click());
        onView(withId(android.R.id.message))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(withText("请输入验证码")));
    }

    @Test
    public void testRejisterFailureCodeError() throws Exception {
        onView(withId(R.id.et_phone)).perform(typeText("12345678901"), closeSoftKeyboard());
        onView(withId(R.id.et_verification_code)).perform(typeText("1213"), closeSoftKeyboard());
        onView(withId(R.id.btn_start)).perform(click());
        onView(withId(android.R.id.message))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(withText("请输入正确验证码")));
    }

}