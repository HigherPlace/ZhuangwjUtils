package com.zwj.zwjutils.image;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;

import com.zwj.zwjutils.LogUtils;
import com.zwj.zwjutils.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 图像处理工具类
 * 
 * */
public class ImageUtil {

	/**
	 * 图片缩放
	 * 
	 * @param bitmap
	 * @param width
	 * @param height
	 * 
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
		if (bitmap == null) {
			return null;
		}
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width / w);
		float scaleHeight = ((float) height / h);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
		return newbmp;
	}

	/**
	 * 添加阴影
	 * 
	 * @param originalBitmap
	 * 
	 */
	public static Bitmap drawImageDropShadow(Bitmap originalBitmap, Context context) {

		BlurMaskFilter blurFilter = new BlurMaskFilter(1,
				BlurMaskFilter.Blur.NORMAL);
		Paint shadowPaint = new Paint();
		shadowPaint.setAlpha(50);

		shadowPaint.setColor(context.getResources().getColor(R.color.black));
		shadowPaint.setMaskFilter(blurFilter);

		int[] offsetXY = new int[2];
		Bitmap shadowBitmap = originalBitmap
				.extractAlpha(shadowPaint, offsetXY);

		Bitmap shadowImage32 = shadowBitmap.copy(Config.ARGB_8888, true);
		Canvas c = new Canvas(shadowImage32);
		c.drawBitmap(originalBitmap, offsetXY[0], offsetXY[1], null);

		return shadowImage32;
	}

	/**
	 * 转换图片成圆形
	 * 
	 * @param bitmap
	 *            传入Bitmap对象
	 * @return
	 */
	public static Bitmap getRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;

			left = 0;
			top = 0;
			right = width;
			bottom = width;

			height = width;

			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;

			float clip = (width - height) / 2;

			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;

			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right,
				(int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);

		paint.setAntiAlias(true);// 设置画笔无锯齿

		canvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas

