package com.zhongtie.work.util.image;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.zhongtie.work.util.ResourcesUtils;

/**
 * Created by Cheek on 2017.5.17.
 * Fresco 图片加载封装
 */

class FrescoImageLoaderProvider implements ImageLoaderProvider {

    @Override
    public void loadImage(Context ctx, final ImageLoader img) {
        if (img == null) {
            throw new NullPointerException("ImageLoader not null");
        }

        SimpleDraweeView v = (SimpleDraweeView) img.getImgView();
        GenericDraweeHierarchy hierarchy = v.getHierarchy();
        //是否是圆角
        if (img.isRoundAsCircle()) {
            RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
            roundingParams.setRoundAsCircle(true);
            hierarchy.setRoundingParams(roundingParams);
        }
        //加载替代图片
        hierarchy.setFailureImage(img.getPlaceHolder());
        hierarchy.setPlaceholderImage(img.getPlaceHolder());
        v.setHierarchy(hierarchy);
        String url = img.getUrl() == null ? "" : img.getUrl();
        ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url));
        if (img.getWidth() > 0) {
            ResizeOptions resizeOptions = new ResizeOptions(ResourcesUtils.dip2px(img.getWidth()), ResourcesUtils.dip2px(img.getHeight()));
            imageRequestBuilder.setResizeOptions(resizeOptions);
        }
        ImageRequest request = imageRequestBuilder.build();
        AbstractDraweeController abstractDraweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(v.getController())
                //加载监听
                .setControllerListener(new ControllerListener<ImageInfo>() {
                    @Override
                    public void onSubmit(String id, Object callerContext) {

                    }

                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        if (img.getLoaderListener() != null) {
                            img.getLoaderListener().loaderSuccess(id);
                        }

                    }

                    @Override
                    public void onIntermediateImageSet(String id, ImageInfo imageInfo) {

                    }


                    @Override
                    public void onIntermediateImageFailed(String id, Throwable throwable) {

                    }

                    @Override
                    public void onFailure(String id, Throwable throwable) {
                        if (img.getLoaderListener() != null) {
                            img.getLoaderListener().loaderFail();
                        }
                    }

                    @Override
                    public void onRelease(String id) {

                    }
                })
                .build();
        v.setController(abstractDraweeController);
    }


}
