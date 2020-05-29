package com.hazz.kuangji.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.hazz.kuangji.R;
import com.hazz.kuangji.net.ExceptionHandle;


/**
 * Description:
 * Data：2018/10/25-14:54
 * Author: cwh
 */
public class ToastUtils {
    private static Toast mToast = null;
    private static long mLastShowTime = 0;
    private static String mLastMessage = "";

    private ToastUtils() {
    }

    /**
     * show Toast
     *
     * @param context
     * @param msg
     */
    public static void showToast(Context context, String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER,0,0);
            mToast.show();
            mLastShowTime = System.currentTimeMillis();
            mLastMessage = msg;
        } else {
            if (mLastMessage.equals(msg)) {
                if ((System.currentTimeMillis() - mLastShowTime) > Toast.LENGTH_SHORT) {
                    mToast.setText(msg);
                    mToast.show();
                    mLastShowTime = System.currentTimeMillis();
                } else {
                    mToast.setText(msg);
                    mToast.show();
                }
            } else {
                mLastMessage = msg;
                mToast.setText(msg);
                mToast.show();
                mLastShowTime = System.currentTimeMillis();
            }
        }
    }


    public static void showToast(Context context, int id){
        showToast(context,context.getString(id));
    }


    public static void showToast(Context context, ExceptionHandle.ResponeThrowable responseThrowable) {
        int code = responseThrowable.code;
        if (code == ExceptionHandle.ERROR.UNKONW_HOST_EXCEPTION) {
            showToast(context, context.getString(R.string.net_work_error));
        }else if(code == ExceptionHandle.ERROR.NETWORD_ERROR ||code==ExceptionHandle.ERROR.SERVER_ADDRESS_ERROR){
            showToast(context,context.getString(R.string.connect_server_timeout));
        } else if(code ==ExceptionHandle.ERROR.PARSE_ERROR){
            showToast(context, context.getString(R.string.parse_data_error));
        }else if(code==ExceptionHandle.ERROR.HTTP_ERROR){
            showToast(context,context.getString(R.string.connect_error));
        }else {
            showToast(context, context.getString(R.string.on_server_error));
        }
    }


}
