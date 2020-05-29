package com.hazz.kuangji.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.widget.Toolbar;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/11/15.
 */

public class DpUtils {

    private DpUtils() {
    }

    private static int[] widthAndHeight = new int[2];

    /**
     * 获取屏幕宽高(PX)
     *
     * @param context (Activity)
     * @return
     */
    public static int[] getDeviceWidthAndHeight(Context context) {
        if (widthAndHeight[0] == 0 && widthAndHeight[1] == 0) {
            DisplayMetrics metrics = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay()
                    .getMetrics(metrics);

            widthAndHeight[0] = metrics.widthPixels;
            widthAndHeight[1] = metrics.heightPixels;
        }
        return widthAndHeight;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * drawable 转Bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 设置状态栏透明
     */
    public static void setStatusBarTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                field.setAccessible(true);
                field.setInt(activity.getWindow().getDecorView(), Color.TRANSPARENT);  //设置透明
            } catch (Exception e) {
            }
        }
    }


    /**
     * 设置顶部通知栏透明 需要时在 中调用
     *
     * @param isSet 是否设置为透明状态栏
     */
    public static void setSystemUI(Activity activity, boolean isSet) {
        if (!isSet) {
            return;
        }
        View decorView = activity.getWindow().getDecorView();
        int systemUI = decorView.getSystemUiVisibility();
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        systemUI |= flags;
        decorView.setSystemUiVisibility(systemUI);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }


    /**
     * 获取状态栏高度——方法
     */
    public static int getStatusHeight(Context context) {
        int statusBarHeight = -1;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        } else {
            statusBarHeight = DpUtils.dip2px(context, 20);
        }
        return statusBarHeight;
    }

    /**
     * 获取toolbar高度
     *
     * @return
     */
    public static int getToolBarHeight(Toolbar mToolbar, Context context) {
        if (mToolbar != null) {
            return mToolbar.getMinimumHeight();
        }
        return DpUtils.dip2px(context, 56);
    }


    /**
     * 设置状态栏字体颜色是否为亮色s
     */
    private void setStatusBarTextColor(Activity activity, boolean isLight) {
        if (isLight) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }


}
