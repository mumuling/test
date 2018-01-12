package com.zhongtie.work.util.image;

import android.widget.ImageView;

import com.zhongtie.work.R;


/**
 * Created by Cheek on 2017.5.17.
 * ImageLoader
 */

public class ImageLoader {
    private int type;  //类型 (大图，中图，小图)
    private String url; //需要解析的url
    private int placeHolder; //当没有成功加载的时候显示的图片
    private ImageView imgView; //ImageView的实例
    private int wifiStrategy;//加载策略，是否在wifi下才加载
    private boolean isRoundAsCircle;
    private int width;
    private int height;
    private ImageLoaderListener loaderListener;

    private ImageLoader(Builder builder) {
        this.type = builder.type;
        this.url = builder.url;
        this.placeHolder = builder.placeHolder;
        this.imgView = builder.imgView;
        this.wifiStrategy = builder.wifiStrategy;
        this.isRoundAsCircle = builder.isRoundAsCircle;
        this.width = builder.width;
        this.height = builder.height;
        this.loaderListener = builder.loaderListener;
    }

    public ImageLoaderListener getLoaderListener() {
        return loaderListener;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getType() {
        return type;
    }

    public boolean isRoundAsCircle() {
        return isRoundAsCircle;
    }

    public String getUrl() {
        return url;
    }

    public int getPlaceHolder() {
        return placeHolder;
    }

    public ImageView getImgView() {
        return imgView;
    }

    public int getWifiStrategy() {
        return wifiStrategy;
    }

    public static class Builder {
        private int type;
        private String url;
        private int placeHolder;
        private int width;
        private int height;
        private ImageView imgView;
        private int wifiStrategy;
        private boolean isRoundAsCircle;
        private ImageLoaderListener loaderListener;

        public Builder() {
            this.type = 0;
            this.url = "";
            this.placeHolder = R.drawable.ic_def;
            this.imgView = null;
            this.isRoundAsCircle = false;
            this.width = 0;
            this.height = 0;
        }

        public Builder size(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder listener(ImageLoaderListener loaderListener) {
            this.loaderListener = loaderListener;
            return this;
        }

        public Builder type(int type) {
            this.type = type;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder setRoundAsCircle(boolean isRoundAsCircle) {
            this.isRoundAsCircle = isRoundAsCircle;
            if (isRoundAsCircle) {
                placeHolder = R.drawable.portrait;
            }
            return this;
        }

        public Builder placeHolder(int placeHolder) {
            this.placeHolder = placeHolder;
            return this;
        }

        public Builder imgView(ImageView imgView) {
            this.imgView = imgView;
            return this;
        }

        public Builder strategy(int strategy) {
            this.wifiStrategy = strategy;
            return this;
        }

        public ImageLoader build() {
            return new ImageLoader(this);
        }

    }
}
