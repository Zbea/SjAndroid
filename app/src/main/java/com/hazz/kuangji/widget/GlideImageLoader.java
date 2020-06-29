package com.hazz.kuangji.widget;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hazz.kuangji.R;
import com.hazz.kuangji.utils.GlideRoundTransform;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .transform(new GlideRoundTransform(8))
                .error(R.mipmap.banner1);
        Glide.with(context).load(path).apply(options).into(imageView);
    }
}
