package com.zwj.zhuangwjutils.activity.adapterdemo;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhy.adapter.common.CommonSingleSelectionAdapter;
import com.zwj.zhuangwjutils.R;
import com.zwj.zhuangwjutils.activity.base.BaseAutoLayoutCommonActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试通用单选recyclerview
 */
public class CommonSingleSelectionAdapterDemoActivity extends BaseAutoLayoutCommonActivity {
    private RecyclerView rv;
    private CommonSingleSelectionAdapter<String> adapter;
    private List<String> datas;
    private boolean isLinearLayout = true;  // recyclerview的布局

    @Override
    protected int getContentViewId() {
        return R.layout.activity_common_single_selection_adapter_demo;
    }

    @Override
    protected void findViews() {
        rv = getView(R.id.rv);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        datas = new ArrayList<>();
        for (int i=0; i<10; i++) {
            datas.add("item "+i);
        }

        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.setHasFixedSize(true);
        adapter = new CommonSingleSelectionAdapter(mContext, datas);
        rv.setAdapter(adapter);
        rv.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void setListener() {

    }

    public void change(View view) {
        if(isLinearLayout) {
            rv.setLayoutManager(new GridLayoutManager(mContext, 3));
        }else {
            rv.setLayoutManager(new LinearLayoutManager(mContext));
        }

        isLinearLayout = !isLinearLayout;
    }
}
