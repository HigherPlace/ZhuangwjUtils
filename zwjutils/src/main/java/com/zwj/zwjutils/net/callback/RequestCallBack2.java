package com.zwj.zwjutils.net.callback;

import com.zwj.zwjutils.net.NetManager;

/**
 * Created by zwj on 2017/3/22.
 */

public interface RequestCallBack2 extends NetManager.RequestCallBack {

    /**
     * 未登录
     */
    void onUnlogin(String msg);
}
