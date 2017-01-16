package com.zwj.zwjutils.image;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.zwj.zwjutils.R;


/**
 * Created by Administrator on 2016/6/2.
 */
public class ImageBuilder {
    /**
     * 加载图片的模式
     */
    public static enum LoadMode {
        /** 网络加载 */
        URL,
        /** 加载图片文件*/
        FILE,
        /** 从assets文件夹中加载图片 */
        ASSETS,
        /** 从drawable文件夹中加载 */
        DRAWABLE
    }

    private Context context;
    private Fragment fragment;
    private ImageView iv;
    private String url;
    private int drawableId;
    private boolean isDrawabId; // true,加载drawable里的文件
    private LoadMode loadMode;      // 图片加载模式
    private boolean isCircle;       // 是否要裁切成圆形
    private int defaultImageId = R.drawable.pictures_no;     // 默认占位图片
    private boolean noDefault;      // true,不需要默认图片
    /**
     * 图片缩放模式
     */
    private ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER_CROP;
//    /** true,滑动不加载图片 */
//    private boolean isPauseOnSrcoll;

    public ImageBuilder(Context context, ImageView iv, String url, LoadMode loadMode) {
        this.context = context;
        this.iv = iv;
        this.url = url;
        this.loadMode = loadMode;
    }

    public ImageBuilder(Fragment fragment, ImageView iv, String url, LoadMode loadMode) {
        this.fragment = fragment;
        this.iv = iv;
        this.url = url;
        this.loadMode = loadMode;
    }

    public ImageBuilder(Context context, Fragment fragment, ImageView iv, String url, int drawableId, boolean isDrawabId, LoadMode loadMode, boolean isCircle, int defaultImageId, boolean noDefault, ImageView.ScaleType scaleType) {
        this.context = context;
        this.fragment = fragment;
        this.iv = iv;
        this.url = url;
        this.drawableId = drawableId;
        this.isDrawabId = isDrawabId;
        this.loadMode = loadMode;
        this.isCircle = isCircle;
        this.defaultImageId = defaultImageId;
        this.noDefault = noDefault;
        this.scaleType = scaleType;
    }

    public Context getContext() {
        return context;
    }

