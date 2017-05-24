package com.zwj.zhuangwjutils.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zwj.zhuangwjutils.MyApplication;
import com.zwj.zhuangwjutils.R;
import com.zwj.zhuangwjutils.activity.base.BaseAutoLayoutCommonActivity;
import com.zwj.zwjutils.ToastUtil;
import com.zwj.zwjutils.file.FileUtils;
import com.zwj.zwjutils.file.Param;
import com.zwj.zwjutils.file.WriteParam;

public class FileUtilsDemoActivity extends BaseAutoLayoutCommonActivity {
    private EditText et;
    private TextView tv;
    private Button btnWriteInternal, btnWriteExternal, btnWriteSdcard,
            btnReadInternal, btnReadExternal, btnReadSdcard, btnCopyFromAssets, btnCopy;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_file_utils_demo;
    }

    @Override
    protected void findViews() {
        et = getView(R.id.et);
        tv = getView(R.id.tv);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {

    }

    public void writeInternal(View view) {
        String content = et.getText().toString();
        if (!TextUtils.isEmpty(content)) {
            WriteParam param = new WriteParam();
            param.setDatas(content.getBytes())
                    .setContext(mContext)
                    .setFileName("xxx.txt");
            if (FileUtils.saveFile(param)) {
                ToastUtil.toast(MyApplication.getGlobalContext(), "写入成功");
            } else {
                ToastUtil.toast(MyApplication.getGlobalContext(), "写入失败");
            }
        } else {
            ToastUtil.toast(MyApplication.getGlobalContext(), "请输入内容");
        }
    }

    public void writeExternal(View view) {

    }

    public void writeSdcard(View view) {

    }

    public void readInternal(View view) {
        Param param = new Param();
        param.setContext(mContext)
                .setFileName("xxx.txt");
        String content = FileUtils.loadContentFromFile(param);
        tv.setText(content);
    }

    public void readExternal(View view) {

    }

    public void readSdcard(View view) {

    }

    public void copyFromAssets(View view) {

    }

    public void copy(View view) {

    }

}
