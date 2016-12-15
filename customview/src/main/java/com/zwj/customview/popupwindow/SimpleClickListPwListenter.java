package com.zwj.customview.popupwindow;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by zwj on 2016/12/15.
 */

public class SimpleClickListPwListenter implements ListPopupWindow.OnPWItemClickListener {

    @Override
    public void onItemClick(ListPopupWindow listPopupWindow, AdapterView<?> parent, View view, int position, long id) {
        listPopupWindow.dismiss();
    }
}
