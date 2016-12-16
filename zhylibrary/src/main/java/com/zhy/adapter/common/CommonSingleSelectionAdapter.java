package com.zhy.adapter.common;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zwj.autolibrary.R;

import java.util.List;

/**
 * 单选通用Adapter，显示选中的图标id为iv
 * 其他操作就重写convert，记得调用super.convert（）
 * <p>
 * 若需要改变文本的颜色，textview的id设为tv
 *
 * @author zwj
 */
public class CommonSingleSelectionAdapter<T> extends CommonAdapter<T> {
    public static final int NO_IMAGE = -100;  // 表示没有图片
    public static final int NO_CHECKED = -101;  // 表示未选中
    public static final int DEFAULT_IMAGE = 2002;  // 使用默认图标
    public static final int DEFAULT_LAYOUT_ID = R.layout.item_common_single_selection;
    protected int currentCheckedPosition = 0; // 当前被选中的item位置,NO_CHECKED 为全未选中
    private int normalDrawableId = R.drawable.checkbox_default;        // 正常时显示的图片id
    private int selectedDrawableId = R.drawable.checkbox_checked;        // 选中时显示的图片id
    private int textNormalColor;
    private int textSelectedColor;
    private boolean textNeedChangeStatus;    // true,文字也要跟着变色
    private boolean isDefaultLayout; // 是否使用默认布局
    private int lastCheckedPostion = 0;     // 上一个选中的下标


    public CommonSingleSelectionAdapter(Context context, int layoutId, List<T> datas) {
        super(context, layoutId, datas);
    }

    public CommonSingleSelectionAdapter(Context context, List<T> datas) {
        this(context, DEFAULT_LAYOUT_ID, datas);
    }

    public CommonSingleSelectionAdapter(Context context, int layoutId, List<T> datas,
                                        int normalDrawableId, int selectedDrawableId) {
        this(context, layoutId, datas);

        if(layoutId == DEFAULT_LAYOUT_ID) {
            isDefaultLayout =true;
        }

        if(normalDrawableId != DEFAULT_IMAGE) {
            this.normalDrawableId = normalDrawableId;
        }

        if(selectedDrawableId != DEFAULT_IMAGE) {
            this.selectedDrawableId = selectedDrawableId;
        }
    }

    public CommonSingleSelectionAdapter(Context context, int layoutId, List<T> datas,
                                        int currentCheckedPosition) {
        this(context, layoutId, datas);
        this.currentCheckedPosition = currentCheckedPosition;
        lastCheckedPostion = currentCheckedPosition;
    }

    @Override
    protected void convert(final ViewHolder holder, final T item, final int position) {
        View itemView = holder.getConvertView();
        ImageView iv = holder.getView(R.id.iv);
        if (position == currentCheckedPosition) {
            if (iv != null) {
                if (normalDrawableId == NO_IMAGE) {
                    iv.setVisibility(View.VISIBLE);
                }
                iv.setImageResource(selectedDrawableId);

                if (textNeedChangeStatus) {
                    holder.setTextColor(R.id.tv, textSelectedColor);
                }
            }
        } else {
            if (iv != null) {
                if (normalDrawableId == NO_IMAGE) {
                    iv.setVisibility(View.GONE);
                } else {
                    iv.setImageResource(normalDrawableId);
                }

                if (textNeedChangeStatus) {
                    holder.setTextColor(R.id.tv, textNormalColor);
                }
            }
        }

        if(isDefaultLayout) {
            holder.setText(R.id.tv, item.toString());
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentCheckedPosition != position) {
                    currentCheckedPosition = position;
//                notifyDataSetChanged();
                    refresh();

                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(item, position);
                    }
                }
            }
        });
    }

    private void refresh() {
        notifyItemChanged(lastCheckedPostion);
        notifyItemChanged(currentCheckedPosition);
        lastCheckedPostion = currentCheckedPosition;
    }


    public interface OnItemClickListener<T> {
        /**
         * 点击item时回调
         */
        void onItemClick(T item, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public T getCurrentCheckedItem() {
        if (currentCheckedPosition != NO_CHECKED) {
            return mDatas.get(currentCheckedPosition);
        }

        return null;
    }

    public int getCurrentCheckedPosition() {
        return currentCheckedPosition;
    }

    public CommonSingleSelectionAdapter<T> setCurrentCheckedPosition(int currentCheckedPosition) {
        if (currentCheckedPosition >= mDatas.size()
                || currentCheckedPosition < 0) {
            this.currentCheckedPosition = 0;
        } else {
            if (this.currentCheckedPosition != currentCheckedPosition) {
                this.currentCheckedPosition = currentCheckedPosition;
            }
        }

//        notifyDataSetChanged();
        refresh();
        return this;
    }

    public int getNormalDrawableId() {
        return normalDrawableId;
    }

    public CommonSingleSelectionAdapter<T> setNormalDrawableId(int normalDrawableId) {
        this.normalDrawableId = normalDrawableId;
        return this;
    }

    public int getSelectedDrawableId() {
        return selectedDrawableId;
    }

    public CommonSingleSelectionAdapter<T> setSelectedDrawableId(int selectedDrawableId) {
        this.selectedDrawableId = selectedDrawableId;
        return this;
    }

    public OnItemClickListener getmOnItemClickListener() {
        return mOnItemClickListener;
    }

    public CommonSingleSelectionAdapter<T> setmOnItemClickListener(OnItemClickListener<T> mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
        return this;
    }

    public int getTextNormalColor() {
        return textNormalColor;
    }

    public CommonSingleSelectionAdapter<T> setTextNormalColor(int textNormalColor) {
        this.textNormalColor = textNormalColor;
        return this;
    }

    public int getTextSelectedColor() {
        return textSelectedColor;
    }

    public CommonSingleSelectionAdapter<T> setTextSelectedColor(int textSelectedColor) {
        this.textSelectedColor = textSelectedColor;
        return this;
    }

    public boolean isTextNeedChangeStatus() {
        return textNeedChangeStatus;
    }

    public CommonSingleSelectionAdapter<T> setTextNeedChangeStatus(boolean textNeedChangeStatus) {
        this.textNeedChangeStatus = textNeedChangeStatus;
        return this;
    }
}
