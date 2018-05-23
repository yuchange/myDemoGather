package com.yunzhi.lib.net;

import java.util.Map;

/**
 * 功能：
 * Created by yaoyafeng on 17/1/13 下午2:19
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/1/13 下午2:19
 * @modify by reason:{方法名}:{原因}
 */
public interface IHttpManager{

    /**
     * post请求
     * @param urlStr url
     * @param map 参数
     * @param callback 回调
     */
    void doPost(String urlStr, Map<String, String> map, IHttpCallback callback);

    void doHead(String urlStr);
}
