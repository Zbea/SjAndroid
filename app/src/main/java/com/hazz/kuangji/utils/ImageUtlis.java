package com.hazz.kuangji.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageUtlis {


    private static File mPhotoFile = null;

    public static void setPhotoFile(File photoFile) {
        mPhotoFile = photoFile;
    }

    public static File getPhotoFile() {

        return mPhotoFile;
    }


    /**
     * @param bmp     获取的bitmap数据
     * @param picName 自定义的图片名
     */
    public static void saveBmpGallery(Context context, Bitmap bmp, String picName) {

        File appDir= new File(getFilePath(context, "/eye/"));
//        File appDir = new File(Environment.getExternalStorageDirectory()+"/eye/image/");
//        if (!appDir.exists()) {
//            appDir.mkdir();
//        }
        // 声明文件对象
        File file = null;
        // 声明输出流
        FileOutputStream outStream = null;
        try {
            // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
            file = new File(appDir, picName + ".png");
            Log.i("sj","111:"+file.toString());
            // 获得输出流，如果文件中有内容，追加内容
            outStream = new FileOutputStream(file);
            if (null != outStream) {
                bmp.compress(Bitmap.CompressFormat.PNG, 90, outStream);
            }
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);

    }

    /**
     * @param context 上下文对象
     * @param dir     存储目录
     * @return
     */
    public static String getFilePath(Context context, String dir) {
        String directoryPath = "";
        //判断SD卡是否可用
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            directoryPath = context.getExternalFilesDir(dir).getAbsolutePath();
            // directoryPath =context.getExternalCacheDir().getAbsolutePath() ;
        } else {
            //没内存卡就存机身内存
            directoryPath = context.getFilesDir() + File.separator + dir;
            // directoryPath=context.getCacheDir()+File.separator+dir;
        }
        File file = new File(directoryPath);
        if (!file.exists()) {//判断文件目录是否存在
            file.mkdirs();
        }
        return directoryPath;
    }

}