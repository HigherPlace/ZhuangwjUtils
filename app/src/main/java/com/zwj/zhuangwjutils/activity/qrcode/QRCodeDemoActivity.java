package com.zwj.zhuangwjutils.activity.qrcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.google.zxing.WriterException;
import com.zwj.zhuangwjutils.MyApplication;
import com.zwj.zhuangwjutils.R;
import com.zwj.zwjutils.ToastUtil;
import com.zxing.encode.EncodeHandler;

public class QRCodeDemoActivity extends AppCompatActivity {
    private ImageView ivQrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        ivQrCode = (ImageView) findViewById(R.id.iv_qrcode);
    }

    /**
     * 生成二维码图片
     * @param view
     */
    public void createQRCode(View view) {

        Bitmap bitmap = null;
        try {
            bitmap = EncodeHandler.createQRCode("http://www.baidu.com", 800);
        } catch (WriterException e) {
            e.printStackTrace();
            bitmap = null;
        }

        if (bitmap == null) {
            ToastUtil.toast(MyApplication.getGlobalContext(), "二维码生成失败");
        } else {
            ivQrCode.setImageBitmap(bitmap);
        }
    }

    /**
     * 扫描二维码
     * @param view
     */
    public void ScanQRCode(View view) {
        startActivity(new Intent(this, CaptureActivity.class));
    }
}
