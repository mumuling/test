package com.zhongtie.work.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhongtie.work.R;
import com.zhongtie.work.util.image.ImageLoader;
import com.zhongtie.work.util.image.ImageLoaderUtil;

import static com.zhongtie.work.ui.safe.item.CreatePicItemView.HTTP;

/**
 * Auth: Chaek
 * Date: 2018/1/9
 */

public class BaseImageView extends SimpleDraweeView {
    public BaseImageView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public BaseImageView(Context context) {
        super(context);
    }

    public BaseImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void loadImage(String data) {
        loadImage(data, 40);
    }

    public void loadImage(String data, int width) {
        ImageLoader imageLoader = new ImageLoader.Builder().url(data.startsWith(HTTP) ? data : "file://" + data).placeHolder(R.drawable.portrait).
                imgView(this).size(width, width).build();
        ImageLoaderUtil.getInstance().loadImage(getContext(), imageLoader);
    }
}
