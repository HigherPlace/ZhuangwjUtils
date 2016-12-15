package com.zwj.zhuangwjutils.activity.adapterdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zwj.zhuangwjutils.R;
import com.zwj.zhuangwjutils.activity.base.BaseAutoLayoutCommonActivity;

import java.util.ArrayList;
import java.util.List;

public class CommonAdapterDemoActivity extends BaseAutoLayoutCommonActivity {
    private RecyclerView rv;
    private CommonAdapter<String> adapter;
    private List<String> datas;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_common_adapter_demo;
    }

    @Override
    protected void findViews() {
        rv = getView(R.id.rv);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        datas = new ArrayList<>();
            datas.add("通用单选列表");

        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.setHasFixedSize(true);

        adapter = new CommonAdapter<String>(mContext, R.layout.item, datas) {
            @Override
            protected void convert(ViewHolder holder, String item, int position) {
                holder.setText(R.id.btn, item);
            }
        };

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, String item, int position) {
                switch (item) {
                    case "通用单选列表":
                        startActivity(new Intent(mContext, CommonSingleSelectionAdapterDemoActivity.class));
                        break;
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, String item, int position) {
                return false;
            }
        });

        rv.setAdapter(adapter);
        rv.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void setListener() {

    }
}
