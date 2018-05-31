package com.demo.architecture.api;

import com.demo.architecture.R;
import com.demo.architecture.base.App;
import com.demo.architecture.base.ComponentHolder;
import com.demo.architecture.utils.L;

import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by HeYingXin on 2017/9/28.
 */
public class SslContextFactory {
    private static final String CLIENT_TRUST_PASSWORD = "weareyll";//信任证书密码
    private static final String CLIENT_AGREEMENT = "TLS";//使用协议
    private static final String CLIENT_TRUST_MANAGER = "X509";
    private static final String CLIENT_TRUST_KEYSTORE = "BKS";
    SSLContext sslContext = null;

    public SSLContext getSslSocket() {
        try {
            //取得SSL的SSLContext实例
            sslContext = SSLContext.getInstance(CLIENT_AGREEMENT);
            //取得TrustManagerFactory的X509密钥管理器实例
            TrustManagerFactory trustManager = TrustManagerFactory.getInstance(CLIENT_TRUST_MANAGER);
            //取得BKS密库实例
            KeyStore tks = KeyStore.getInstance(CLIENT_TRUST_KEYSTORE);
            InputStream is = ComponentHolder.getAppComponent().myApplication().getResources().openRawResource(R.raw.myyll_com_bundle);
            try {
                tks.load(is, CLIENT_TRUST_PASSWORD.toCharArray());
            } catch (Exception e){
                e.printStackTrace();
                L.e("ERROR","严重bug");
            }
            finally {
                is.close();
            }
            //初始化密钥管理器
            trustManager.init(tks);
            // 初始化SSLContext
            sslContext.init(null, trustManager.getTrustManagers(), null);
        } catch (Exception e) {
            L.e("SslContextFactory", e.getMessage());
        }
        return sslContext;
    }

}