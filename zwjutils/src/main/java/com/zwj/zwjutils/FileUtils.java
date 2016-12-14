package com.zwj.zwjutils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

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
 */
public class FileUtils {

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
     * 获取文件(夹)对象，若该文件(夹)不存在，则创建
     *
     * @param path 文件夹的绝对路径, path为null则获取sdcard /data/data/当前应用程序包名/files/ 目录下的文件夹
     * @return
     */
    public static File getFolder(Context context, String path) {
        File folder = null;
        if (sdcardIsEnable()) {
            if (TextUtils.isEmpty(path)) {
                folder = context.getApplicationContext()
                        .getExternalFilesDir(null);
            } else {
                folder = new File(context.getApplicationContext()
                        .getExternalFilesDir(null), path);
            }

            if (!folder.exists()) {
                folder.mkdirs();
            }
        }

        return folder;
    }

    /**
     * 获取sdcard /data/data/当前应用程序包名/files/ 的路径
     * @param context
     * @return
     */
    public static File getFolder(Context context) {
        return getFolder(context, null);
    }

    /**
     * 获取内部文件(夹)对象，若该文件(夹)不存在，则创建
     *
     * @param path 文件夹的绝对路径
     * @return
     */
    public static File getInternalFolder(Context context, String path) {
        File folder = null;
        if (sdcardIsEnable()) {
            folder = new File(context.getApplicationContext().getFilesDir(),
                    path);
            if (!folder.exists()) {
                folder.mkdirs();
            }
        }

        return folder;
    }

    public static boolean deleteFileFromSdcard(File folder, String fileName) {
        boolean flag = false;

        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File file = new File(folder, fileName);
            if (file.exists()) {
                flag = file.delete();
            }
        }

