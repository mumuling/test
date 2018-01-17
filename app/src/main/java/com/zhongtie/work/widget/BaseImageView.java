package com.zhongtie.work.widget;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.zhongtie.work.R;
import com.zhongtie.work.util.TextUtil;
import com.zhongtie.work.util.image.ImageLoader;
import com.zhongtie.work.util.image.ImageLoaderUtil;

import static com.zhongtie.work.ui.safe.item.CreatePicItemView.HTTP;

/**+
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
        if (TextUtil.isEmpty(data)) {
            data = "";
        }
        ImageLoader imageLoader = new ImageLoader.Builder().url(data.startsWith(HTTP) ? data : "file://" + data).placeHolder(R.drawable.portrait).
                imgView(this).size(width, width).build();
        ImageLoaderUtil.getInstance().loadImage(getContext(), imageLoader);
    }

    /**
     * 加载本地文件夹
     * @param userId
     */
    public void loadImag(int userId) {
//        String userName=
        Uri lowResUri, highResUri;
//        DraweeController controller = Fresco.newDraweeControllerBuilder()
//                .setLowResImageRequest(ImageRequest.fromUri(lowResUri))
//                .setImageRequest(ImageRequest.fromUri(highResUri))
//                .setOldController(getController())
//                .build();
//        setController(controller);
    }
}
