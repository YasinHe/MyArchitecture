package com.xingsu.ufuli.perenter;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.xingsu.ufuli.base.Constants;
import com.xingsu.ufuli.ui.search.location.SearchActivity;
import com.xingsu.ufuli.ui.search.location.SearchContract;
import com.xingsu.ufuli.ui.search.location.SearchPersenter;
import com.xingsu.ufuli.utils.RxTools;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

/**
 * Created by HeYingXin on 2017/9/1
 */
@RunWith(AndroidJUnit4.class)
public class SearchPersenterRequestTest {

    private SearchPersenter persenter;

    SearchContract.View view;

    @Rule
    public ActivityTestRule<SearchActivity> mActivityRule = new ActivityTestRule<>(SearchActivity.class, true, false);

    static {
        RxTools.setUpRxSchedulers();
        Constants.Server.isUnitTest = true;
    }

    @Before
    public void setupMocksAndView(){
        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);
        view = mActivityRule.getActivity();
        persenter = new SearchPersenter();
        persenter.attachView(view);
    }

    @Test
    public void testSearch_ErrorCode() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        String text = "13856975026";
        persenter.searchLocation(text,latch);
        latch.await();
        Assert.assertTrue(persenter.listModel.size() <= 0);
    }

    @Test
    public void testSearchSuccessCode() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        String text = "安徽国际";
        persenter.searchLocation(text,latch);
        latch.await();
        Assert.assertTrue(persenter.listModel.size() > 0);
    }

}