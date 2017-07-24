package com.zwj.zhuangwjutils.activity.qrcode;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.google.zxing.client.result.ResultParser;
import com.zwj.zhuangwjutils.R;
import com.zwj.zwjutils.LogUtils;
import com.zxing.AmbientLightManager;
import com.zxing.BeepManager;
import com.zxing.FinishListener;
import com.zxing.InactivityTimer;
import com.zxing.IntentSource;
import com.zxing.activity.BaseCaptureActivity;
import com.zxing.camera.CameraManager;
import com.zxing.common.BitmapUtils;
import com.zxing.decode.BitmapDecoder;
import com.zxing.decode.CaptureActivityHandler;
import com.zxing.view.ViewfinderView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Map;

/**
 * Initial the camera
 *
 * @author Ryan.Tang
 * @modifier Lemon
 * @use extends CaptureActivity并且在setContentView方法后调用init方法
 */
public class CaptureActivity extends BaseCaptureActivity implements View.OnClickListener{
    private static final String TAG = CaptureActivity.class.getSimpleName();
    private Context mContext;
    private static final int REQUEST_CODE = 100;

    private static final int PARSE_BARCODE_FAIL = 300;
    private static final int PARSE_BARCODE_SUC = 200;
    public static final String SCANNER_QR_CODE_RESULT = "scanner_qr_code_result";
    /**
     * 是否有预览
     */
    private boolean hasSurface;

    /**
     * 活动监控器。如果手机没有连接电源线，那么当相机开启后如果一直处于不被使用状态则该服务会将当前activity关闭。
     * 活动监控器全程监控扫描活跃状态，与CaptureActivity生命周期相同.每一次扫描过后都会重置该监控，即重新倒计时。
     */
    private InactivityTimer inactivityTimer;

    /**
     * 声音震动管理器。如果扫描成功后可以播放一段音频，也可以震动提醒，可以通过配置来决定扫描成功后的行为。
     */
    private BeepManager beepManager;

    /**
     * 闪光灯调节器。自动检测环境光线强弱并决定是否开启闪光灯
     */
    private AmbientLightManager ambientLightManager;

    private CameraManager cameraManager;
    /**
     * 扫描区域
     */
    private ViewfinderView viewfinderView;

    private CaptureActivityHandler handler;

    private Result lastResult;

    private boolean isFlashlightOpen;

    /**
     * 【辅助解码的参数(用作MultiFormatReader的参数)】 编码类型，该参数告诉扫描器采用何种编码方式解码，即EAN-13，QR
     * Code等等 对应于DecodeHintType.POSSIBLE_FORMATS类型
     * 参考DecodeThread构造函数中如下代码：hints.put(DecodeHintType.POSSIBLE_FORMATS,
     * decodeFormats);
     */
    private Collection<BarcodeFormat> decodeFormats;

    /**
     * 【辅助解码的参数(用作MultiFormatReader的参数)】 该参数最终会传入MultiFormatReader，
     * 上面的decodeFormats和characterSet最终会先加入到decodeHints中 最终被设置到MultiFormatReader中
     * 参考DecodeHandler构造器中如下代码：multiFormatReader.setHints(hints);
     */
    private Map<DecodeHintType, ?> decodeHints;

    /**
     * 【辅助解码的参数(用作MultiFormatReader的参数)】 字符集，告诉扫描器该以何种字符集进行解码
     * 对应于DecodeHintType.CHARACTER_SET类型
     * 参考DecodeThread构造器如下代码：hints.put(DecodeHintType.CHARACTER_SET,
     * characterSet);
     */
    private String characterSet;

    private Result savedResultToShow;

    private IntentSource source;

    /**
     * 图片的路径
     */
    private String photoPath;

    private Handler mHandler = new MyHandler(this);

    static class MyHandler extends Handler {

        private WeakReference<Activity> activityReference;

        public MyHandler(Activity activity) {
            activityReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case PARSE_BARCODE_SUC: // 解析图片成功
                    Toast.makeText(activityReference.get(),
                            "解析成功，结果为：" + msg.obj, Toast.LENGTH_SHORT).show();
                    break;

                case PARSE_BARCODE_FAIL:// 解析图片失败

                    Toast.makeText(activityReference.get(), "解析图片失败",
                            Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }

            super.handleMessage(msg);
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.capture);
        mContext = CaptureActivity.this;
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);
        ambientLightManager = new AmbientLightManager(this);

        // 监听图片识别按钮
        findViewById(R.id.capture_scan_photo).setOnClickListener(this);

