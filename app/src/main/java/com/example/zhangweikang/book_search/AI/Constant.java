package com.example.zhangweikang.book_search.AI;

import android.util.Log;

import com.alibaba.cloudapi.sdk.constant.SdkConstant;
import com.alibaba.cloudapi.sdk.model.ApiCallback;
import com.alibaba.cloudapi.sdk.model.ApiRequest;
import com.alibaba.cloudapi.sdk.model.ApiResponse;
import com.alibaba.cloudapi.sdk.model.HttpClientBuilderParams;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Constant {
    public static String mAnswer;

    public static class Demo_智能问答 {

        public static String answer="";

        static{
            //HTTP Client init
            HttpClientBuilderParams httpParam = new HttpClientBuilderParams();
            httpParam.setAppKey("24979062");
            httpParam.setAppSecret("acfb9fdffc7e66d75fc064a31d8818d8");
            HttpApiClient_智能问答.getInstance().init(httpParam);


            //HTTPS Client init
            HttpClientBuilderParams httpsParam = new HttpClientBuilderParams();
            httpsParam.setAppKey("24979062");
            httpsParam.setAppSecret("acfb9fdffc7e66d75fc064a31d8818d8");

            /**
            * HTTPS request use DO_NOT_VERIFY mode only for demo
            * Suggest verify for security
            */
            X509TrustManager xtm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
                }
            };

            SSLContext sslContext = null;
            try {
                sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());

            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (KeyManagementException e) {
                throw new RuntimeException(e);
            }
            HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            httpsParam.setSslSocketFactory(sslContext.getSocketFactory());
            httpsParam.setX509TrustManager(xtm);
            httpsParam.setHostnameVerifier(DO_NOT_VERIFY);

            HttpsApiClient_智能问答.getInstance().init(httpsParam);


        }

        public static void 智能回复接口HttpTest(){
            HttpApiClient_智能问答.getInstance().智能回复接口("沈阳天气" , new ApiCallback() {
                @Override
                public void onFailure(ApiRequest request, Exception e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(ApiRequest request, ApiResponse response) {
                    try {
                        Log.e("TAG",getResultString(response));
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            });
        }

        public static void 智能回复接口HttpsTest(String question){
            HttpsApiClient_智能问答.getInstance().智能回复接口(question , new ApiCallback() {
                @Override
                public void onFailure(ApiRequest request, Exception e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(ApiRequest request, ApiResponse response) {
                    try {
                        answer=getResultString(response);
    //                    Log.i("Demo:",answer);
                        mAnswer =answer;


                    }catch (Exception ex){
                        answer="";
                        ex.printStackTrace();
                    }
    //                Log.i("*******",answer);
                }
            });


        }



        private static String getResultString(ApiResponse response) throws IOException {
            StringBuilder result = new StringBuilder();
    //        result.append("Response from backend server").append(SdkConstant.CLOUDAPI_LF).append(SdkConstant.CLOUDAPI_LF);
    //        result.append("ResultCode:").append(SdkConstant.CLOUDAPI_LF).append(response.getCode()).append(SdkConstant.CLOUDAPI_LF).append(SdkConstant.CLOUDAPI_LF);
    //        if(response.getCode() != 200){
    //            result.append("Error description:").append(response.getHeaders().get("X-Ca-Error-Message")).append(SdkConstant.CLOUDAPI_LF).append(SdkConstant.CLOUDAPI_LF);
    //        }

            result.append("").append(SdkConstant.CLOUDAPI_LF).append(new String(response.getBody() , SdkConstant.CLOUDAPI_ENCODING));

            return result.toString();
        }
    }
}
