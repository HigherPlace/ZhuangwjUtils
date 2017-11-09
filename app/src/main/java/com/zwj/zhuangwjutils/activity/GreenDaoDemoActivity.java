package com.zwj.zhuangwjutils.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.zwj.zhuangwjutils.MyApplication;
import com.zwj.zhuangwjutils.R;
import com.zwj.zhuangwjutils.activity.base.BaseAutoLayoutCommonActivity;
import com.zwj.zhuangwjutils.bean.User;
import com.zwj.zhuangwjutils.greendao.UserDaoOpe;
import com.zwj.zwjutils.LogUtils;

import java.util.List;


public class GreenDaoDemoActivity extends BaseAutoLayoutCommonActivity {
    private EditText et;
    private TextView tv;

//    private UserDao userDao;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_realm_demo;
    }

    @Override
    protected void findViews() {
        et = getView(R.id.et);
        tv = getView(R.id.tv);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
//        userDao = MyApplication.getGlobalContext().getDaoSession().getUserDao();
    }

    @Override
    protected void setListener() {

    }

    public void insert(View view) {
        String id = et.getText().toString();
        User user = new User();
        user.setId(id);
        user.setAge(23);
        user.setName("test"+id);
        user.setNum(44);
        long result = UserDaoOpe.insertData(MyApplication.getGlobalContext(), user);
        LogUtils.i(TAG, "result ---> "+result);
    }

    public void getFromRealm(View view) {
        List<User> users = UserDaoOpe.queryAll(MyApplication.getGlobalContext());
        LogUtils.i(TAG, "users ---> "+ JSON.toJSONString(users));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
