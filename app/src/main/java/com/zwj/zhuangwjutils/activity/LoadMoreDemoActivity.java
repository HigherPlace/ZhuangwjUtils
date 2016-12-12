package com.zwj.zhuangwjutils.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;
import com.zwj.zhuangwjutils.R;
import com.zwj.zhuangwjutils.activity.base.BaseAutoLayoutCommonActivity;
import com.zwj.zwjutils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class LoadMoreDemoActivity extends BaseAutoLayoutCommonActivity {
    private static final  int MESSAGE_LOAD_MORE = 1234;
    private static final  int MESSAGE_REFRESH = 1235;
    private static final  int MESSAGE_SET_ERROR = 1236;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView rvProduct;
    private LoadMoreWrapper mLoadMoreWrapper;
    private CommonAdapter<String> adapter;
    private List<String> dataList;
    private int page = 1;



    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case MESSAGE_LOAD_MORE:
                    loadData();
                    refreshUI();
                    mLoadMoreWrapper.onFinish();
                    break;

                case MESSAGE_REFRESH:
                    dataList.clear();
                    loadData();
                    refreshUI();
                    mLoadMoreWrapper.onFinish();
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;

                case MESSAGE_SET_ERROR :
                    mLoadMoreWrapper.setLoadError();
                    break;
            }
        }
    };

    @Override
    protected int getContentViewId() {
        return R.layout.activity_load_more_demo;
    }

    @Override
    protected void findViews() {
        mSwipeRefreshLayout = getView(R.id.swipe);
        rvProduct = getView(R.id.rv_productinfo);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        dataList = getData(1);
        refreshUI();
    }

    @Override
    protected void setListener() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
           refresh();
        });
    }

    public void refreshUI() {
        if (adapter == null || mLoadMoreWrapper == null) {
            rvProduct.setLayoutManager(new LinearLayoutManager(mContext));
            rvProduct.setHasFixedSize(true);
            // TODO
            adapter = new CommonAdapter<String>(mContext, R.layout.item_loadmore, dataList) {
                @Override
                protected void convert(ViewHolder holder, String s, int position) {
                    holder.setText(R.id.tv, s);
                }
            };

            mLoadMoreWrapper = new LoadMoreWrapper(adapter);
            setLoadStatusOnRefreshUI();
            mLoadMoreWrapper.setOnLoadMoreListener(() -> loadMore());

            rvProduct.setAdapter(mLoadMoreWrapper);
            rvProduct.setItemAnimator(new DefaultItemAnimator());

        } else {
            setLoadStatusOnRefreshUI();
            adapter.setDatas(dataList);
            mLoadMoreWrapper.notifyDataSetChanged();
        }
    }

    private int count = 30;
    private List<String> getData(int page) {

        List<String> list = new ArrayList<>();

        int start = (page - 1)*10;
        int end = start + 10;
        for(int i=start; i<end; i++) {
            list.add("测试 "+i);
        }

        return list;
    }

    private void loadData() {
        List<String> tempList = getData(page);
        dataList.addAll(tempList);
    }

    /**
     * 为了防止第一页就将最后的加载布局显示出来，每页的条数一定要大于屏幕，
     * 如果全部加载出来的不够就设置状态
     */
    public void setLoadStatusOnRefreshUI() {
        if (this.count == 0) {
            mLoadMoreWrapper.setLoadStatus(LoadMoreWrapper.LoadStatus.LOAD_EMPTY);
        } else if (dataList.size() > 0 && dataList.size() == this.count) {
            mLoadMoreWrapper.setLoadStatus(LoadMoreWrapper.LoadStatus.LOAD_ALL);
        }
    }

    private boolean isError = true;

    private void loadMore() {
        if(page <= 3) {
            page++;
            if(page == 2 && isError) { // 模拟出错
                page--;
                isError = false;
                mHandler.sendEmptyMessageDelayed(MESSAGE_SET_ERROR, 1000);
                return;
            }

            // 模拟从后台取数据（必须停止滑动才能刷新adapter，否则报错）
            mHandler.sendEmptyMessageDelayed(MESSAGE_LOAD_MORE, 1000);
        }else {
            LogUtils.sysout("dfsfsdfdsfdsf--------");
        }

    }

    private void refresh() {
        page = 1;
        mHandler.sendEmptyMessageDelayed(MESSAGE_REFRESH, 1000);
    }
}
