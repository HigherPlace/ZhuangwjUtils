package com.zwj.zwjutils.net.callback;

import android.text.TextUtils;

import com.zwj.zwjutils.net.bean.ResponseBean;

import java.util.List;

/**
 * Created by zwj on 2016/12/9.
 */

public abstract class ParseListCallback<T> extends SimpleCallBack{

    @Override
    public void onSuccess(ResponseBean responseBean) {
        String result = responseBean.getResult();
        if(!TextUtils.isEmpty(result)) {

        }
    }

    public abstract void onSuccess(ResponseBean responseBean, List<T> list);
}