		// 以下有两种方法画圆,drawRounRect和drawCircle
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);// 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。
		// canvas.drawCircle(roundPx, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
		canvas.drawBitmap(bitmap, src, dst, paint); // 以Mode.SRC_IN模式合并bitmap和已经draw了的Circle
		
		if (bitmap != null && bitmap.isRecycled()) {
			bitmap.recycle();
		}

		return output;
	}


	/**
	 * 将图片裁剪变成带圆边的圆形图片
	 *
	 * @param bitmap
	 *            原图片
	 * @param d
	 *            直径
	 * @param pad
	 *            边框大小
	 * @param color
	 *            边框颜色
	 * @return
	 */
	public static Bitmap getRoundBitmap(Bitmap bitmap, int d, int pad, int color) {
		if (bitmap == null) {
			return null;
		}
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		if (d == 0) {
			d = (width > height) ? height : width;
		}
		// 将图片变成圆角
		Bitmap roundBitmap = Bitmap.createBitmap(d, d, Config.ARGB_4444);
		Canvas canvas = new Canvas(roundBitmap);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		canvas.drawCircle(d / 2, d / 2, d / 2 - pad, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		if (width >= height) {
			canvas.drawBitmap(bitmap, new Rect(width / 2 - height / 2, 0, width
					/ 2 + height / 2, height), new Rect(0, 0, d, d), paint);
		} else {
			canvas.drawBitmap(bitmap, new Rect(0, height / 2 - width / 2,
					width, height / 2 + width / 2), new Rect(0, 0, d, d), paint);
		}
		Bitmap outBitmap = null;
		if (pad > 0) {
			// 将图片加圆边
			outBitmap = Bitmap.createBitmap(d, d, Config.ARGB_4444);
			canvas = new Canvas(outBitmap);
			paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			if (color == 0) {
				color = Color.parseColor("#ffffffff");
			}
			paint.setColor(color);
			canvas.drawCircle(d / 2, d / 2, d / 2, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_OVER));
			canvas.drawBitmap(roundBitmap, 0, 0, paint);
		}
		bitmap.recycle();
		bitmap = null;
		if (pad > 0) {
			roundBitmap.recycle();
			roundBitmap = null;
			return outBitmap;
		} else {
			return roundBitmap;
		}
	}

	/**
	 * 圆角图片
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = 12;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;

	}

	/***
	 * 设置图片倒影
	 * 
	 * @param originalBitmap
	 * @return
	 */
	public static Bitmap createReflectedImage(Bitmap originalBitmap) {
		// 图片与倒影间隔距离
		final int reflectionGap = 4;

		// 图片的宽度
		int width = originalBitmap.getWidth();
		// 图片的高度
		int height = originalBitmap.getHeight();

		Matrix matrix = new Matrix();
		// 图片缩放，x轴变为原来的1倍，y轴为-1倍,实现图片的反转
		matrix.preScale(1, -1);
		// 创建反转后的图片Bitmap对象，图片高是原图的一半。
		Bitmap reflectionBitmap = Bitmap.createBitmap(originalBitmap, 0,
				height / 2, width, height / 2, matrix, false);
		// 创建标准的Bitmap对象，宽和原图一致，高是原图的1.5倍。
		Bitmap withReflectionBitmap = Bitmap.createBitmap(width, (height
				+ height / 2 + reflectionGap), Config.ARGB_8888);

		// 构造函数传入Bitmap对象，为了在图片上画图
		Canvas canvas = new Canvas(withReflectionBitmap);
		// 画原始图片
		canvas.drawBitmap(originalBitmap, 0, 0, null);

		// 画间隔矩形
		Paint defaultPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);

		// 画倒影图片
		canvas.drawBitmap(reflectionBitmap, 0, height + reflectionGap, null);

		// 实现倒影效果
		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0,
				originalBitmap.getHeight(), 0,
				withReflectionBitmap.getHeight(), 0x70ffffff, 0x00ffffff,
				TileMode.MIRROR);
		paint.setShader(shader);
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));

		// 覆盖效果
		canvas.drawRect(0, height, width, withReflectionBitmap.getHeight(),
				paint);

		return withReflectionBitmap;
	}
	
	/**
     *  改变图片
     * @param bm
     * @param hue   色调
     * @param saturation    饱和度
     * @param lum   亮度
     * @return
     */
    public static Bitmap handleImageEffect(Bitmap bm, float hue, float saturation, float lum) {
        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        ColorMatrix hueMatrix = new ColorMatrix();
        hueMatrix.setRotate(0, hue);
        hueMatrix.setRotate(1, hue);
        hueMatrix.setRotate(2, hue);

        ColorMatrix saturationMatrix = new ColorMatrix();
        saturationMatrix.setSaturation(saturation);

        ColorMatrix lumMatrix = new ColorMatrix();
        lumMatrix.setScale(lum, lum, lum, 1);

        ColorMatrix imageMatrix = new ColorMatrix();
        imageMatrix.postConcat(hueMatrix);
        imageMatrix.postConcat(saturationMatrix);
        imageMatrix.postConcat(lumMatrix);

        paint.setColorFilter(new ColorMatrixColorFilter(imageMatrix));
        canvas.drawBitmap(bm, 0, 0, paint);

        return bmp;
    }

    /**
     * 底片效果
     * @param bm
     * @return
     */
    public static Bitmap handleImageNegative(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        int color;
        int r, g, b, a;

        Bitmap bmp = Bitmap.createBitmap(width, height
                , Config.ARGB_8888);

        int[] oldPx = new int[width * height];
        int[] newPx = new int[width * height];
        bm.getPixels(oldPx, 0, width, 0, 0, width, height);

        for (int i = 0; i < width * height; i++) {
            color = oldPx[i];
            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);
            a = Color.alpha(color);

            r = 255 - r;
            g = 255 - g;
            b = 255 - b;

            if (r > 255) {
                r = 255;
            } else if (r < 0) {
                r = 0;
            }
            if (g > 255) {
                g = 255;
            } else if (g < 0) {
                g = 0;
            }
            if (b > 255) {
                b = 255;
            } else if (b < 0) {
                b = 0;
            }
            newPx[i] = Color.argb(a, r, g, b);
        }
        bmp.setPixels(newPx, 0, width, 0, 0, width, height);
        return bmp;
    }

    /**
     * 老照片效果
     * @param bm
     * @return
     */
    public static Bitmap handleImagePixelsOldPhoto(Bitmap bm) {
        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(),
                Config.ARGB_8888);
        int width = bm.getWidth();
        int height = bm.getHeight();
        int color = 0;
        int r, g, b, a, r1, g1, b1;

        int[] oldPx = new int[width * height];
        int[] newPx = new int[width * height];

        bm.getPixels(oldPx, 0, bm.getWidth(), 0, 0, width, height);
        for (int i = 0; i < width * height; i++) {
            color = oldPx[i];
            a = Color.alpha(color);
            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);

            r1 = (int) (0.393 * r + 0.769 * g + 0.189 * b);
            g1 = (int) (0.349 * r + 0.686 * g + 0.168 * b);
            b1 = (int) (0.272 * r + 0.534 * g + 0.131 * b);

            if (r1 > 255) {
                r1 = 255;
            }
            if (g1 > 255) {
                g1 = 255;
            }
            if (b1 > 255) {
                b1 = 255;
            }

            newPx[i] = Color.argb(a, r1, g1, b1);
        }
        bmp.setPixels(newPx, 0, width, 0, 0, width, height);
        return bmp;
    }

    /**
     * 浮雕效果
     * @param bm
     * @return
     */
    public static Bitmap handleImagePixelsRelief(Bitmap bm) {
        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(),
                Config.ARGB_8888);
        int width = bm.getWidth();
        int height = bm.getHeight();
        int color = 0, colorBefore = 0;
        int a, r, g, b;
        int r1, g1, b1;

        int[] oldPx = new int[width * height];
        int[] newPx = new int[width * height];

        bm.getPixels(oldPx, 0, bm.getWidth(), 0, 0, width, height);
        for (int i = 1; i < width * height; i++) {
            colorBefore = oldPx[i - 1];
            a = Color.alpha(colorBefore);
            r = Color.red(colorBefore);
            g = Color.green(colorBefore);
            b = Color.blue(colorBefore);

            color = oldPx[i];
            r1 = Color.red(color);
            g1 = Color.green(color);
            b1 = Color.blue(color);

            r = (r - r1 + 127);
            g = (g - g1 + 127);
            b = (b - b1 + 127);
            if (r > 255) {
                r = 255;
            }
            if (g > 255) {
                g = 255;
            }
            if (b > 255) {
                b = 255;
            }
            newPx[i] = Color.argb(a, r, g, b);
        }
        bmp.setPixels(newPx, 0, width, 0, 0, width, height);
        return bmp;
    }

	/**
	 * Drawable转为Bitmap
	 *
	 * @param drawable
	 * @return bitmap
	 * */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		Bitmap bitmap = null;

		try {
			bitmap = Bitmap
					.createBitmap(
							drawable.getIntrinsicWidth(),
							drawable.getIntrinsicHeight(),
							drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
									: Config.RGB_565);
			Canvas canvas = new Canvas(bitmap);
			// canvas.setBitmap(bitmap);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight());
			drawable.draw(canvas);
		} catch (Exception e) {
		}

		return bitmap;
	}

	/**
	 * 从文件中读取bitmap
	 *
	 * @param path
	 *            文件绝对路径
	 * @return
	 */
	public static Bitmap getBitmapFromFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return null;
		}
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		opt.inPreferredConfig = Config.RGB_565;
		try {
			return BitmapFactory.decodeFile(path, opt);
		} catch (OutOfMemoryError error1) {
			System.gc();
			Thread.yield();
			try {
				return BitmapFactory.decodeFile(path, opt);
			} catch (OutOfMemoryError error2) {
				return null;
			}
		}
	}

	/**
	 * 从assets文件中读取图片
	 *
	 * @param fileName
	 * @param context
	 * @return
	 */
	public static Bitmap getBmpFromAssetsFile(String fileName, Context context) {
		Bitmap image = null;
		AssetManager am = context.getResources().getAssets();
		try {
			InputStream is = am.open(fileName);
			image = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}


	/**
	 * 获取一个bitmap在内存中所占的大小
	 * @param image
	 * @return
	 */
	@SuppressLint("NewApi")
	private static int getSize(Bitmap image){
		int size=0;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
			size = image.getAllocationByteCount();
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
			size = image.getByteCount();
		} else {
			size = image.getRowBytes() * image.getHeight();
		}
		return size;
	}

	/**
	 * 压缩bitmap
	 *
	 * @param bitmap
	 * @return
	 */
	public static byte[] compressBitmap(Bitmap bitmap) {
		int quality = 100;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos);
		LogUtils.e("BitmapUtil",  "压缩前图片的大小是--------->"+bos.toByteArray().length / 1024+"kb");
