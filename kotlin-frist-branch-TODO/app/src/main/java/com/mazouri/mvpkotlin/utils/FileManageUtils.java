package com.mazouri.mvpkotlin.utils;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import com.mazouri.mvpkotlin.base.ComponentHolder;
import com.mazouri.mvpkotlin.base.Constants;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

/**
 * Created by HeYingXin on 2017/7/6.
 */
public class FileManageUtils {

    private static FileManageUtils fileManageUtils;

    private boolean isExistSDCard = false;
    // 应用文件路径 备份

    private String APP_FILE_BACKUPS = ComponentHolder.getAppComponent().application().getCacheDir().toString() + File.separator
            + Constants.SystemConfig.APP_NAME + File.separator;
    // 应用文件路径 默认
    private String APP_FILE_DEFAULT = Environment.getExternalStorageDirectory().toString() + File.separator
            + Constants.SystemConfig.APP_NAME + File.separator;
    // 图片缓存
    private String APP_PHOTO_CACHE = "PhotoCache" + File.separator;
    // 临时图片
    private String APP_PHOTO_TEMP = "PhotoTemp" + File.separator;
    // 录音缓存
    private String APP_TAPE_CACHE = "TapeCache" + File.separator+".Secret";//录音设置不直接显示
    //文件缓存
    private String APP_FILE_CACHE = "FileCache" + File.separator+".Secret";
    //文件下载目录
    private String APP_FILE_DOWNLOAD = "FileDownload" + File.separator;
    // 错误文件
    private String APP_ERROR_LOG = "ErrorLog" + File.separator+".Secret";
    // APP文件
    private String APP_DOWNLOAD = "Download" + File.separator;
    // 资源文件
    private String APP_RESOURCES = "Resources" + File.separator+".Secret";
    // 崩溃
    private String APP_CRASH = "crash" + File.separator;

    DownLoadListener listen;

    public synchronized static FileManageUtils getInstance() {
        if (fileManageUtils == null) {
            fileManageUtils = new FileManageUtils();
        }
        return fileManageUtils;
    }

    public FileManageUtils() {
        // 判断条件：SD卡存在并且剩余空间大于10MB时
        isExistSDCard = ExistSDCard() && (getSDFreeSize() > 10);
    }

    /**
     * 获取文件根目录
     */
    public String getAppTemp() {
        String fileTemp = "";
        if (isExistSDCard) {
            fileTemp = APP_FILE_DEFAULT;
        } else {
            fileTemp = APP_FILE_BACKUPS;
        }
        File file = new File(fileTemp);
        file.mkdirs();
        return fileTemp;
    }

    /**
     * 获取临时图片路径
     *
     * @return
     */
    public String getAPP_PHOTO_TEMP() {
        String fileTemp = "";
        if (isExistSDCard) {
            fileTemp = APP_FILE_DEFAULT + APP_PHOTO_TEMP;
        } else {
            fileTemp = APP_FILE_BACKUPS + APP_PHOTO_TEMP;
        }
        File file = new File(fileTemp);
        file.mkdirs();
        return fileTemp;
    }

    /**
     * 获取图片缓存路径
     *
     * @return
     */
    public String getAPP_PHOTO_CACHE() {
        String fileTemp = "";
        if (isExistSDCard) {
            fileTemp = APP_FILE_DEFAULT + APP_PHOTO_CACHE;
        } else {
            fileTemp = APP_FILE_BACKUPS + APP_PHOTO_CACHE;
        }
        File file = new File(fileTemp);
        file.mkdirs();
        return fileTemp;
    }

    /**
     * 获取文件缓存
     *
     * @return
     */
    public String getAPP_FILE_CACHE() {
        String fileTemp = "";
        if (isExistSDCard) {
            fileTemp = APP_FILE_DEFAULT + APP_FILE_CACHE;
        } else {
            fileTemp = APP_FILE_BACKUPS + APP_FILE_CACHE;
        }
        File file = new File(fileTemp);
        file.mkdirs();
        return fileTemp;
    }