        findViewById(R.id.capture_flashlight).setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // 相机初始化的动作需要开启相机并测量屏幕大小，这些操作
        // 不建议放到onCreate中，因为如果在onCreate中加上首次启动展示帮助信息的代码的 话，
        // 会导致扫描窗口的尺寸计算有误的bug
        cameraManager = CameraManager.getInstance(getApplication());

        viewfinderView = (ViewfinderView) findViewById(R.id.capture_viewfinder_view);
        viewfinderView.setCameraManager(cameraManager);

        handler = null;
        lastResult = null;

        // 摄像头预览功能必须借助SurfaceView，因此也需要在一开始对其进行初始化
        // 如果需要了解SurfaceView的原理
        // 参考:http://blog.csdn.net/luoshengyang/article/details/8661317
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.capture_preview_view); // 预览
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            // 防止sdk8的设备初始化预览异常
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            surfaceHolder.addCallback(this);
        }

        // 加载声音配置，其实在BeemManager的构造器中也会调用该方法，即在onCreate的时候会调用一次
        beepManager.updatePrefs();

        // 启动闪光灯调节器
        ambientLightManager.start(cameraManager);

        // 恢复活动监控器
        inactivityTimer.onResume();

        source = IntentSource.NONE;
        decodeFormats = null;
        characterSet = null;
    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        ambientLightManager.stop();
        beepManager.close();

        // 关闭摄像头
        cameraManager.closeDriver();
        if (!hasSurface) {
            SurfaceView surfaceView = (SurfaceView) findViewById(R.id.capture_preview_view);
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }

        if (cameraManager.isOpen()) {
            Log.w(TAG,
                    "initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            if (handler == null) {
                handler = new CaptureActivityHandler(this, decodeFormats,
                        decodeHints, characterSet, cameraManager);
            }
            decodeOrStoreSavedBitmap(null, null);
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            // Barcode Scanner has seen crashes in the wild of this variety:
            // java.?lang.?RuntimeException: Fail to connect to camera service
            Log.w(TAG, "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }
    }

    /**
     * 向CaptureActivityHandler中发送消息，并展示扫描到的图像
     * @param bitmap
     * @param result
     */
    private void decodeOrStoreSavedBitmap(Bitmap bitmap, Result result) {
        if (handler == null) {
            savedResultToShow = result;
        } else {
            if (result != null) {
                savedResultToShow = result;
                Message message = Message.obtain(handler,
                        R.id.decode_succeeded, savedResultToShow);
                handler.sendMessage(message);
            }
            savedResultToShow = null;
        }
    }

    private void displayFrameworkBugMessageAndExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(getString(R.string.msg_camera_framework_bug));
        builder.setPositiveButton(R.string.button_ok, new FinishListener(this));
        builder.setOnCancelListener(new FinishListener(this));
        builder.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if ((source == IntentSource.NONE) && lastResult != null) { // 重新进行扫描
                    restartPreviewAfterDelay(0L);
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_FOCUS:
            case KeyEvent.KEYCODE_CAMERA:
                // Handle these events so they don't launch the Camera app
                return true;

            case KeyEvent.KEYCODE_VOLUME_UP:
                cameraManager.zoomIn();
                return true;

            case KeyEvent.KEYCODE_VOLUME_DOWN:
                cameraManager.zoomOut();
                return true;

        }
        return super.onKeyDown(keyCode, event);
    }

    public void restartPreviewAfterDelay(long delayMS) {
        if (handler != null) {
            handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
        }
        resetStatusView();
    }

    private void resetStatusView() {
        viewfinderView.setVisibility(View.VISIBLE);
        lastResult = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (resultCode == RESULT_OK) {
            final ProgressDialog progressDialog;
            switch (requestCode) {
                case REQUEST_CODE:

                    // 获取选中图片的路径
                    Cursor cursor = getContentResolver().query(
                            intent.getData(), null, null, null, null);
                    if (cursor.moveToFirst()) {
                        photoPath = cursor.getString(cursor
                                .getColumnIndex(MediaStore.Images.Media.DATA));
                    }
                    cursor.close();

                    progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("正在扫描...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    new Thread(new Runnable() {

                        @Override
                        public void run() {

                            Bitmap img = BitmapUtils
                                    .getCompressedBitmap(photoPath);

                            BitmapDecoder decoder = new BitmapDecoder(
                                    CaptureActivity.this);
                            Result result = decoder.getRawResult(img);

                            if (result != null) {
                                Message m = mHandler.obtainMessage();
                                m.what = PARSE_BARCODE_SUC;
                                m.obj = ResultParser.parseResult(result)
                                        .toString();
                                mHandler.sendMessage(m);
                            } else {
                                Message m = mHandler.obtainMessage();
                                m.what = PARSE_BARCODE_FAIL;
                                mHandler.sendMessage(m);
                            }

                            progressDialog.dismiss();
                        }
                    }).start();

                    break;

            }
        }
    }

    @Override
    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    @Override
    public Handler getHandler() {
        return handler;
    }

    @Override
    public CameraManager getCameraManager() {
        return cameraManager;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            Log.e(TAG,
                    "*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    @Override
    public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
        // 重新计时
        inactivityTimer.onActivity();
        lastResult = rawResult;

        // 把图片画到扫描框
        viewfinderView.drawResultBitmap(barcode);

        beepManager.playBeepSoundAndVibrate();
        String result = ResultParser.parseResult(rawResult).toString();
        handleScanCodeResult(result);
    }

    @Override
    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.capture_scan_photo:   // 打开手机中的相册
                Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT); // "android.intent.action.GET_CONTENT"
                innerIntent.setType("image/*");
                Intent wrapperIntent = Intent.createChooser(innerIntent,
                        "选择二维码图片");
                this.startActivityForResult(wrapperIntent, REQUEST_CODE);
                break;

            case R.id.capture_flashlight:
                if (isFlashlightOpen) {
                    cameraManager.setTorch(false); // 关闭闪光灯
                    isFlashlightOpen = false;
                } else {
                    cameraManager.setTorch(true); // 打开闪光灯
                    isFlashlightOpen = true;
                }
                break;

        }
    }

    private void handleScanCodeResult(String resultString) {
        if (TextUtils.isEmpty(resultString)) {
//            String url3 = "https://qrcode.iqcloud.cc:18588/scanFail.html?message=" + "扫码失败";
//            startActivity(WebActivity.newIntent(mContext, url3, "扫码结果"));
            LogUtils.e(TAG, "扫描失败");
            finish();
            return;

        }

        LogUtils.e(TAG, "扫描成功 ----> "+resultString);
        finish();
//        if (resultString.contains("iqcloud")) {
//            //是我们的二维码
//            if (resultString.contains("tk=")) {
//                //需要拼接字符串
//                handleMeetRoomQrCode(resultString);//处理会议室二维码扫描
//            } else if (resultString.startsWith("wyiqcloud:")) {
//                //自定义的二维码格式
//                String[] array = resultString.split(":");
//                String content64 = array[1];
//                //解码
//                String content = new String(Base64.decode(content64, Base64.DEFAULT));
//                QrCodeBean bean = JsonUtil.getObject(content, QrCodeBean.class);
//                switch (bean.getType()) {
//                    case 1://添加好友
//                        if (!TextUtils.isEmpty(bean.getHeadUrl())) {
//                            Friend friend = new Friend(bean.getAccountId(),
//                                    bean.getName(), Uri.parse(bean.getHeadUrl()));
//                            Intent intent = new Intent(mContext, UserDetailActivity.class);
//                            intent.putExtra("type", CLICK_CONTACT_FRAGMENT_FRIEND);
//                            intent.putExtra("friend", friend);
//                            finish();
//                            mContext.startActivity(intent);
//                        } else {
//                            String url2 = "https://qrcode.iqcloud.cc:18588/scanFail.html?message=二维码内容异常";
//                            startActivity(WebActivity.newIntent(mContext, url2, "扫码结果"));
////                            finish();
//                            finish();
//                        }
//                        break;
//                    default:
//                        String url2 = "https://qrcode.iqcloud.cc:18588/scanFail.html?message=当前版本过低，请尽快升级新版本";
//                        startActivity(WebActivity.newIntent(mContext, url2, "扫码结果"));
//                        finish();
//                        break;
//                }
//            } else {
//                String url2 = "https://qrcode.iqcloud.cc:18588/scanFail.html?message=暂不支持扫描此类型二维码";
//                startActivity(WebActivity.newIntent(mContext, url2, "扫码结果"));
//                finish();
//            }
//        } else {
//            String url2 = "https://qrcode.iqcloud.cc:18588/scanFail.html?message=暂不支持扫描此类型二维码";
//            startActivity(WebActivity.newIntent(mContext, url2, "扫码结果"));
//            finish();
//        }
    }
}