package com.zhy.adapter.recyclerview.wrapper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.utils.WrapperUtils;
import com.zwj.autolibrary.R;


/**
 * Created by zhy on 16/6/23.
 */
public class LoadMoreWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_TYPE_LOAD_MORE = Integer.MAX_VALUE - 2;

    private RecyclerView.Adapter mInnerAdapter;
    private View mLoadMoreView;
    private int mLoadMoreLayoutId = R.layout.layout_default_loading;
    private boolean isUseDefaultLoading = true;    // 是否是使用默认的加载布局

    private LoadStatus loadStatus = LoadStatus.LOAD_NORMAL;

    /**
     * 加载的状态
     */
    public static enum LoadStatus{
        /**
         * 全部加载完成(没有数据了)
         */
        LOAD_ALL,

        /**
         * 正在加载中
         */
        LOADING,

        /**
         * 处于加载完成，可以继续加载的正常状态(但并代表后台没有数据了)
         */
        LOAD_NORMAL,

        /**
         * 空数据
         */
        LOAD_EMPTY
    }

    public LoadMoreWrapper(RecyclerView.Adapter adapter) {
        mInnerAdapter = adapter;
    }

    private boolean hasLoadMore() {
        return mLoadMoreView != null || mLoadMoreLayoutId != 0;
    }


    private boolean isShowLoadMore(int position) {
        return hasLoadMore() && (position >= mInnerAdapter.getItemCount());
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowLoadMore(position)) {
            return ITEM_TYPE_LOAD_MORE;
        }
        return mInnerAdapter.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_LOAD_MORE) {
            ViewHolder holder;
            if (mLoadMoreView != null) {
                holder = ViewHolder.createViewHolder(parent.getContext(), mLoadMoreView);
            } else {
                holder = ViewHolder.createViewHolder(parent.getContext(), parent, mLoadMoreLayoutId);
            }
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    private void setLoadMoreContent(ViewHolder holder) {
        if(isUseDefaultLoading) {
            if(loadStatus == LoadStatus.LOAD_ALL) {
                holder.setVisibility(R.id.progress_wheel, false);
                holder.setText(R.id.tv, "已经没有数据了！");
            }else if(loadStatus == LoadStatus.LOAD_EMPTY) {
                holder.setVisibility(R.id.progress_wheel, false);
                holder.setText(R.id.tv, "");
                // TODO
            }else {
                holder.setVisibility(R.id.progress_wheel, true);
                holder.setText(R.id.tv, "努力加载中...");
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isShowLoadMore(position)) {
            if(holder instanceof ViewHolder) {
                setLoadMoreContent((ViewHolder) holder);
            }

            if (mOnLoadMoreListener != null && loadStatus == LoadStatus.LOAD_NORMAL) {
                loadStatus = LoadStatus.LOADING;
                mOnLoadMoreListener.onLoadMoreRequested();
            }
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                if (isShowLoadMore(position)) {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null) {
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });
    }


    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);

        if (isShowLoadMore(holder.getLayoutPosition())) {
            setFullSpan(holder);
        }
    }

    private void setFullSpan(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;

            p.setFullSpan(true);
        }
    }

    @Override
    public int getItemCount() {
        return mInnerAdapter.getItemCount() + (hasLoadMore() ? 1 : 0);
    }


    public interface OnLoadMoreListener {
        void onLoadMoreRequested();
    }

    private OnLoadMoreListener mOnLoadMoreListener;

    public LoadMoreWrapper setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        if (loadMoreListener != null) {
            mOnLoadMoreListener = loadMoreListener;
        }
        return this;
    }

    public LoadMoreWrapper setLoadMoreView(View loadMoreView) {
        mLoadMoreView = loadMoreView;
        notUseDefaultLoading();
        return this;
    }

    public LoadMoreWrapper setLoadMoreView(int layoutId) {
        mLoadMoreLayoutId = layoutId;
        notUseDefaultLoading();
        return this;
    }

    private void notUseDefaultLoading() {
        isUseDefaultLoading = false;
    }

    /**
     * 加载完全部数据
     */
    public void setLoadAll() {
        loadStatus = LoadStatus.LOAD_ALL;
    }

    public void setEmpty() {
        loadStatus = LoadStatus.LOAD_EMPTY;
    }

    public void reset() {
        loadStatus = loadStatus.LOAD_NORMAL;
    }

    public void onFinish() {
        if(loadStatus == LoadStatus.LOADING) {
            loadStatus = loadStatus.LOAD_NORMAL;
        }
    }

    public void setLoadStatus(LoadStatus loadStatus) {
        this.loadStatus = loadStatus;
    }

    public LoadStatus getLoadStatus() {
        return loadStatus;
    }
}
