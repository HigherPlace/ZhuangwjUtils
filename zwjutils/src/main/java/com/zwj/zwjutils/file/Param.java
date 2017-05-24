package com.zwj.zwjutils.file;

import android.content.Context;
import android.text.TextUtils;

/**
 * Created by zwj on 2017/5/24.
 */
public class Param {
    protected Context context;
    protected String path;        // 文件路径
    protected RootFileLoaction rootFileLoaction = RootFileLoaction.EXTERNAL;
    protected String folderPath;      // 文件夹路径
    protected String fileName;        // 文件名称
    protected boolean isFile;       // true - 文件； false - 文件夹

    /**
     * 根目录所在位置
     */
    public enum RootFileLoaction{
        INTERNAL,           // 内部/data/data/当前应用程序包名/files/目录下
        EXTERNAL,           // 外部/data/data/当前应用程序包名/files/目录下
        SDCARD              // 外部sdcard上
    }

    public Context getContext() {
        return context;
    }

    public Param setContext(Context context) {
        this.context = context;
        return this;
    }

    public String getPath() {
        if(TextUtils.isEmpty(path)) {
            StringBuilder sbPath = new StringBuilder();
            if(!TextUtils.isEmpty(folderPath)) {
                sbPath.append(folderPath);
            }

            if(!TextUtils.isEmpty(fileName)) {
                sbPath.append("/").append(fileName);
            }

            path = sbPath.toString();
            if(TextUtils.isEmpty(path)) {
                return null;
            }
        }
        return path;
    }

    public Param setPath(String path) {
        this.path = path;
        return this;
    }

    public RootFileLoaction getRootFileLoaction() {
        return rootFileLoaction;
    }

    public Param setRootFileLoaction(RootFileLoaction rootFileLoaction) {
        this.rootFileLoaction = rootFileLoaction;
        return this;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public Param setFolderPath(String folderPath) {
        this.folderPath = folderPath;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    /**
     * 有设置文件名称时会把类型设置为文件
     * @param fileName
     * @return
     */
    public Param setFileName(String fileName) {
        this.fileName = fileName;
        this.isFile = true;
        return this;
    }

    public boolean isFile() {
        return isFile;
    }

    public Param setFile(boolean file) {
        isFile = file;
        return this;
    }
}
