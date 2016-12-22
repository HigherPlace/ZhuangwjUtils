package com.zwj.zwjutils.net.bean;

import android.content.Context;
import android.text.TextUtils;

import com.zwj.zwjutils.net.NetManager;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 网络请求的实体类
 */
public class RequestBean {
    /**
     * 最大重连次数
     */
    public static final int MAX_RECONNECTION_COUNT = 2;
    public static final int METHOD_GET = 2000;
    public static final int METHOD_POST = 2001;

    //    private String key;        // 用于标记是否为同一个请求实体
    private String url;
    /**
     * 请求的方式
     */
    private int requestMethod;
    /**
     * 请求参数
     */
    private Map<String, String> paramMap;
    private Map<String, List<String>> paramArrayMap;    // 存数组的参数
    private String bodyContent; // 以json形式传递的参数
    private int timeOut = 10 * 10000;   // 连接超时时间，单位毫秒

    /**
     * 是否需要对结果进行解析处理,默认不进行解析
     */
    private boolean isNeedParse;

    // 回调接口
    private NetManager.RequestCallBack callback;

    private int count;    // 超时重连次数
    private boolean isNeedReconnection; // 是否需要重连，true需要；

    /**
     * 是否需要添加cookies信息,默认true
     * 使用cookie还需要在sp文件中保存cookie方会自动添加
     */
    private boolean isNeedCookies = true;

    /**
     * 是否自动追加token参数，默认true
     */
    private boolean isNeedToken = true;

    private Callback.Cancelable cancelable;
    private String tag;

    private String sessionId;

    public RequestBean() {
        super();

        addParam("isMobile", "true");
    }

    public RequestBean(String url, int requestMethod) {
        this();
        this.url = url;
        this.requestMethod = requestMethod;
    }

    public RequestBean(String url, int requestMethod, NetManager.RequestCallBack callback) {
        this(url, requestMethod);
        this.callback = callback;
    }

    public int getRequestMethod() {
        return requestMethod;
    }

    public RequestBean setRequestMethod(int requestMethod) {
        this.requestMethod = requestMethod;
        return this;
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public RequestBean setParamMap(Map<String, String> paramMap) {
        this.paramMap = paramMap;
        return this;
    }

    public boolean isNeedParse() {
        return isNeedParse;
    }

    /**
     * 默认false
     * @param isNeedParse
     * @return
     */
    public RequestBean setNeedParse(boolean isNeedParse) {
        this.isNeedParse = isNeedParse;
        return this;
    }

    public NetManager.RequestCallBack getCallback() {
        return callback;
    }

    public RequestBean setCallback(NetManager.RequestCallBack callback) {
        this.callback = callback;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public RequestBean setUrl(String url) {
        this.url = url;
        return this;
    }

    public int getCount() {
        return count;
    }

    public RequestBean setCount(int count) {
        this.count = count;
        return this;
    }

    public boolean isNeedReconnection() {
        return isNeedReconnection;
    }

    public RequestBean setNeedReconnection(boolean needReconnection) {
        isNeedReconnection = needReconnection;
        return this;
    }

    public boolean isNeedCookies() {
        return isNeedCookies;
    }

    /**
     * 默认true
     * @param needCookies
     * @return
     */
    public RequestBean setNeedCookies(boolean needCookies) {
        isNeedCookies = needCookies;
        return this;
    }

    public boolean isNeedToken() {
        return isNeedToken;
    }

    /**
     * 是否自动追加token参数，默认true
     */
    public RequestBean setNeedToken(boolean needToken) {
        isNeedToken = needToken;
        return this;
    }

    public RequestBean addParam(String name, String value) {
        if (!TextUtils.isEmpty(value)) {
            if (paramMap == null) {
                paramMap = new HashMap<>();
            }
            paramMap.put(name, value);
        }
        return this;
    }

    public String getBodyContent() {
        return bodyContent;
    }

    public RequestBean setBodyContent(String bodyContent) {
        this.bodyContent = bodyContent;
        return this;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public RequestBean setTimeOut(int timeOut) {
        this.timeOut = timeOut;
        return this;
    }

    /**
     * 发起网络请求
     *
     * @param context
     * @return
     */
    public Callback.Cancelable request(Context context) {
        this.cancelable = NetManager.request(context, this);
        return cancelable;
    }

    public Map<String, List<String>> getParamArrayMap() {
        return paramArrayMap;
    }

    public RequestBean setParamArrayMap(Map<String, List<String>> paramArrayMap) {
        this.paramArrayMap = paramArrayMap;
        return this;
    }

    /**
     * 添加数组参数
     *
     * @param name
     * @param valueList
     * @return
     */
    public RequestBean addParamArray(String name, List<String> valueList) {
        if (valueList != null && valueList.size() > 0) {
            if (paramArrayMap == null) {
                paramArrayMap = new HashMap<>();
            }
            paramArrayMap.put(name, valueList);
        }
        return this;
    }

    public Callback.Cancelable getCancelable() {
        return cancelable;
    }

    public RequestBean setCancelable(Callback.Cancelable cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public RequestBean setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public void download() {

    }

    public String getSessionId() {
        return sessionId;
    }

    public RequestBean setSessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }
}
