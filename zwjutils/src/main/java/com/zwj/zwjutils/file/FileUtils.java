package com.zwj.zwjutils.file;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.zwj.zwjutils.LogUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


/**
 * @author zwj 2015-5-22 12:52
 *         <p>
 *         内部存储以Internal表示  （）
 *         外部ExternalFilesDir
 */
public class FileUtils {
    private static final String TAG = "FileUtils";

    /**
     * 判断sdcard是否可用
     *
     * @return true, 可用; false,不可用
     */
    public static boolean sdcardIsEnable() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }


    /**
     * 获取文件(夹)
     * folderPath为null则获取根目录，如/data/data/当前应用程序包名/files/ 目录下的文件夹
     *
     * @param param
     * @return
     */
    public static File getFile(Param param) {
        File folder = null;
        switch (param.getRootFileLoaction()) {
            case INTERNAL:
                if (TextUtils.isEmpty(param.getFolderPath())) {
                    folder = param.getContext().getApplicationContext().getFilesDir();
                } else {
                    folder = new File(param.getContext().getApplicationContext()
                            .getFilesDir(), param.getFolderPath());
                }
                break;

            case EXTERNAL:
                if (sdcardIsEnable()) {
                    if (TextUtils.isEmpty(param.getFolderPath())) {
                        folder = param.getContext().getApplicationContext()
                                .getExternalFilesDir(null);
                    } else {
                        folder = new File(param.getContext().getApplicationContext()
                                .getExternalFilesDir(null), param.getPath());
                    }
                } else {
                    LogUtils.e(TAG, "sdcard不可用！");
                }
                break;

            case SDCARD:
                if (sdcardIsEnable()) {
                    if (TextUtils.isEmpty(param.getFolderPath())) {
                        folder = Environment.getExternalStorageDirectory();
                    } else {
                        folder = new File(Environment.getExternalStorageDirectory(), param.getFolderPath());
                    }
                } else {
                    LogUtils.e(TAG, "sdcard不可用！");
                }
                break;
        }

        if (folder != null && !folder.exists()) {
            folder.mkdirs();
        }

        if (param.isFile) {
            File file = null;
            if (folder != null) {
                file = new File(folder, param.getFileName());
            }
            return file;
        } else {
            return folder;
        }
    }

    /**
     * 将数据写入到文件中
     *
     * @return true - 写入成功； false - 写入失败
     */
    public static boolean saveFile(WriteParam param) {
        boolean flag = false;

        FileOutputStream fos = null;

        try {
            File file = getFile(param);
            if (file != null) {
                fos = new FileOutputStream(file, param.isAppend());
                fos.write(param.getDatas(), 0, param.getDatas().length);
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
     * 从文件中提取数据
     *
     * @return
     */
    public static String loadContentFromFile(Param param) {
        FileInputStream fis = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {

            File file = getFile(param);
            if (file != null) {
                fis = new FileInputStream(file);
                byte[] data = new byte[1024];

                int len = 0;
                while ((len = fis.read(data)) != -1) {
                    baos.write(data, 0, len);
                }
                return new String(baos.toByteArray());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static boolean deleteFile(Param param) {
        boolean flag = false;
        File file = getFile(param);
        if (file != null && file.exists()) {
            flag = file.delete();
        }
        return flag;
    }

    /**
     * 递归删除所有文件(传入的文件对象不能为空)
     *
     * @param file
     */
    public static void deleteFileWithRecursion(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file2 : files) {
                deleteFileWithRecursion(file2);
            }
        } else {
            file.delete();
        }
    }

    // ====== 其他需要重新整理的
    // TODO


    /**
     * 以树状遍历文件
     *
     * @param file  遍历的根目录
     * @param count 文件或文件夹所处的层数
     */
    public static void listAllFile(File file, int count) {
        if (null != file) {
            StringBuffer sbuf = new StringBuffer();
            for (int i = 1; i < count; i++) {
                sbuf.append("--");
            }
            sbuf.append(file.getName());

            if (file.isDirectory()) { // file是文件夹
                System.out.println(sbuf.toString() + "(目录)");

                File[] files = file.listFiles();
                count++;
                for (File file2 : files) {
                    listAllFile(file2, count);
                }
            } else { // file是文件
                System.out.println(sbuf.toString() + "(文件)");
            }
        }
    }

    /**
     * 文件拷贝
     * @param param
     * @return
     */
    public static String copyFile(CopyFileParam param) {

        boolean flag = false;

        File targetFile = getFile(param.getTargetParam());
        if(targetFile != null) {
            if(param.getModel() == 1 && targetFile.exists()) {
                flag = true;
            }else {
                FileOutputStream fos = null;
                InputStream is = null;
                try {
                    if (param.isAssets()) {
                        AssetManager assetManager = param.getSourceParam().getContext().getAssets();
                        is = assetManager.open(param.getSourceParam().getFileName());
                    } else {
                        File sourceFile = getFile(param.getSourceParam());
                        if(sourceFile != null) {
                            is = new FileInputStream(sourceFile);
                        }
                    }

                    if(targetFile != null) {
                        fos = new FileOutputStream(targetFile);
                    }

                    if(fos != null && is != null) {
                        int len = 0;
                        byte[] buffer = new byte[1024];
                        while ((len = is.read(buffer)) != -1) {
                            fos.write(buffer, 0, len);
                        }

                        fos.flush();
                        flag = true;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        if(flag) {
            return targetFile.getAbsolutePath();
        }
        return null;
    }

    /**
     * 从本地获取json数据信息
     *
     * @param context  上下文
     * @param fileName json数据文件名
     * @return
     */
    public static String loadContentFromAssets(Context context, String fileName) {
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    context.getApplicationContext().getAssets().open(fileName)));
            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public static boolean copyFile(File sourceFile, File targetFile) {
        // NIO中读取数据的步骤：1）从FileInputStream中得到Channel对象;2)创建一个buffer对象;3)从Channel中读数据到Buffer中;
        FileInputStream fin = null;
        FileOutputStream fout = null;
        FileChannel fcin = null;
        FileChannel fcout = null;
        try {
            fin = new FileInputStream(sourceFile);
            fout = new FileOutputStream(targetFile);
            fcin = fin.getChannel();
            fcout = fout.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int r = 0;
            while ((r = fcin.read(buffer)) != -1) {
                buffer.clear();
                buffer.flip();// 反转一下，从写入状态变成读取状态
                fcout.write(buffer);
            }
            return true;
        } catch (Exception e) {
            Log.i("FileUtils", "复制文件发生错误", e);
        } finally {
            if (fin != null)
                try {
                    fin.close();
                    if (fout != null)
                        fout.close();
                    if (fcin != null)
                        fcin.close();
                    if (fcout != null)
                        fcout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return false;
    }


    /**
     * 获取文件的路径,若是不存在则返回null
     *
     * @param file
     * @return
     */
    public static String getFilePath(File file) {
        if (file != null && file.exists()) {
            return file.getAbsolutePath();
        }
        return null;
    }
}