    public ImageBuilder setContext(Context context) {
        this.context = context;
        return this;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public ImageBuilder setFragment(Fragment fragment) {
        this.fragment = fragment;
        return this;
    }

    public ImageView getIv() {
        return iv;
    }

    public ImageBuilder setIv(ImageView iv) {
        this.iv = iv;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ImageBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public ImageBuilder setDrawableId(int drawableId) {
        this.drawableId = drawableId;
        return this;
    }

    public LoadMode getLoadMode() {
        return loadMode;
    }

    public ImageBuilder setLoadMode(LoadMode loadMode) {
        this.loadMode = loadMode;
        return this;
    }

    public boolean isCircle() {
        return isCircle;
    }

    public ImageBuilder setCircle(boolean circle) {
        isCircle = circle;
        return this;
    }

    public int getDefaultImageId() {
        return defaultImageId;
    }

    public ImageBuilder setDefaultImageId(int defaultImageId) {
        this.defaultImageId = defaultImageId;
        return this;
    }

    public boolean isNoDefault() {
        return noDefault;
    }

    public ImageBuilder setNoDefault(boolean noDefault) {
        this.noDefault = noDefault;
        return this;
    }

    public ImageView.ScaleType getScaleType() {
        return scaleType;
    }

    public ImageBuilder setScaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
        return this;
    }

    public boolean isDrawabId() {
        return isDrawabId;
    }

    public ImageBuilder setDrawabId(boolean drawabId) {
        isDrawabId = drawabId;
        return this;
    }

    public void build() {
        load(this);
    }

    public static void load(final ImageBuilder imageBuilder) {

        if (imageBuilder.getIv() == null) {
            return;
        }

        imageBuilder.getIv().setScaleType(imageBuilder.getScaleType());

        RequestManager requestManager = null;
        if (imageBuilder.getContext() != null) {
            if (imageBuilder.getContext() instanceof Activity) {
                Activity activity = (Activity) imageBuilder.getContext();
                requestManager = Glide.with(activity);
            } else {
                requestManager = Glide.with(imageBuilder.getContext());
            }
        } else if (imageBuilder.getFragment() != null) {
            requestManager = Glide.with(imageBuilder.getFragment());
        } else {
            // ImageLoader.getInstance().displayImage(url, iv);
            return;
        }

        if (requestManager != null) {
            DrawableTypeRequest drawableTypeRequest = null;

            if (imageBuilder.isDrawabId()) {
                drawableTypeRequest = requestManager.load(imageBuilder.getDrawableId());
            } else {
                if (!TextUtils.isEmpty(imageBuilder.getUrl())) {
                    StringBuilder sbUrl = new StringBuilder();
                    switch (imageBuilder.getLoadMode()) {
                        case URL:

                            break;

                        case FILE:
                            sbUrl.append("file://");
                            break;

                        case ASSETS:
                            sbUrl.append("file:///android_asset");
                            break;

                        case DRAWABLE:
                            break;
                    }
                    sbUrl.append(imageBuilder.getUrl());
                    drawableTypeRequest = requestManager.load(sbUrl.toString());
                } else {
                    imageBuilder.setNoDefault(true);
                    drawableTypeRequest = requestManager.load(imageBuilder.getDefaultImageId());
                }
            }

            if (drawableTypeRequest != null) {
                if (imageBuilder.isCircle()) {
                    if (imageBuilder.isNoDefault()) {
                        drawableTypeRequest.asBitmap()
                                .into(new BitmapImageViewTarget(imageBuilder.getIv()) {
                                    @Override
                                    protected void setResource(
                                            Bitmap resource) {
                                        // 圆形
                                        // iv.setImageBitmap(BitmapUtil.getRoundBitmap(
                                        // resource, 0, 30,
                                        // Color.parseColor("#ffeeeeee")));
                                        imageBuilder.getIv().setImageBitmap(resource);
                                    }
                                });
                    } else {
                        drawableTypeRequest.asBitmap()
                                .placeholder(imageBuilder.getDefaultImageId()).centerCrop()
                                .into(new BitmapImageViewTarget(imageBuilder.getIv()) {
                                    @Override
                                    protected void setResource(
                                            Bitmap resource) {
                                        // 圆形
                                        // iv.setImageBitmap(BitmapUtil.getRoundBitmap(
                                        // resource, 0, 30,
                                        // Color.parseColor("#ffeeeeee")));
                                        imageBuilder.getIv().setImageBitmap(resource);
                                    }
                                });
                    }
                } else {
                    if (imageBuilder.isNoDefault()) {
                        drawableTypeRequest.into(imageBuilder.getIv());
                    } else {
                        drawableTypeRequest
                                .placeholder(imageBuilder.getDefaultImageId()).into(imageBuilder.getIv());
                    }
                }
            }
        }
    }

    /**
     * 设置滚动不加载图片的监听
     */
    public static void setOnSlidePauseLoadListener(Context context,
                                                   AbsListView absListView) {
        absListView.setOnScrollListener(getOnSlidePauseLoadListener(context));
    }

    /**
     * 设置滑动不加载图片的监听
     * @param context
     */
    public static void setOnSlidePauseLoadListener(final Context context, RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                setOnSlidePauseLoadImage(context, newState);
            }
        });
    }

    /**
     * 设置滑动不加载图片
     * @param context
     * @param newState
     */
    public static void setOnSlidePauseLoadImage(Context context, int newState) {
        switch (newState) {
            case RecyclerView.SCROLL_STATE_IDLE:
                Glide.with(context).resumeRequests();
                break;

            default:
                Glide.with(context).pauseRequests();
                break;
        }
    }

    /**
     * 获取滚动不加载图片的监听
     *
     * @return
     */
    public static AbsListView.OnScrollListener getOnSlidePauseLoadListener(final Context context) {
        return new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_IDLE:
                        Glide.with(context).resumeRequests();
                        break;

                    default:
                        Glide.with(context).pauseRequests();
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

            }
        };
    }
}