    /**
     * 获取文件下载目录
     *
     * @return
     */
    public String getAPP_FILE_DOWNLOAD() {
        String fileTemp = "";
        if (isExistSDCard) {
            fileTemp = APP_FILE_DEFAULT + APP_FILE_DOWNLOAD;
        } else {
            fileTemp = APP_FILE_BACKUPS + APP_FILE_DOWNLOAD;
        }
        File file = new File(fileTemp);
        file.mkdirs();
        return fileTemp;
    }

    /**
     * 获取录音缓存
     *
     * @return
     */
    public String getAPP_TAPE_CACHE() {
        String fileTemp = "";
        if (isExistSDCard) {
            fileTemp = APP_FILE_DEFAULT + APP_TAPE_CACHE;
        } else {
            fileTemp = APP_FILE_BACKUPS + APP_TAPE_CACHE;
        }
        File file = new File(fileTemp);
        file.mkdirs();
        return fileTemp;
    }

    /**
     * 获取错误日志
     *
     * @return
     */
    public String getAPP_ERROR_LOG() {
        String fileTemp = "";
        if (isExistSDCard) {
            fileTemp = APP_FILE_DEFAULT + APP_ERROR_LOG;
        } else {
            fileTemp = APP_FILE_BACKUPS + APP_ERROR_LOG;
        }
        File file = new File(fileTemp);
        file.mkdirs();
        return fileTemp;
    }

    /**
     * 下载目录
     *
     * @return
     */
    public String getAPP_DOWNLOAD() {
        String fileTemp = "";
        if (isExistSDCard) {
            fileTemp = APP_FILE_DEFAULT + APP_DOWNLOAD;
        } else {
            fileTemp = APP_FILE_BACKUPS + APP_DOWNLOAD;
        }
        File file = new File(fileTemp);
        file.mkdirs();
        return fileTemp;
    }

    /**
     * 资源目录
     *
     * @return
     */
    public String getAPP_RESOURCES() {
        String fileTemp = "";
        if (isExistSDCard) {
            fileTemp = APP_FILE_DEFAULT + APP_RESOURCES;
        } else {
            fileTemp = APP_FILE_BACKUPS + APP_RESOURCES;
        }
        File file = new File(fileTemp);
        file.mkdirs();
        return fileTemp;
    }

    /**
     * 资源目录
     *
     * @return
     */
    public String getAPP_Crash() {
        String fileTemp = "";
        if (isExistSDCard) {
            fileTemp = APP_FILE_DEFAULT + APP_CRASH;
        } else {
            fileTemp = APP_FILE_BACKUPS + APP_CRASH;
        }
        File file = new File(fileTemp);
        file.mkdirs();
        return fileTemp;
    }

