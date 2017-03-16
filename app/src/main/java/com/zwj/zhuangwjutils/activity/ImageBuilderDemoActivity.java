package com.zwj.zhuangwjutils.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.zwj.zhuangwjutils.R;
import com.zwj.zwjutils.image.ImageBuilder;

public class ImageBuilderDemoActivity extends AppCompatActivity {
    private ImageView iv;

    private static String imgUrl = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";
    private static String errorImgUrl = "http://xxxxx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_builder_demo);

        iv = (ImageView) findViewById(R.id.iv);

        new ImageBuilder(this, iv, imgUrl)
                .setRadius(30)
                .build();
    }
}
