package com.zwj.zwjutils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.net.Uri;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.ImageView;

import java.text.DecimalFormat;

public class CommonUtil {

    private CommonUtil() {
    }


    /**
     * 复制内容到剪贴板
     *
     * @param content
     */
//    public static void copyToClipBoard(String content) {
//        ClipboardManager cm = (ClipboardManager) MyApplication.getGlobalContext().getSystemService(Context.CLIPBOARD_SERVICE);
//        cm.setText(content);
//        ToastUtil.toast("复制成功");
//    }

    /**
     * 纠正url转json的时候出现错误
     */
    public static String correctUrlString(String content) {
        return content.replace("\\/", "/");
    }

    /**
     * 点击ImageView的时候添加压黑效果
     *
     * @param iv
     */
    public static void addLayerForImageview(ImageView iv) {
        iv.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        changeLight((ImageView) view, 0);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        changeLight((ImageView) view, -80);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        changeLight((ImageView) view, 0);
                        break;
                    default:
                        changeLight((ImageView) view, 0);
                        break;
                }
                return false;
            }
        });
    }

    private static void changeLight(ImageView imageview, int brightness) {
        ColorMatrix matrix = new ColorMatrix();
        matrix.set(new float[]{1, 0, 0, 0, brightness, 0, 1, 0, 0,
                brightness, 0, 0, 1, 0, brightness, 0, 0, 0, 1, 0});
        imageview.setColorFilter(new ColorMatrixColorFilter(matrix));
    }

    /**
     * 拨打电话
     * @param activity
     * @param phoneNum
     */
    public static void call(Activity activity, String phoneNum) {
//        MobclickAgent.onEvent(activity, Constant.UM_EVENT_DIAL); // 统计拨打电话
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        activity.startActivity(intent);
    }

    /***
     * 隐藏手机号码中间四位
     */
    public static String hidePhoneNum(String phone) {
        if (phone.length() > 7) {
            return phone.substring(0, 3) + "****"
                    + phone.substring(7, phone.length());
        }
        return phone;
    }

    /**
     * 半角转换为全角 ，用于解决textview自动换行问题
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

//    /**
//     * 保留两位小数,强转成double之后如果小数点之后是".00"就会变成".0"
//     *
//     * @param number
//     * @return
//     */
//    public static double keepTwoPlacesOfDecimal(double number) {
//        return keepPlacesOfDecimal(number, 2);
//    }
//
//    /**
//     * 保留两位小数,强转成double之后如果小数点之后是".00"就会变成".0"
//     *
//     * @param number
//     * @param places 保留的小数位数
//     * @return
//     */
//    public static double keepPlacesOfDecimal(double number, int places) {
//        BigDecimal b = new BigDecimal(number);
//        return b.setScale(places, BigDecimal.ROUND_HALF_UP).doubleValue();
//    }

    public static String fromaterTwoPlacesOfDecimal(double number) {
        if (number != 0) {
            DecimalFormat df = new DecimalFormat("#.00");
            return df.format(number);
        } else {
            return "0";
        }
    }

    public static void showView(View view, boolean isShow) {
        if (view != null) {
            if (isShow) {
                if (view.getVisibility() == View.GONE
                        || view.getVisibility() == View.INVISIBLE) {
                    view.setVisibility(View.VISIBLE);
                }
            } else {
                if (view.getVisibility() == View.VISIBLE) {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }

    /**
     * 获取当前应用的信息
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo info = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是当前类的包名，0代表是获取版本信息
            info = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 把编号的字符串格式化为每四位用空格隔开
     *
     * @param numberStr
     */
    public static String formatNumberDiviveFour(String numberStr) {
        if(TextUtils.isEmpty(numberStr)) {
            return null;
        }

        StringBuilder sbCarNo = new StringBuilder();
        int count = numberStr.length() / 4;
        int index = numberStr.length() % 4;

        if (index != 0) {
            sbCarNo.append(numberStr.substring(0, index));
        }

        for (int i = 0; i < count; i++) {
            if (sbCarNo.length() > 0) {
                sbCarNo.append("  ");
            }

            sbCarNo.append(numberStr.substring(index + i * 4, index + (i + 1)
                    * 4));
        }

        return sbCarNo.toString();
    }


    /**
     * 是否可以往上滚动
     */
    public static boolean canChildScrollUp(View view) {
        if (view == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 14) {
            if (view instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) view;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(view, -1) || view.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(view, -1);
        }
    }

    /**
     * 是否可以往下滚动
     *
     * @param view
     * @return
     */
    public static boolean canChildScrollDown(View view) {
        if (view == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 14) {
            if (view instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) view;
                if (absListView.getChildCount() > 0) {
                    int lastChildBottom = absListView.getChildAt(absListView.getChildCount() - 1).getBottom();
                    return absListView.getLastVisiblePosition() == absListView.getAdapter().getCount() - 1 && lastChildBottom <= absListView.getMeasuredHeight();
                } else {
                    return false;
                }

            } else {
                return ViewCompat.canScrollVertically(view, 1) || view.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(view, 1);
        }
    }

    /**
     * 若value不为空，则将数据传入intent中
     */
    public static void putExtra(Intent intent, String key, String value) {
        if (!TextUtils.isEmpty(value)) {
            intent.putExtra(key, value);
        }
    }
//
//    /***
//     * 测量ListView的高度
//     *
//     * @param listView
//     */
//    public static void setListViewHeightBasedOnChildren(ListView listView) {
//        // 获取ListView对应的Adapter
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null) {
//            return;
//        }
//        int totalHeight = 0;
//        for (int i = 0; i < listAdapter.getCount(); i++) { // listAdapter.getCount()返回数据项的数目
//            View listItem = listAdapter.getView(i, null, listView);
//            listItem.measure(0, 0); // 计算子项View 的宽高
//            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
//        }
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        // listView.getDividerHeight()获取子项间分隔符占用的高度
//        // params.height最后得到整个ListView完整显示需要的高度
//        listView.setLayoutParams(params);
//    }

}
