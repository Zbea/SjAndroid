package com.hazz.kuangji.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hazz.kuangji.R;
import com.hazz.kuangji.utils.DpUtils;


public class TipsDialog {

    Context context;
    Dialog dialog;
    TextView mTvTitle, mTvContent;
    Button mBtnCancle;
    Button mBtnConfirm;
    LinearLayout ll_bt;
    View mPartLine;
    View mPartLine1;
    ImageView close;
    private View mViewm;

    public void setCancleText() {
    }

    public interface OnConfirmListener {
        void onConfirm(View view);
    }

    public interface OnCancleListener {
        void onCancle(View view);
    }


    public TipsDialog(Context context) {
        this.context = context;
        createDialog();
    }

    public TipsDialog createDialog() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mViewm = View.inflate(context, R.layout.dialog_tips, null);
        mTvTitle = (TextView) mViewm.findViewById(R.id.title);
        mTvContent = mViewm.findViewById(R.id.mTvContent);
        ll_bt = mViewm.findViewById(R.id.ll_bt);
        mBtnConfirm = (Button) mViewm.findViewById(R.id.btn_confirm);
        mBtnCancle = (Button) mViewm.findViewById(R.id.btn_cancle);
        mPartLine = mViewm.findViewById(R.id.line_part);
        mPartLine1= mViewm.findViewById(R.id.view1);
        close = mViewm.findViewById(R.id.iv_close);
        dialog.setContentView(mViewm);
        dialog.setCanceledOnTouchOutside(false);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        Window window = dialog.getWindow();
        //要加上设置背景，否则dialog宽高设置无作用
        window.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = DpUtils.dip2px(context, 275);
        // layoutParams.height = DensityUtils.dip2px(context, 140);
        window.setAttributes(layoutParams);
        return this;
    }

    public TipsDialog setCancelable(boolean cancelable) {
        dialog.setCancelable(cancelable);
        return this;
    }

    public TipsDialog setTitle(String title) {
        mTvTitle.setText(title);
        return this;
    }

    public TipsDialog setContent(String content) {
        mTvContent.setText(content);
        return this;
    }

    public TipsDialog setTitleTextSize(float size) {
        mTvTitle.setTextSize(size);
        return this;
    }

    public TipsDialog setTitleColor(int color) {
        mTvTitle.setTextColor(color);
        return this;
    }

    public TipsDialog setConfirmText(String text) {
        mBtnConfirm.setText(text);
        return this;
    }

    public TipsDialog setCancleText(String text) {
        mBtnCancle.setText(text);
        return this;
    }

    public TipsDialog sign() {
        mPartLine1.setVisibility(View.GONE);
        mPartLine.setVisibility(View.GONE);
        mBtnCancle.setVisibility(View.GONE);
        mBtnConfirm.setTextColor(context.getResources().getColor(R.color.color_white));
        mBtnConfirm.setBackgroundResource(R.drawable.bg_blue_solid_5dp_coner);

        return this;
    }

    public TipsDialog rule() {

        mPartLine.setVisibility(View.GONE);
        ll_bt.setVisibility(View.GONE);


        return this;
    }

    public TipsDialog setConfirmColor(int color) {
        mBtnConfirm.setTextColor(color);
        return this;
    }

    public TipsDialog setCancleColor(int color) {
        mBtnCancle.setTextColor(color);
        return this;
    }

    public TipsDialog setConfirmListener(final OnConfirmListener onConfirmListener) {
        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (onConfirmListener != null) {
                    onConfirmListener.onConfirm(v);
                }
            }
        });
        return this;
    }

    public TipsDialog setCancleListener(final OnCancleListener onCancleListener) {
        mBtnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (onCancleListener != null) {
                    onCancleListener.onCancle(v);
                }
            }
        });
        return this;
    }

    public void show() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
