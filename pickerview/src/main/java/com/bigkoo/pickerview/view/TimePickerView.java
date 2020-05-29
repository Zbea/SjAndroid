package com.bigkoo.pickerview.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.bigkoo.pickerview.R;
import com.bigkoo.pickerview.configure.PickerOptions;
import com.bigkoo.pickerview.listener.ISelectTimeCallback;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间选择器
 * Created by Sai on 15/11/22.
 * Updated by XiaoSong on 2017-2-22.
 */
public class TimePickerView extends BasePickerView implements View.OnClickListener {

    private WheelTime wheelTime; //自定义控件
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";
    private static final String TAG_START = "start";
    private static final String TAG_END = "end";
    private TextView mTv_start;
    private TextView mTv_end;
    private Context mContext;
    private boolean isStart = true;

    public TimePickerView(PickerOptions pickerOptions) {
        super(pickerOptions.context);
        Log.d("junjun", "执行");
        mPickerOptions = pickerOptions;
        mContext = pickerOptions.context;
        initView(pickerOptions.context);
    }

    private void initView(Context context) {
        setDialogOutSideCancelable();
        initViews();
        initAnim();

        if (mPickerOptions.customListener == null) {
            LayoutInflater.from(context).inflate(R.layout.pickerview_time, contentContainer);

            //顶部标题
            TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
            RelativeLayout rv_top_bar = (RelativeLayout) findViewById(R.id.rv_topbar);

            //确定和取消按钮
            Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
            Button btnCancel = (Button) findViewById(R.id.btnCancel);
            mTv_start = (TextView) findViewById(R.id.tv_start);
            mTv_end = (TextView) findViewById(R.id.tv_end);


            btnSubmit.setTag(TAG_SUBMIT);
            btnCancel.setTag(TAG_CANCEL);
            mTv_start.setTag(TAG_START);
            mTv_end.setTag(TAG_END);

            btnSubmit.setOnClickListener(this);
            btnCancel.setOnClickListener(this);
            mTv_start.setOnClickListener(this);
            mTv_end.setOnClickListener(this);

            //设置文字
            btnSubmit.setText(TextUtils.isEmpty(mPickerOptions.textContentConfirm) ? context.getResources().getString(R.string.pickerview_submit) : mPickerOptions.textContentConfirm);
            btnCancel.setText(TextUtils.isEmpty(mPickerOptions.textContentCancel) ? context.getResources().getString(R.string.pickerview_cancel) : mPickerOptions.textContentCancel);
            tvTitle.setText(TextUtils.isEmpty(mPickerOptions.textContentTitle) ? "" : mPickerOptions.textContentTitle);//默认为空

            //设置color
//            btnSubmit.setTextColor(mPickerOptions.textColorConfirm);
//            btnCancel.setTextColor(mPickerOptions.textColorCancel);
//            tvTitle.setTextColor(mPickerOptions.textColorTitle);
//            rv_top_bar.setBackgroundColor(mPickerOptions.bgColorTitle);

            //设置文字大小
            btnSubmit.setTextSize(mPickerOptions.textSizeSubmitCancel);
            btnCancel.setTextSize(mPickerOptions.textSizeSubmitCancel);
            tvTitle.setTextSize(mPickerOptions.textSizeTitle);

        } else {
            mPickerOptions.customListener.customLayout(LayoutInflater.from(context).inflate(mPickerOptions.layoutRes, contentContainer));
        }
        // 时间转轮 自定义控件
        LinearLayout timePickerView = (LinearLayout) findViewById(R.id.timepicker);
        timePickerView.setBackgroundColor(mPickerOptions.bgColorWheel);

        initWheelTime(timePickerView);


    }

