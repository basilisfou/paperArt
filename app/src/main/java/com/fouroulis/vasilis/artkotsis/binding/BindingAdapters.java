package com.fouroulis.vasilis.artkotsis.binding;

import android.databinding.BindingAdapter;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fouroulis.vasilis.artkotsis.GlideApp;
import com.fouroulis.vasilis.artkotsis.utils.FontCache;

/**
 * Created by Vasilis Fouroulis on 1/16/2018.
 * Email: vasilisfouroulis@gmail.com .
 */

public class BindingAdapters {

    @BindingAdapter({"app:font"})
    public static void setFont(TextView textView, String fontName) {
        textView.setTypeface(FontCache.get("fonts/"+ fontName,textView.getContext()));
    }

    @BindingAdapter("showHide")
    public static void setVisibility(View view, boolean show) {
        if (show) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    @BindingAdapter({"app:setImage"})
    public static void setImage(ImageView imageView, String image){
        GlideApp.with(imageView.getContext())
                .load(Uri.parse("file:///android_asset/images/"+ image))
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE )
                .into(imageView);
    }
}