    /**
     * 判断SD卡是否存在
     *
     * @return
     */
    private boolean ExistSDCard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }

    /**
     * 判断SD卡剩余空间
     *
     * @return
     */
    public long getSDFreeSize() {
        // 取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        // 获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        // 空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocks();
        // 返回SD卡空闲大小
        // return freeBlocks * blockSize; //单位Byte
        // return (freeBlocks * blockSize)/1024; //单位KB
        return (freeBlocks * blockSize) / 1024 / 1024; // 单位MB
    }

    /**
     * 判断SD卡总空间
     *
     * @return
     */
    public long getSDAllSize() {
        // 取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        // 获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        // 获取所有数据块数
        long allBlocks = sf.getBlockCount();
        // 返回SD卡大小
        // return allBlocks * blockSize; //单位Byte
        // return (allBlocks * blockSize)/1024; //单位KB
        return (allBlocks * blockSize) / 1024 / 1024; // 单位MB
    }

    /**
     * 获得文件内容
     * @param FileName
     * @return
     */
    public String getFileText(String FileName) {
        StringBuffer str = new StringBuffer("");
        File file = new File(FileName);
        try {
            FileReader fr = new FileReader(file);
            int ch = 0;
            while ((ch = fr.read()) != -1)
            {
                str.append((char) ch).append("");
            }
            fr.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("File reader出错");
        }
        return str.toString();
    }

    /**
     * 删除文件
     *
     * @param file
     */
    public void deleteFile(File file) {
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete();
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                    this.deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
                }
            }
            file.delete();
        } else {
            L.i("文件不存在:" + file.toString());
        }
    }

    /**
     * 删除文件有时间要求
     *
     * @param file
     * @param time      系统时间就行
     * @param spaceTime 间隔时间    ，比如删除7天前(604800000)，当前时间-文件时间>间隔时间
     */
    public void deleteFile(File file, long time, long spaceTime) {
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                if (time - file.lastModified() > spaceTime)
                    file.delete();
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                    if (time - files[i].lastModified() > spaceTime)//超过间隔时间   过期
                        this.deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
                }
            }
            file.delete();
        } else {
            L.i("文件不存在:" + file.toString());
        }
    }

    public void del(){
        deleteFile(new File(getAPP_PHOTO_CACHE()),System.currentTimeMillis(),259200000);
        deleteFile(new File(getAPP_TAPE_CACHE()),System.currentTimeMillis(),259200000);
        deleteFile(new File(getAPP_FILE_DOWNLOAD()),System.currentTimeMillis(),259200000);
    }


    public boolean saveImage(Bitmap photo, String spath) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(spath, false));
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * 保存图片
     */
    public String saveBitmap(String defaultFile, Bitmap bm, int ratio, String photoCropName) {
        String filse;
        try {
            File f = new File(defaultFile + photoCropName);
            filse = f.toString();
            FileOutputStream out = new FileOutputStream(f);
            if (bm != null && out != null)
                bm.compress(Bitmap.CompressFormat.JPEG, ratio, out);// ratio 是压缩率
            // 如果不压缩是100，表示压缩率为0
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            filse = "";
        } catch (IOException e) {
            e.printStackTrace();
            filse = "";
        }
        return filse;
    }

    /**
     * 保存图片
     * 删除拍照的图片
     */
    public String saveBitmapTwo(String defaultFile, Bitmap bm, int ratio, String photoCropName, String oldPath) {
        String filse;
        try {
            File f = new File(defaultFile + photoCropName);
            filse = f.toString();
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, ratio, out);// ratio 是压缩率
            // 如果不压缩是100，表示压缩率为0
            out.flush();
            out.close();

            FileManageUtils.getInstance().deleteFile(new File(oldPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            filse = "";
        } catch (IOException e) {
            e.printStackTrace();
            filse = "";
        }
        return filse;
    }

    /**
     * 获取表情文件
     *
     * @param context
     * @return
     */
    public static List<String> getEmojiFile(Context context) {
        List<String> list = new ArrayList<String>();
        InputStream in = null;
        BufferedReader br = null;
        try {
            in = context.getResources().getAssets().open("emoji");
            br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String str = null;
            while ((str = br.readLine()) != null) {
                list.add(str);
            }

            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
                try {
                    if(in!=null)
                        in.close();
                    if(br!=null)
                        br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return list;
    }

    /**
     * 目录是否存在
     *
     * @param dir
     * @return
     */
    public static String isFolderExist(String dir) {
        File folder = Environment.getExternalStoragePublicDirectory(dir);
        boolean rs = (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
        return folder.getAbsolutePath();
    }

    @SuppressLint("NewApi")
    public static void download(Context context, String name, String apkUrl) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);
        String dir = isFolderExist(FileManageUtils.getInstance().getAPP_DOWNLOAD());
        System.out.println(dir + "/" + name + ".apk");
        File f = new File(name);
        if (f.exists())
            f.delete();
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkUrl));
        request.setShowRunningNotification(true);
        request.setDestinationInExternalPublicDir(FileManageUtils.getInstance().getAPP_DOWNLOAD(), name + ".apk");
        request.allowScanningByMediaScanner();// 表示允许MediaScanner扫描到这个文件，默认不允许。
        request.setTitle("应用下载");// 设置下载中通知栏提示的标题
        request.setDescription("\"" + name + "\"正在下载");// 设置下载中通知栏提示的介绍
        File file = new File(FileManageUtils.getInstance().getAPP_DOWNLOAD() + name + ".apk");
        Uri uri = Uri.fromFile(file);
        request.setDestinationUri(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        try {
            long downloadId = downloadManager.enqueue(request);
        } catch (IllegalArgumentException e) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(apkUrl);
            intent.setData(content_url);
            context.startActivity(intent);
        }
    }

    /**
     * 复制单个文件
     *
     * @return boolean
     */
    public void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0; //总字节数
            int byteread = 0; //当前缓冲区下的字节
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                fs.close();
            }
        } catch (Exception e) {
            L.i("FileManageUtils", "复制单个文件操作出错" + e);
            e.printStackTrace();
        }
    }

    /**
     * 获取文件大小(这个方法只支持单个文件)
     */
    public String getFileSize(String filePath) {
        File file = new File(filePath);
        String size = "";
        if (file.exists() && file.isFile()) {
            long fileS = file.length();
            if (fileS != 0) {//用文件length判断，没有就用流去计算
                DecimalFormat df = new DecimalFormat("#.00");
                if (fileS < 1024) {
                    size = df.format((double) fileS) + "B";
                } else if (fileS < 1048576) {
                    size = df.format((double) fileS / 1024) + "KB";
                } else if (fileS < 1073741824) {
                    size = df.format((double) fileS / 1048576) + "MB";  //1024*1024
                } else {
                    size = df.format((double) fileS / 1073741824) + "GB";  //*1024
                }
            } else {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file);
                    int fileS2 = fis.available();
                    if (fileS2 != 0) {
                        DecimalFormat df = new DecimalFormat("#.00");
                        if (fileS2 < 1024) {
                            size = df.format((double) fileS2) + "B";
                        } else if (fileS2 < 1048576) {
                            size = df.format((double) fileS2 / 1024) + "KB";
                        } else if (fileS2 < 1073741824) {
                            size = df.format((double) fileS2 / 1048576) + "MB";  //1024*1024
                        } else {
                            size = df.format((double) fileS2 / 1073741824) + "GB";  //*1024
                        }
                    } else {
                        return "0.00B";
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return "未知大小";
                }
                return size;
            }
        } else if (file.exists() && file.isDirectory()) {
            size = "未知大小";
        } else {
            size = "0B";
        }
        return size;
    }

    /**
     * 获取文件夹所有文件大小,递归调用最后返回全部以兆为单位
     *
     * @param file
     * @return
     */
    public double getFileSize(File file) {
        //判断文件是否存在
        if (file.exists()) {
            //如果是目录则递归计算其内容的总大小
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                double size = 0;
                for (File f : children)
                    size += getFileSize(f);
                return size;
            } else {//如果是文件则直接返回其大小,以“兆”为单位
                double size = (double) file.length() / 1024 / 1024;
                return size;
            }
        } else {
            Log.i("FileManageUtils", "文件或者文件夹不存在，请检查路径是否正确！");
            return 0.0;
        }
    }

    public boolean saveToDisk(ResponseBody body,DownLoadListener listen) {
        this.listen=listen;
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(getAPP_FILE_DOWNLOAD()+"bjn.apk");
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    L.d("TAG", "file download: " + fileSizeDownloaded + " of " + fileSize);
                    listen.downLoadProgress(fileSizeDownloaded,fileSize);
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }


    public interface DownLoadListener {

        void downLoadProgress(Long fileSizeDownloaded, Long fileSize);
    }

}
