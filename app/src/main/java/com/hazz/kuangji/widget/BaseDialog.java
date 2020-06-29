package com.hazz.kuangji.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hazz.kuangji.R;


public class BaseDialog extends Dialog {

    private int itemLayoutId;

    /**
     * 无高度设置、居中显示
     *
     * @param mContext
     * @param view
     * @return
     */
    public static BaseDialog showDialog(Activity mContext, int view) {
        BaseDialog dialog = new BaseDialog(mContext, R.style.SubmitDialog, view);
        dialog.show();
        return dialog;
    }

    /**
     * 距离头部、底部的高度
     *
     * @param mContext
     * @param view
     * @param y
     * @return
     */
    public static BaseDialog showDialog(Activity mContext, int view, int mGravity, int y) {
        BaseDialog dialog = new BaseDialog(mContext, R.style.SubmitDialog, view);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(mGravity);
        lp.y = y; // 新位置Y坐标
        dialogWindow.setAttributes(lp);
        dialog.show();
        return dialog;
    }

    public BaseDialog(Context context, int theme, int itemLayoutId) {
        super(context, theme);
        this.itemLayoutId = itemLayoutId;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(itemLayoutId);
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = null;
        view = findViewById(viewId);
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public BaseDialog setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }
}