        return flag;
    }

    /**
     * 保存文件 /data/data/当前应用程序包名/files/ 到目录下
     *
     * @param context
     * @param fileName 文件名
     * @param data     文件内容
     * @return
     */
    public static boolean saveFile(Context context, String fileName, byte[] data) {
        boolean flag = false;

        if (sdcardIsEnable()) {

            FileOutputStream fos = null;

            try {
                File file = new File(context.getExternalFilesDir(null),
                        fileName);
                fos = new FileOutputStream(file);
                fos.write(data, 0, data.length);
                flag = true;
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
        } else {
            ToastUtil.toast(context.getApplicationContext(), "sdcard 不可用!");
        }

        return flag;
    }

    /**
     * 保存文件 /data/data/当前应用程序包名/files/ 到目录下
     *
     * @param context
     * @param folderPath 文件夹路径
     * @param fileName   文件名
     * @param data       文件内容
     * @return
     */
    public static boolean saveFile2MyDir(Context context, String folderPath,
                                         String fileName, byte[] data) {
        boolean flag = false;
        if (sdcardIsEnable()) {

            FileOutputStream fos = null;

            try {
                File floder = new File(context.getExternalFilesDir(null),
                        folderPath);
                if (!floder.exists()) { // 文件夹不存在
                    floder.mkdirs();
                }

                fos = new FileOutputStream(floder.getAbsolutePath()
                        + File.separator + fileName);
                fos.write(data, 0, data.length);
                flag = true;
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
        } else {
            ToastUtil.toast(context.getApplicationContext(), "sdcard 不可用!");
        }

        return flag;
    }

    /**
     * 从 /data/data/当前应用程序包名/files/ 目录下读取相应文件的内容 若读取成功返回文件内容，失败则返回null
     *
     * @param context
     * @param fileName 文件名
     * @return
     */
    public static String loadContentFromFiles(Context context, String fileName) {
        return loadContentFromFiles(context, null, fileName);
    }

    /**
     * 从文件夹（SdCard/Android/data/packagename/files）中提取数据
     *
     * @param folderName 文件夹的全名，没有就传null
     * @param fileName   文件的名字
     * @return
     */
    public static String loadContentFromFiles(Context context, String folderName, String fileName) {
        if (sdcardIsEnable()) {

            FileInputStream fis = null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            try {
                File folder;
                if (TextUtils.isEmpty(folderName)) {
                    folder = new File(context.getApplicationContext()
                            .getExternalFilesDir(null).getAbsolutePath());
                } else {
                    folder = new File(context.getApplicationContext()
                            .getExternalFilesDir(null), folderName);
                }

                File file = new File(folder.getAbsolutePath(),
                        fileName);
                fis = new FileInputStream(file);
                byte[] data = new byte[1024];

                int len = 0;
                while ((len = fis.read(data)) != -1) {
                    baos.write(data, 0, len);
                }
                return new String(baos.toByteArray());
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
        }

        return null;
    }

    public static byte[] fileToByteArray(File file) {
        FileInputStream fis;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            fis = new FileInputStream(file);
            byte[] data = new byte[1024];

            int len = 0;
            while ((len = fis.read(data)) != -1) {
                baos.write(data, 0, len);
            }
            return baos.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }

    public static void listFileInMyDir(Context context) {
        if (sdcardIsEnable()) {
            listAllFile(context.getExternalFilesDir(null), 1);
        }
    }

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
     * 递归删除所有文件(传入的文件对象不能为空)
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file2 : files) {
                deleteFile(file2);
            }
        } else {
            file.delete();
        }
    }

    /**
     * 默认路径是sdcard下（SdCard/Android/data/packagename/files）
     */
    public static void deleteFileFromDefaultPath(String fileName) {
        File folder = getFolder(null);
        File file = new File(folder, fileName);
        deleteFile(file);
    }

    /**
     * 从assets文件夹里拷贝文件到sdcard上
     *
     * @param fileName 要拷贝的文件名
     * @return 返回文件路径
     */
    public static String copyFileFromAssets(Context context, String fileName) {
        File folder = context.getExternalFilesDir(null);
        return copyFileFromAssets(context, folder, fileName);
    }

    public static String copyFileFromAssets(Context context, File folder, String fileName) {
        return copyFileFromAssets(context, folder, fileName, false);
    }

    /**
     * 从assets文件夹里拷贝文件到sdcard上
     *
     * @param fileName 要拷贝的文件名
     * @return 返回文件路径
     */
    public static String copyFileFromAssets(Context context, File folder, String fileName, boolean forceUpdate) {
        File file = null;
        if (folder != null) {
            file = new File(folder, fileName);
            if (forceUpdate || !file.exists()) { // 文件不存在,就复制
                AssetManager assetManager = context.getAssets();
                FileOutputStream fos = null;
                InputStream is = null;
                try {
                    is = assetManager.open(fileName);
                    fos = new FileOutputStream(file);

                    int len = 0;
                    byte[] buffer = new byte[1024];
                    while ((len = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                    }

                    fos.flush();
                    return file.getAbsolutePath();
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
            } else {
                return file.getAbsolutePath();
            }
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
     * 把数据写入到文件中（SdCard/Android/data/packagename/files）
     *
     * @param folderName 文件夹名字
     * @param fileName   文件名
     * @param content    内容
     * @param isAppend   true追加内容到文件末尾/false覆盖文件
     */
    public static void writeContent2File(Context context, String folderName, String fileName, String content, boolean isAppend) {

        if (sdcardIsEnable()) {

            FileOutputStream fos = null;

            try {
                File folder;
                if (TextUtils.isEmpty(folderName)) {
                    folder = new File(context.getApplicationContext()
                            .getExternalFilesDir(null).getAbsolutePath());
                } else {
                    folder = new File(context.getApplicationContext()
                            .getExternalFilesDir(null), folderName);
                }

                if (!folder.exists()) { // 文件夹不存在
                    folder.mkdirs();
                }

                fos = new FileOutputStream(folder.getAbsolutePath()
                        + File.separator + fileName, isAppend);
                fos.write(content.getBytes(), 0, content.getBytes().length);
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
        } else {
            ToastUtil.toast(context.getApplicationContext(), "sdcard 不可用!");
        }
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

    /**
     * 获取日志文件路径
     * @return
     */
    public static String getLogDirPath(Context context) {
        File logDir = getFolder(context.getApplicationContext(), "log");
        String path = null;
        if(logDir != null)
            path = logDir.getAbsolutePath();

        return path;
    }
}