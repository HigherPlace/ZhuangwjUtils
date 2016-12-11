package com.zwj.zhuangwjutils.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zwj.zhuangwjutils.MyApplication;
import com.zwj.zhuangwjutils.R;
import com.zwj.zhuangwjutils.activity.base.BaseActivity;
import com.zwj.zwjutils.encrypt.RSAUtils;

import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 加密解密demo
 */
public class EncryptionDemoActivity extends BaseActivity {
    private EditText etText;
    private TextView tvEncrypt, tvDencrypt;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_encryption_demo;
    }

    @Override
    protected void findViews() {
        etText = getView(R.id.et);
        tvEncrypt = getView(R.id.tv_encrypt);
        tvDencrypt = getView(R.id.tv_dencrypt);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {

    }

    public void encrypt(View view) {
        String text = etText.getText().toString();
        if(!TextUtils.isEmpty(text)) {
            InputStream inPublic;
            try {
                inPublic = MyApplication.getGlobalContext().getResources()
                        .getAssets().open("key/rsa_public_key.pem");
                PublicKey publicKey = RSAUtils.loadPublicKey(inPublic);

                // 加密
                byte[] encryptByte = RSAUtils.encryptData(text.getBytes(),
                        publicKey);
                String afterencrypt = Base64.encodeToString(encryptByte,
                        Base64.DEFAULT);

                tvEncrypt.setText(afterencrypt);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void dencrypt(View view) {
        String encryptStr = tvEncrypt.getText().toString().trim();
        if (!TextUtils.isEmpty(encryptStr)) {
            InputStream inPrivate = null;
            try {
                inPrivate = MyApplication.getGlobalContext()
                        .getResources().getAssets()
                        .open("key/pkcs8_rsa_private_key.pem");

                PrivateKey privateKey = RSAUtils.loadPrivateKey(inPrivate);

                byte[] decryptByte = RSAUtils.decryptData(
                        Base64.decode(encryptStr, Base64.DEFAULT),
                        privateKey);

                String decryptStr = new String(decryptByte);
                tvDencrypt.setText(decryptStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
