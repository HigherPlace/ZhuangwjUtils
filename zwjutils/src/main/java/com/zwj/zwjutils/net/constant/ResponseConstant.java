package com.zwj.zwjutils.net.constant;

/**
 * Created by zwj on 2017/3/9.
 * 后台返回的json字段名称
 */
public class ResponseConstant {

    /**状态字0*/
    public static String TAG_CODE = "status";

    /**提示消息*/
    public static String TAG_MESSAGE = "message";


    /**数据*/
    public static String TAG_DATA = "data";

    public static String TAG_TOTAL = "total";

    /** 成功获取数据 */
    public static int SUCCESS = 1;

    /** 成功获取数据，没有返回状态字直接返回数据的情况 */
    public static int SUCCESS_ONLY_DATA = 1000;

    /** 未登录 */
    public static int UNLOGIN = 1001;

    /** 请求数据失败,数据为空 */
    public static int FAIL = 0;

    /** 超时 */
    public static int TIME_OUT = 1111;

    /** 没有网络 */
    public static int DISABLE_NETWORK = 1112;

    /** 图片转换成base64的数据出错时也将调用该方法 */
    public static int PARSE_IMAGE_FILE_TO_BASE64 = 1113;

    /** 取消网络请求失败异常 */
    public static int CANCELLED_EXCEPTION = 1114;

    /** 后台出错 */
    public static int ERROR = 1115;

    /** 其他错误 */
    public static int ERROR_OTHER = 1116;
}
