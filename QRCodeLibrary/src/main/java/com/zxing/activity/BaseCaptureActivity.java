package com.zxing.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.SurfaceHolder.Callback;

import com.google.zxing.Result;
import com.zxing.camera.CameraManager;
import com.zxing.view.ViewfinderView;

/**
 * Initial the camera
 * @author Ryan.Tang
 * @modifier Lemon
 * @use extends CaptureActivity并且在setContentView方法后调用init方法
 */
public abstract class BaseCaptureActivity extends Activity implements Callback {

	public abstract ViewfinderView getViewfinderView();

	public abstract Handler getHandler();

	public abstract CameraManager getCameraManager();

	public abstract void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor);

	public abstract void drawViewfinder();
}