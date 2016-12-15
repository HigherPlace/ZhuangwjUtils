package com.zwj.customview.popupwindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zwj.mycustomview.R;
import com.zwj.zwjutils.DensityUtils;

import java.util.List;

public class ListPopupWindow extends BasePopupWindow {
    private static final int ITEM_HEIGHT = 52;    // 每个item的高度，单位dp
    protected FrameLayout flRoot;
    protected ListView lv;
    protected CommonAdapter<String> adapter;
//	protected List<String> dataList;


    /**
     * 点击item的回调接口
     */
    public interface OnPWItemClickListener {
        void onItemClick(ListPopupWindow listPopupWindow, AdapterView<?> parent, View view, int position, long id);
    }

    private OnPWItemClickListener onPWItemClickListener = new SimpleClickListPwListenter();

    public ListPopupWindow(Context context, List<String> dataList) {
        super(LayoutInflater.from(context)
                        .inflate(R.layout.pw_list, null), LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
                true);

        // 注意：这里会先调用父类构造法，会先依次调用initViews()、initEvents()、init()方法
        // 若将下面的代码放在init()方法中，此时data为null，会导致报NullPointerException
//		this.dataList = dataList;
        if (dataList != null && dataList.size() > 5) {
            FrameLayout.LayoutParams lps = (FrameLayout.LayoutParams) lv.getLayoutParams();
            lps.height = DensityUtils.dp2px(context, ITEM_HEIGHT * 5);
        }

        adapter = new CommonAdapter<String>(context, R.layout.item_lv_pw, dataList) {
            @Override
            protected void convert(ViewHolder holder, String item, int position) {
                holder.setText(R.id.tv, item);
            }
        };
        lv.setAdapter(adapter);
    }

    @Override
    protected void beforeInitWeNeedSomeParams(Object... params) {

    }

    @Override
    public void initViews() {
        flRoot = (FrameLayout) findViewById(R.id.fl_root);
        lv = (ListView) findViewById(R.id.lv);
    }

    @Override
    public void initEvents() {
        flRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onPWItemClickListener != null) {
                    onPWItemClickListener.onItemClick(ListPopupWindow.this, parent, view, position, id);
                }
            }
        });
    }

    @Override
    public void init() {
    }


    /**
     * 设置完数据会进行刷新
     *
     * @param dataList
     */
    public void setDatas(List<String> dataList) {
        adapter.setDatas(dataList);
    }

    public void setOnPWItemClickListener(OnPWItemClickListener onPWItemClickListener) {
        this.onPWItemClickListener = onPWItemClickListener;
    }
}
