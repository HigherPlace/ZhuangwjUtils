package com.zwj.customview.popupwindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;
import com.zwj.mycustomview.R;

import java.util.List;

public class ListPopupWindow extends BasePopupWindow{
	private ListView lv;
	private CommonAdapter<String> adapter;

	public ListPopupWindow(Context context, List<String> dataList) {
		super(LayoutInflater.from(context)
				.inflate(R.layout.pw_list, null), LayoutParams.MATCH_PARENT, (dataList !=null && dataList.size() > 4) ? AutoUtils.getPercentHeightSize(100)*5 : LayoutParams.WRAP_CONTENT,
				true);


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
		lv = (ListView) findViewById(R.id.lv);
	}

	@Override
	public void initEvents() {
	}

	@Override
	public void init() {
		
	}
	
	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		if(lv != null) {
			lv.setOnItemClickListener(onItemClickListener);
		}
	}

	/**
	 * 设置完数据会进行刷新
	 * @param dataList
     */
	public void setDatas(List<String> dataList) {
		adapter.setDatas(dataList);
	}
}
