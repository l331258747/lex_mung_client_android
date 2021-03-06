package cn.lex_mung.client_android.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.lex_mung.client_android.app.Constants;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.utils.AppUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by LGQ
 * Time: 2018/7/26
 * Function: file工具类
 */

public class FileUtil2 {

    /**
     * context方法
     * getCacheDir  getFilesDir
     * 1.getCacheDir和getFilesDir是放在/data/data/packagename下的，所以这个目录中的内容必须是root的手机在文件操作系统中才能看到。
     * 2.当然如果在应用程序中清空数据或者卸载应用，那么这两个目录下的文件也将会被清空的。
     * 3.些文件一旦设备内部存储空间不足时，这些保存在 cache下的文件会删除
     * /data/data/包名/cache
     *
     * getExternalFilesDir      getExternalCacheDir
     * 1.这个是放在外置存储卡的，这个目录下的内容 可以使用文件浏览系统查看到，
     * 但是如果清空数据或者卸载应用，俩个目录下的文件也将被清空。
     * /storage/emulated/0/Android/data/com.learn.test/cache
     */

    /**
     * 外部存储 跟随应用
     * getExternalFilesDir()
     * /storage/emulated/0/Android/data/com.learn.test/files
     *
     * getExternalFilesDir("test")
     * /storage/emulated/0/Android/data/com.learn.test/files/test
     *
     * getExternalFilesDir(String type) DIRECTORY_MUSIC
     * /storage/emulated/0/Android/data/com.learn.test/files/Music
     */

    /**
     * 外部存储 不跟随应用 Environment
     * getExternalStorageDirectory = getExternalStoragePublicDirectory
     * /storage/emulated/0
     *
     * Environment.getExternalStoragePublicDirectory("test")
     * /storage/emulated/0/test
     *
     * Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
     * /storage/emulated/0/Pictures
     */

    /**
     * 内部存储
     * Environment.getDataDirectory()
     * /data文件夹,一般应用无权限操作
     * <p>
     * Environment.getDownloadCacheDirectory()
     * /cache文件夹,一般应用无权限操作
     * <p>
     * Environment.getRootDirectory()
     * /system 文件夹,需要root 权限
     */


    //获取文件夹 filename:log
    public static File getFolder(Context context, String filename) {
        String filePath;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) { // SD卡根目录的hello.text
            filePath = Environment.getExternalStorageDirectory().getPath() + File.separator +
                    Constants.BASE_PATH + File.separator + filename;
        } else  // 系统下载缓存根目录的hello.text
            filePath = context.getApplicationContext().getFilesDir().getPath() + File.separator + filename;
        File dir = new File(filePath);

        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * 删除文件目录，或指定文件
     */
    public static void delAllFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                delAllFile(f);
            }
        } else if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 创建下载文件路径
     *
     * @param filePath 文件名
     * @return 生成的文件
     */
    public static File createDownloadFile(String filePath) {
        return createFile(filePath);
    }

    /**
     * 通过提供的文件名在默认路径下生成文件
     *
     * @param fileName 文件的名称
     * @return 生成的文件
     */
    public static File createFile(String fileName) {
        File file = new File(fileName);
        if (!isFileExist(file)) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                if (fileName.lastIndexOf("/") > -1) {
                    String fatherRoot = fileName.substring(0, fileName.lastIndexOf("/"));
                    File filetemp = new File(fatherRoot);
                    filetemp.mkdirs();
                    try {
                        file.createNewFile();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
        return file;
    }

    /**
     * 是否存在此文件
     *
     * @param file 判断是否存在的文件
     * @return 存在返回true，否则返回false
     */
    public static boolean isFileExist(final File file) {
        boolean isExist = false;
        // 在无SD卡时file会为空
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            isExist = true;
        } else {
            isExist = false;
        }
        return isExist;
    }


    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                LogUtil.e("复制单个文件操作成功");
            }
        } catch (Exception e) {
            LogUtil.e("复制单个文件操作出错");
            e.printStackTrace();

        }
    }

    public static void downloadFile3(String url, String filePath,DowloadListener dowloadListener) {
        LogUtil.e("url:" + url);
        //下载路径，如果路径无效了，可换成你的下载路径
        Request request = new Request.Builder().url(url).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败
                e.printStackTrace();
                LogUtil.e("DOWNLOAD ：download failed");
                dowloadListener.onFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Sink sink = null;
                BufferedSink bufferedSink = null;
                try {
                    File dest = new File(filePath);
                    sink = Okio.sink(dest);
                    bufferedSink = Okio.buffer(sink);
                    bufferedSink.writeAll(response.body().source());
                    bufferedSink.close();
                    LogUtil.e("DOWNLOAD:download success");
                    dowloadListener.onSuccess();
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.e("DOWNLOAD :download failed");
                    dowloadListener.onFailed();
                } finally {
                    if (bufferedSink != null) {
                        bufferedSink.close();
                    }
                }
            }
        });
    }

    public interface DowloadListener{
        void onSuccess();
        void onFailed();
    }

}
