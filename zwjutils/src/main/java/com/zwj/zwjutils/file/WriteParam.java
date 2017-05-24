package com.zwj.zwjutils.file;

/**
 * Created by zwj on 2017/5/24.
 * 写入文件相关参数实体
 */
public class WriteParam extends Param{
    private byte[] datas;           // 写入内容
    private boolean isAppend;       // true - 追加； false - 覆盖；

    public byte[] getDatas() {
        return datas;
    }

    public WriteParam setDatas(byte[] datas) {
        this.datas = datas;
        return this;
    }

    public boolean isAppend() {
        return isAppend;
    }

    public WriteParam setAppend(boolean append) {
        isAppend = append;
        return this;
    }
}
