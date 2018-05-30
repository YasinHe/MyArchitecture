package com.xingsu.ufuli.ui;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.xingsu.ufuli.R;
import com.xingsu.ufuli.base.Constants;
import com.xingsu.ufuli.ui.search.location.SearchActivity;
import com.xingsu.ufuli.utils.RxTools;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by HeYingXin on 2017/9/1.
 */
@RunWith(AndroidJUnit4.class)
public class SearchActivityUiTest {

    @Rule
    public ActivityTestRule<SearchActivity> mActivityRule = new ActivityTestRule<>(SearchActivity.class, true, false);

    static {
        RxTools.setUpRxSchedulers();
        Constants.Server.isUnitTest = true;
    }

    @Test
    public void test_null_data(){
        Intent intent = new Intent();
        intent.putExtra("searchName", "");
        intent.putParcelableArrayListExtra("locations", null);
        mActivityRule.launchActivity(intent);
        onView(withId(R.id.et_location)).check(matches(withText("")));
    }

    @Test
    public void test_has(){
        Intent intent = new Intent();
        intent.putExtra("searchName", "合肥");
        intent.putParcelableArrayListExtra("locations", null);
        mActivityRule.launchActivity(intent);
        onView(withId(R.id.et_location)).check(matches(withText("合肥")));
    }

    @Test
    public void test_delete(){
        Intent intent = new Intent();
        intent.putExtra("searchName", "安徽国际金融中心");
        intent.putParcelableArrayListExtra("locations", null);
        mActivityRule.launchActivity(intent);
        onView(withId(R.id.iv_delete)).perform(click());
        onView(withId(R.id.et_location)).check(matches(withText("")));
    }
}