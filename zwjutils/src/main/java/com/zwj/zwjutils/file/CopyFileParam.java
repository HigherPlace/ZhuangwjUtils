package com.zwj.zwjutils.file;

/**
 * Created by zwj on 2017/5/24.
 */

public class CopyFileParam {
    private Param sourceParam;
    private Param targetParam;
    private boolean isAssets;               // true - 从assets文件夹里拷贝文件
    private int model;              // 0 - 强制copy，不管文件是否已经存在； 1 - 若存在则不覆盖


    public Param getSourceParam() {
        return sourceParam;
    }

    public CopyFileParam setSourceParam(Param sourceParam) {
        this.sourceParam = sourceParam;
        return this;
    }

    public Param getTargetParam() {
        return targetParam;
    }

    public CopyFileParam setTargetParam(Param targetParam) {
        this.targetParam = targetParam;
        return this;
    }

    public boolean isAssets() {
        return isAssets;
    }

    public CopyFileParam setAssets(boolean assets) {
        isAssets = assets;
        return this;
    }

    public int getModel() {
        return model;
    }

    public CopyFileParam setModel(int model) {
        this.model = model;
        return this;
    }
}
