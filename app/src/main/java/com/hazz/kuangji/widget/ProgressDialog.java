package com.hazz.kuangji.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hazz.kuangji.R;


/**
 * Description:
 * Data：2018/10/31-15:00
 * Author: cwh
 */
public class ProgressDialog {


    Context context;
    Dialog mDialog;

    public ProgressDialog(Context context) {
        this.context = context;
        createDialog();
    }

    public void createDialog() {
        mDialog = new Dialog(context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = View.inflate(context, R.layout.dialog_progress, null);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);
        Window window = mDialog.getWindow();
        //要加上设置背景，否则dialog宽高设置无作用
        window.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        //设置背景不变暗
        layoutParams.dimAmount=0f;
        WindowManager wm = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();  //屏幕宽
        int height = wm.getDefaultDisplay().getHeight();  //屏幕高
        layoutParams.width = (int) (1 * width / 3);
        layoutParams.height = (int) (1 * width /4);
        window.setAttributes(layoutParams);
    }



    public void show() {
        Activity activity= (Activity) context;
        if (activity!=null && !activity.isFinishing() && !activity.isDestroyed() &&
                mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
    }



    public void dismiss() {
        Activity activity= (Activity) context;
        if (activity!=null && !activity.isFinishing() && !activity.isDestroyed() &&
                mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

}