//		FileUtils.saveFile2Files(MyApplication.getGlobalContext(), "test.png",Context.MODE_PRIVATE, bos.toByteArray());
		while (bos.toByteArray().length / 1024 > 100) {
			// >200kb就进行压缩
			bos.reset();
			quality -= 10;
			LogUtils.e("BitmapUtil", quality+"");

			bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos);
			if (quality < 30) {
				break;
			}
		}
		LogUtils.e("BitmapUtil", "压缩后图片的大小是--------->"+bos.toByteArray().length / 1024+"kb");
		return bos.toByteArray();
	}


	public static int compressBitmapToStream(Bitmap image,OutputStream stream){
		if(image==null || stream==null)return 0;
		try {
			Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
			int size = getSize(image);
			Log.i("Alex","存入内寸的bitmap大小是"+(size>>10)+" KB 宽度是"+image.getWidth()+" 高度是"+image.getHeight());
			int quality = getQuality(size);//根据图像的大小得到合适的有损压缩质量
			Log.i("Alex","目前适用的有损压缩率是"+quality);
			long startTime = System.currentTimeMillis();
			image.compress(format, quality, stream);//压缩文件并且输出
			if (image != null) {
				image.recycle();//此处把bitmap从内存中移除
				image = null;
			}
			Log.i("Alex","压缩图片并且存储的耗时"+(System.currentTimeMillis()-startTime));
			return size;
		}catch (Exception e){
			Log.i("Alex","压缩图片出现异常",e);
		}
		return 0;
	}

	/**
	 * 根据图像的大小得到合适的有损压缩质量，因为此时传入的bitmap大小已经比较合适了，靠近1000*1000，所以根据其内存大小进行质量压缩
	 * @param size
	 * @return
	 */
	private static int getQuality(int size){
		int mb=size>>20;//除以2的20次方，也就是m
		int kb = size>>10;
		Log.i("Alex","准备按照图像大小计算压缩质量，大小是"+kb+"KB,兆数是"+mb);
		if(mb>70){
			return 17;
		}else if(mb>50){
			return 20;
		}else if(mb>40){
			return 25;
		}else if(mb>20){
			return 40;
		}else if(mb>10){
			return 60;
		}else if(mb>3){//目标压缩大小 100k，这里可根据实际情况来判断
			return 60;
		}else if(mb>=2){
			return 60;
		}else if(kb > 1500){
			return 70;
		}else if(kb > 1000){
			return 80;
		}else if(kb>500){
			return 85;
		}else if(kb>100){
			return 90;
		}
		else{
			return 100;
		}
	}
}
