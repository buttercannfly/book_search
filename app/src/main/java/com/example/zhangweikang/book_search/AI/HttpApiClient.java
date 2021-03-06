//
//  Created by  fred on 2017/1/12.
//  Copyright © 2016年 Alibaba. All rights reserved.
//

package com.example.zhangweikang.book_search.AI;

import com.alibaba.cloudapi.sdk.enums.HttpMethod;
import com.alibaba.cloudapi.sdk.enums.ParamPosition;
import com.alibaba.cloudapi.sdk.enums.Scheme;
import com.alibaba.cloudapi.sdk.model.ApiCallback;
import com.alibaba.cloudapi.sdk.model.ApiRequest;
import com.alibaba.cloudapi.sdk.model.HttpClientBuilderParams;


public class HttpApiClient extends com.alibaba.cloudapi.sdk.client.HttpApiClient {
    public final static String HOST = "jisuznwd.market.alicloudapi.com";
    static HttpApiClient instance = new HttpApiClient();
    public static HttpApiClient getInstance(){return instance;}

    public void init(HttpClientBuilderParams httpClientBuilderParams){
        httpClientBuilderParams.setScheme(Scheme.HTTP);
        httpClientBuilderParams.setHost(HOST);
        super.init(httpClientBuilderParams);
    }




    public void 智能回复接口(String question , ApiCallback callback) {
        String path = "/iqa/query";
        ApiRequest request = new ApiRequest(HttpMethod.GET , path);
        request.addParam("question" , question , ParamPosition.QUERY , true);



        sendAsyncRequest(request , callback);
    }
}