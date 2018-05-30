package com.xingsu.ufuli.fun;

import com.xingsu.ufuli.base.Constants;
import com.xingsu.ufuli.ui.login.LoginContract;
import com.xingsu.ufuli.ui.login.LoginPersenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by HeYingXin on 2017/8/30
 */
public class LoginPersenterUiTest {

    private LoginPersenter persenter;

    @Mock
    LoginContract.View view;

    static {
        Constants.Server.isUnitTest = true;
    }

    @Before
    public void setupMocksAndView(){
        MockitoAnnotations.initMocks(this);
        persenter = new LoginPersenter();
        when(view.getContext()).thenReturn(null);
        persenter.attachView(view);
    }

    @Test
    public void testSendCode_EmptyUsername() throws Exception {
        persenter.getCode("");
        verify(view).showError("没有输入账号",null);
    }

    @Test
    public void testLogin_EmptyPassword() throws Exception {
        persenter.start("18355376855","");
        verify(view).showError("没有输入验证码",null);
        persenter.start("","1213");
        verify(view).showError("没有输入账号",null);
    }

    @Test
    public void testSendCode_Error() throws Exception {
        persenter.getCode("18355376855");
        verify(view).SendCodeFail();
    }

}