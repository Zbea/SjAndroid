package com.hazz.kuangji.widget

import android.app.Activity
import android.app.Dialog
import android.content.Context

import android.view.*
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorInt
import com.hazz.kuangji.R
import com.hazz.kuangji.utils.SToast


/**
 * Description:
 * Data：2018/12/24-15:40
 * Author: cwh
 */
class SafeCheckDialog(var context: Context) {

    init {
        createDialog(context)
    }

    lateinit var mDialog: Dialog
    var mTvTitle: TextView? = null
    var mTvCancel: TextView? = null
    var mTvConfirm: TextView? = null
    var mTvForget: TextView? = null
    var mEt: EditText? = null


    private fun createDialog(context: Context) {
        mDialog = Dialog(context)
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_safe_check, null)
        mTvTitle = view.findViewById(R.id.mTvCheckTitle)
        mTvCancel = view.findViewById(R.id.mTvCheckCancel)
        mTvConfirm = view.findViewById(R.id.mTvCheckConfirm)
        mTvForget= view.findViewById(R.id.mTvForget)
        mEt = view.findViewById(R.id.mEtCheck)
        mDialog.setContentView(view)
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.setCancelable(true)
        mDialog.setOnDismissListener{
            mEt!!.text=null
        }
        val window = mDialog.window
        //要加上设置背景，否则dialog宽高设置无作用
        window!!.setBackgroundDrawableResource(android.R.color.transparent)
        window.decorView.setPadding(0, 0, 0, 0) //消除边距
        val layoutParams = window.attributes
        layoutParams.width = context.resources.getDimensionPixelOffset(R.dimen.dialog_wid)
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.gravity = Gravity.CENTER
        window.attributes = layoutParams
    }

    fun setEditTextInputType(inputType: Int): SafeCheckDialog {
        mEt?.let {
            it.inputType = inputType

        }
        return this
    }

    fun setTitle(title: String): SafeCheckDialog {
        mTvTitle?.let {
            it.text = title
        }
        return this
    }

    fun setTitleColoe(@ColorInt color: Int): SafeCheckDialog {
        mTvTitle?.let {
            it.setTextColor(color)
        }
        return this
    }

    fun setCancelText(cancel: String): SafeCheckDialog {
        mTvCancel?.let {
            it.text = cancel
        }
        return this
    }

    fun setCancelColor(@ColorInt color: Int): SafeCheckDialog {
        mTvCancel?.let {
            it.setTextColor(color)
        }
        return this
    }

    fun setConfirmText(confirmText: String): SafeCheckDialog {
        mTvConfirm?.let {
            it.text = confirmText
        }
        return this
    }

    fun setConfirmColor(@ColorInt color: Int): SafeCheckDialog {
        mTvConfirm?.let {
            it.setTextColor(color)
        }
        return this
    }

    fun setCancelListener(cancelListener: (View) -> Unit): SafeCheckDialog {
        mTvCancel?.let {
            it.setOnClickListener {
                dismiss()
                cancelListener(it)
            }
        }
        return this
    }

    fun setForgetListener(forgetListener: (View) -> Unit): SafeCheckDialog {
        mTvForget?.let {
            it.setOnClickListener {
                dismiss()
                forgetListener(it)
            }
        }
        return this
    }

    fun setConfirmListener(confirmListener: (View,String) -> Unit): SafeCheckDialog {
        mTvConfirm?.let { it ->
            it.setOnClickListener {
                val text=mEt!!.text?.toString()?:""
                if(text.isEmpty()){
                    SToast.showText(context.getString(R.string.input_deal_password))
                    return@setOnClickListener
                }
                dismiss()
                confirmListener(it, text)
            }
        }
        return this
    }

    fun show() {
        val activity: Activity = context as Activity
        if (activity != null && !activity.isDestroyed && !activity.isFinishing &&
                mDialog != null && !mDialog.isShowing) {
            mDialog.show()
        }
    }

    fun dismiss() {
        val activity: Activity = context as Activity
        if (activity != null && !activity.isDestroyed && !activity.isFinishing &&
                mDialog != null && mDialog.isShowing) {
            mDialog.dismiss()
        }
    }


}