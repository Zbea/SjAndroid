package com.hazz.kuangji.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.hazz.kuangji.R;


/**
 * Description:
 * Data：2018/10/26-14:57
 * Author: cwh
 */
public class ToolBarCustom {

    public static Builder newBuilder(@NonNull Toolbar mToolbar) {
        return new Builder(mToolbar);
    }


    public static final class Builder {
        private Toolbar mToolBar;

        public interface OnLeftIconClick {
            void onLeftClick(View view);

        }

        public interface OnRightClick {
            void onRightClick(View view);

        }
        public Builder(@NonNull Toolbar mToolBar) {
            this.mToolBar = mToolBar;
        }


        /**
         * 设置Toolbar背景颜色
         *
         * @param colorId
         * @return
         */
        public Builder setToolBarBg(@ColorInt int colorId) {
            if (mToolBar != null) {
                mToolBar.setBackgroundColor(colorId);
            }
            return this;
        }


        public Builder setLeftText(String text) {
            if (mToolBar != null) {
                TextView textView = mToolBar.findViewById(R.id.mTvLeftText);
                if (textView != null)
                    textView.setText(text);
            }
            return this;
        }

        public Builder setLeftTextIsShow(boolean isShow) {
            if (mToolBar != null) {
                TextView textView = mToolBar.findViewById(R.id.mTvLeftText);
                if (textView != null) {
                    if (isShow)
                        textView.setVisibility(View.VISIBLE);
                    else
                        textView.setVisibility(View.GONE);
                }
            }
            return this;
        }

        public Builder setLeftTextColor(@ColorInt int colorId) {
            if (mToolBar != null) {
                TextView textView = mToolBar.findViewById(R.id.mTvLeftText);
                if (textView != null)
                    textView.setTextColor(colorId);
            }
            return this;
        }

        /**
         * 设置左边图标
         *
         * @param resId
         */
        public Builder setLeftIcon(@DrawableRes int resId) {
            if (mToolBar != null) {
                ImageView imageView = mToolBar.findViewById(R.id.mImgBack);
                if (imageView != null)
                    imageView.setImageResource(resId);
            }
            return this;
        }

        /**
         * 设置toolbar左边图标是否显示
         */
        public Builder setLeftIconIsShow(boolean isShow) {
            if (mToolBar != null) {
                ImageView imageView = mToolBar.findViewById(R.id.mImgBack);
                if (imageView != null) {
                    if (isShow)
                        imageView.setVisibility(View.VISIBLE);
                    else
                        imageView.setVisibility(View.GONE);
                }
            }
            return this;
        }

        public Builder setOnLeftIconClickListener(final OnLeftIconClick listener) {
            if (mToolBar != null) {
                ImageView imageView = mToolBar.findViewById(R.id.mImgBack);
                if (imageView != null) {
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (listener != null) {
                                listener.onLeftClick(v);
                            }
                        }
                    });

                }
            }
            return this;
        }

        public Builder setOnRightClickListener(final OnRightClick listener) {
            if (mToolBar != null) {
                TextView imageView = mToolBar.findViewById(R.id.mTvRightText);
                if (imageView != null) {
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (listener != null) {
                                listener.onRightClick(v);
                            }
                        }
                    });

                }
            }
            return this;
        }
            /**
             * 设置toolbar的Title
             *
             * @param title
             */
            public Builder setTitle (@NonNull String title){
                if (mToolBar != null) {
                    TextView textView = mToolBar.findViewById(R.id.mTvTitle);
                    if (textView != null) {
                        textView.setText(title);
                    }
                }
                return this;
            }

            public Builder setTitleColor ( @ColorInt int colorId){
                if (mToolBar != null) {
                    TextView textView = mToolBar.findViewById(R.id.mTvTitle);
                    if (textView != null)
                        textView.setTextColor(colorId);
                }
                return this;
            }

            /**
             * 设置右边图标
             *
             * @param resId
             * @return
             */
            public Builder setRightOneIcon ( @DrawableRes int resId){
                if (mToolBar != null) {
                    ImageView imageView = mToolBar.findViewById(R.id.img_right_one);
                    if (imageView != null)
                        imageView.setImageResource(resId);
                }
                return this;
            }
        public Builder setOnRightIconClickListener(final OnRightClick listener) {
            if (mToolBar != null) {
                ImageView imageView = mToolBar.findViewById(R.id.img_right_one);
                if (imageView != null) {
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (listener != null) {
                                listener.onRightClick(v);
                            }
                        }
                    });

                }
            }
            return this;
        }

            /**
             * 设置右边图标是否可见
             *
             * @param isShow
             * @return
             */
            public Builder setRightOneIconIsShow ( boolean isShow){
                if (mToolBar != null) {
                    ImageView imageView = mToolBar.findViewById(R.id.img_right_one);
                    if (imageView != null) {
                        if (isShow)
                            imageView.setVisibility(View.VISIBLE);
                        else
                            imageView.setVisibility(View.GONE);
                    }
                }

                return this;
            }

            /**
             * 设置右边文字
             *
             * @param rightText
             * @return
             */
            public Builder setRightText (@NonNull String rightText){
                if (mToolBar != null) {
                    TextView textView = mToolBar.findViewById(R.id.mTvRightText);
                    if (textView != null) {
                        textView.setText(rightText);
                    }
                }
                return this;
            }

        /**
         *
         * @param colorId
         * @return
         */
        public Builder setRightTextColor(@ColorInt int colorId){
                if (mToolBar != null) {
                    TextView textView = mToolBar.findViewById(R.id.mTvRightText);
                    if (textView != null)
                        textView.setTextColor(colorId);
                }
                return this;
            }

            public Builder setRightTextIsShow ( boolean isShow){
                if (mToolBar != null) {
                    TextView textView = mToolBar.findViewById(R.id.mTvRightText);
                    if (textView != null) {
                        if (isShow)
                            textView.setVisibility(View.VISIBLE);
                        else
                            textView.setVisibility(View.GONE);
                    }
                }

                return this;
            }



            /**
             * 设置Toolbar 背景图
             *
             * @param id
             * @return
             */
            public Builder setToolBarBgRescource ( @DrawableRes int id){
                if (mToolBar != null) {
                    mToolBar.setBackgroundResource(id);
                }
                return this;

            }


        }

    }
