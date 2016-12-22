package com.zwj.zhuangwjutils.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zwj.zhuangwjutils.R;
import com.zwj.zhuangwjutils.bean.ParseBean;
import com.zwj.zhuangwjutils.constant.UrlConstant;
import com.zwj.zwjutils.JsonUtil;
import com.zwj.zwjutils.LogUtils;
import com.zwj.zwjutils.net.NetManager;
import com.zwj.zwjutils.net.bean.RequestBean;
import com.zwj.zwjutils.net.bean.ResponseBean;
import com.zwj.zwjutils.net.callback.ParseBeanCallBack;
import com.zwj.zwjutils.net.callback.SimpleCallBack;
import com.zwj.zwjutils.net.callback.SimpleCommonCallback;

import org.xutils.http.cookie.DbCookieStore;

import java.io.File;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * NetManager网络工具的使用demo
 */
public class NetMangerDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_manger_demo);
    }

    public void testCookies(View view) {
        new RequestBean("http://10.111.24.21:8080/hch/debug/testRequest", RequestBean.METHOD_GET)
                .setCallback(new SimpleCallBack() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        LogUtils.sysout("result ---> "+responseBean.getResult());
                        DbCookieStore dbCookieStore = DbCookieStore.INSTANCE;
                        List<HttpCookie> cookies = dbCookieStore.getCookies();
                        for(HttpCookie cookie : cookies) {
                            LogUtils.sysout("name ---> "+cookie.getName());
                            LogUtils.sysout("coment ---> "+cookie.getComment());
                            LogUtils.sysout("comenturl ---> "+cookie.getCommentURL());
                            LogUtils.sysout("domain ---> "+cookie.getDomain());
                            LogUtils.sysout("path ---> "+cookie.getPath());
                        }
                    }
                }).request(this);
    }

    public void testParseBeanCallback(View view) {
        new RequestBean("http://10.111.24.21:8080/hch/debug/testParseBean", RequestBean.METHOD_GET)
                .setCallback(new ParseBeanCallBack<ParseBean>(ParseBean.class) {
                    @Override
                    public void onSuccess(ResponseBean responseBean, ParseBean obj) {
                        LogUtils.sysout(obj.getName());
                        LogUtils.sysout(obj.getTestParse());
                    }
                }).request(this);
    }

    public void testUpload(View view) {

        File sdcard = Environment.getExternalStorageDirectory();

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("code", "fsfdsfdf");

        List<File> uplodFileList = new ArrayList<>();
        uplodFileList.add(new File(sdcard+File.separator+"a.jpg"));
        uplodFileList.add(new File(sdcard+File.separator+"70BA32A6658B.jpg"));

        NetManager.uploadFile("http://10.111.24.21:8080/hch/uploadfile/uploadQuasiNewImages", uplodFileList, paramMap, new SimpleCommonCallback() {
            @Override
            public void onSuccess(String result) {
                LogUtils.sysout("reslut ---> "+result);
            }
        });
    }

    /**
     * 测试json和token一起传递
     * @param view
     */
    public void testJsonAndParamRequest(View view) {
        ParseBean parseBean = new ParseBean();
        parseBean.setName("aaa");
        parseBean.setTestParse("bbb");
        new RequestBean(UrlConstant.BASE_URL+"/debug/testJsonAndParamRequest", RequestBean.METHOD_POST)
                .setBodyContent(JsonUtil.toJSONString(parseBean)).request(this);
    }

    public void testMuldParamRequest(View view) {
        List<String> values = new ArrayList<>();
        values.add("a");
        values.add("c");
        values.add("b");

        new RequestBean(UrlConstant.BASE_URL+"/debug/testMuldParamRequest", RequestBean.METHOD_POST)
                .addParamArray("mul", values)
                .request(this);
    }

    public void getCar(View view) {
        new RequestBean("http://120.24.6.87/ChetongxiangHCH/product/rental/getInfoByID", RequestBean.METHOD_GET)
                .addParam("id", "1")
                .setNeedParse(true)
                .setCallback(new SimpleCallBack() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                    }
                }).request(this);
    }
}
