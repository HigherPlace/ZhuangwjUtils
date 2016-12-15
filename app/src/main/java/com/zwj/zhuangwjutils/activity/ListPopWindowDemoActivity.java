package com.zwj.zhuangwjutils.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.zwj.customview.popupwindow.ListPopupWindow;
import com.zwj.zhuangwjutils.R;
import com.zwj.zhuangwjutils.activity.base.BaseActivity;
import com.zwj.zwjutils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 从底部弹出的list的popupwindow使用demo
 */
public class ListPopWindowDemoActivity extends BaseActivity {
    private RelativeLayout rootView;
    private ListPopupWindow listPopupWindow;
    private List<String> datas;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_list_pop_window;
    }

    @Override
    protected void findViews() {
        rootView = getView(R.id.activity_list_pop_window);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        datas = new ArrayList<>();
        for (int i=0; i<10; i++) {
            datas.add("item "+i);
        }
    }

    @Override
    protected void setListener() {

    }

    public void popListWindow(View view) {
        if (listPopupWindow == null) {
            listPopupWindow = new ListPopupWindow(mContext, datas);

            listPopupWindow.setOnPWItemClickListener(new ListPopupWindow.OnPWItemClickListener() {
                @Override
                public void onItemClick(ListPopupWindow listPopupWindow, AdapterView<?> parent, View view, int position, long id) {
                    ToastUtil.toast(mContext, "onclick --> "+parent.getAdapter().getItem(position));
                    listPopupWindow.dismiss();
                }
            });

            listPopupWindow
                    .setAnimationStyle(R.style.local_popupwindow_anim);
        }

        if (!listPopupWindow.isShowing()) {
            listPopupWindow.showAtLocation(rootView,
                    Gravity.BOTTOM, 0, 0);
        }
    }
}
