package io.playcode.streambox.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by anpoz on 2017/4/12.
 */

public class ImageLoader {
    public static void with(Context context, String imageUrl, ImageView imageView) {
        Glide.with(context)
                .load(imageUrl)
                .crossFade()
                .into(imageView);
    }

    public static void withCenterCrop(Context context, String imageUrl, ImageView imageView) {
        Glide.with(context)
                .load(imageUrl)
                .crossFade()
                .centerCrop()
                .into(imageView);
    }
}
