package com.zhongtie.work.util.image;

import android.content.Context;

/**
 * Created by Cheek on 2017.5.17.
 */

public class ImageLoaderUtil {
    private static ImageLoaderUtil loader;
    private ImageLoaderProvider loaderProvider;

    public static ImageLoaderUtil getInstance() {
        if (loader == null) {
            synchronized (ImageLoaderUtil.class) {
                loader = new ImageLoaderUtil();
            }
        }
        return loader;
    }

    public ImageLoaderUtil() {
        loaderProvider = new FrescoImageLoaderProvider();
    }

    public void setLoaderProvider(ImageLoaderProvider loaderProvider) {
        this.loaderProvider = loaderProvider;
    }

    public void loadImage(Context context, ImageLoader imageLoader) {
        loaderProvider.loadImage(context, imageLoader);
    }
}