    private void initWheelTime(LinearLayout timePickerView) {
        wheelTime = new WheelTime(timePickerView, mPickerOptions.type, mPickerOptions.textGravity, mPickerOptions.textSizeContent);


        wheelTime.setLunarMode(mPickerOptions.isLunarCalendar);

        if (mPickerOptions.startYear != 0 && mPickerOptions.endYear != 0
                && mPickerOptions.startYear <= mPickerOptions.endYear) {
            setRange();
        }

        //若手动设置了时间范围限制
        if (mPickerOptions.startDate != null && mPickerOptions.endDate != null) {
            if (mPickerOptions.startDate.getTimeInMillis() > mPickerOptions.endDate.getTimeInMillis()) {
                throw new IllegalArgumentException("startDate can't be later than endDate");
            } else {
                setRangDate();
            }
        } else if (mPickerOptions.startDate != null) {
            if (mPickerOptions.startDate.get(Calendar.YEAR) < 1900) {
                throw new IllegalArgumentException("The startDate can not as early as 1900");
            } else {
                setRangDate();
            }
        } else if (mPickerOptions.endDate != null) {
            if (mPickerOptions.endDate.get(Calendar.YEAR) > 2100) {
                throw new IllegalArgumentException("The endDate should not be later than 2100");
            } else {
                setRangDate();
            }
        } else {//没有设置时间范围限制，则会使用默认范围。
            setRangDate();
        }

        setTime();
        wheelTime.setLabels(mPickerOptions.label_year, mPickerOptions.label_month, mPickerOptions.label_day
                , mPickerOptions.label_hours, mPickerOptions.label_minutes, mPickerOptions.label_seconds);
        wheelTime.setTextXOffset(mPickerOptions.x_offset_year, mPickerOptions.x_offset_month, mPickerOptions.x_offset_day,
                mPickerOptions.x_offset_hours, mPickerOptions.x_offset_minutes, mPickerOptions.x_offset_seconds);
        wheelTime.setItemsVisible(mPickerOptions.itemsVisibleCount);
        wheelTime.setAlphaGradient(mPickerOptions.isAlphaGradient);
        //setOutSideCancelable(mPickerOptions.cancelable);
        wheelTime.setCyclic(mPickerOptions.cyclic);
        wheelTime.setDividerColor(mPickerOptions.dividerColor);
        wheelTime.setDividerType(mPickerOptions.dividerType);
        wheelTime.setLineSpacingMultiplier(mPickerOptions.lineSpacingMultiplier);
        wheelTime.setTextColorOut(mPickerOptions.textColorOut);
        wheelTime.setTextColorCenter(mPickerOptions.textColorCenter);
        wheelTime.isCenterLabel(mPickerOptions.isCenterLabel);

    }


    /**
     * 设置默认时间
     */
    public void setDate(Calendar date) {
        mPickerOptions.date = date;
        setTime();
    }

    /**
     * 设置可以选择的时间范围, 要在setTime之前调用才有效果
     */
    private void setRange() {
        wheelTime.setStartYear(mPickerOptions.startYear);
        wheelTime.setEndYear(mPickerOptions.endYear);

    }

    /**
     * 设置可以选择的时间范围, 要在setTime之前调用才有效果
     */
    private void setRangDate() {
        wheelTime.setRangDate(mPickerOptions.startDate, mPickerOptions.endDate);
        initDefaultSelectedDate();
    }

    private void initDefaultSelectedDate() {
        //如果手动设置了时间范围
        if (mPickerOptions.startDate != null && mPickerOptions.endDate != null) {
            //若默认时间未设置，或者设置的默认时间越界了，则设置默认选中时间为开始时间。
            if (mPickerOptions.date == null || mPickerOptions.date.getTimeInMillis() < mPickerOptions.startDate.getTimeInMillis()
                    || mPickerOptions.date.getTimeInMillis() > mPickerOptions.endDate.getTimeInMillis()) {
                mPickerOptions.date = mPickerOptions.startDate;
            }
        } else if (mPickerOptions.startDate != null) {
            //没有设置默认选中时间,那就拿开始时间当默认时间
            mPickerOptions.date = mPickerOptions.startDate;
        } else if (mPickerOptions.endDate != null) {
            mPickerOptions.date = mPickerOptions.endDate;
        }
    }

