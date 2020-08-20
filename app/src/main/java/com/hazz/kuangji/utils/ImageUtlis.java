package com.hazz.kuangji.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtlis {


    private static File mPhotoFile = null;

    public static void setPhotoFile(File photoFile) {
        mPhotoFile = photoFile;
    }

    public static File getPhotoFile() {

        return mPhotoFile;
    }


    /**
     * 保存图片到图库
     *
     * @param bmp
     */
    public static void saveImageToGallery(Context context,Bitmap bmp, String bitName) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(),
                "code");
        if (!appDir.exists()) {
            appDir.mkdir();
        }

        String fileName = bitName + ".png";
        File file = new File(appDir, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * @param bmp     获取的bitmap数据
     * @param picName 自定义的图片名
     */
    public static void saveBmp2Gallery(Context context, Bitmap bmp, String picName) {
//        saveImageToGallery(context,bmp, picName);
        String fileName = null;
        //系统相册目录
        String galleryPath =  Environment.getExternalStorageDirectory().toString();
        // 声明文件对象
        File file = null;
        // 声明输出流
        FileOutputStream outStream = null;
        try {
            // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
            file = new File(galleryPath, picName + ".png");
            // 获得文件相对路径
            fileName = file.toString();
            // 获得输出流，如果文件中有内容，追加内容
            outStream = new FileOutputStream(fileName);
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
}