    /**
     * 设置选中时间,默认选中当前时间
     */
    private void setTime() {
        int year, month, day, hours, minute, seconds;
        Calendar calendar = Calendar.getInstance();

        if (mPickerOptions.date == null) {
            calendar.setTimeInMillis(System.currentTimeMillis());
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            hours = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);
            seconds = calendar.get(Calendar.SECOND);
        } else {
            year = mPickerOptions.date.get(Calendar.YEAR);
            month = mPickerOptions.date.get(Calendar.MONTH);
            day = mPickerOptions.date.get(Calendar.DAY_OF_MONTH);
            hours = mPickerOptions.date.get(Calendar.HOUR_OF_DAY);
            minute = mPickerOptions.date.get(Calendar.MINUTE);
            seconds = mPickerOptions.date.get(Calendar.SECOND);
        }

        wheelTime.setPicker(year, month, day, hours, minute, seconds);

        mTv_start.setText(wheelTime.getTime());
        mTv_end.setText(wheelTime.getTime());


        wheelTime.setSelectChangeCallback(new ISelectTimeCallback() {
            @Override
            public void onTimeSelectChanged() {

                if (isStart) {

                    mTv_start.setText(wheelTime.getTime());
                } else {
                    mTv_end.setText(wheelTime.getTime());
                }

            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_SUBMIT)) {
            returnData();
            dismiss();
        } else if (tag.equals(TAG_CANCEL)) {
            if (mPickerOptions.cancelListener != null) {
                mPickerOptions.cancelListener.onClick(v);
            }
            dismiss();
        } else if (tag.equals(TAG_START)) {
            isStart = true;
            mTv_start.setBackground(mContext.getResources().getDrawable(R.drawable.bg_blue));
            mTv_start.setTextColor(Color.parseColor("#5390FF"));
            mTv_end.setBackground(mContext.getResources().getDrawable(R.drawable.bg_gray));
            mTv_end.setTextColor(Color.parseColor("#a5b1c8"));
        } else if (tag.equals(TAG_END)) {
            isStart = false;
            mTv_end.setBackground(mContext.getResources().getDrawable(R.drawable.bg_blue));
            mTv_end.setTextColor(Color.parseColor("#5390FF"));
            mTv_start.setBackground(mContext.getResources().getDrawable(R.drawable.bg_gray));
            mTv_start.setTextColor(Color.parseColor("#a5b1c8"));
        }

    }

    public void returnData() {
        if (mPickerOptions.timeSelectListener != null) {

            mPickerOptions.timeSelectListener.onTimeSelect(mTv_start.getText().toString(), mTv_end.getText().toString());


        }
    }

    /**
     * 动态设置标题
     *
     * @param text 标题文本内容
     */
    public void setTitleText(String text) {
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        if (tvTitle != null) {
            tvTitle.setText(text);
        }
    }

    /**
     * 目前暂时只支持设置1900 - 2100年
     *
     * @param lunar 农历的开关
     */
    public void setLunarCalendar(boolean lunar) {
        try {
            int year, month, day, hours, minute, seconds;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(WheelTime.dateFormat.parse(wheelTime.getTime()));
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            hours = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);
            seconds = calendar.get(Calendar.SECOND);

            wheelTime.setLunarMode(lunar);
            wheelTime.setLabels(mPickerOptions.label_year, mPickerOptions.label_month, mPickerOptions.label_day,
                    mPickerOptions.label_hours, mPickerOptions.label_minutes, mPickerOptions.label_seconds);
            wheelTime.setPicker(year, month, day, hours, minute, seconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public boolean isLunarCalendar() {
        return wheelTime.isLunarMode();
    }


    @Override
    public boolean isDialog() {
        return mPickerOptions.isDialog;
    }
}
