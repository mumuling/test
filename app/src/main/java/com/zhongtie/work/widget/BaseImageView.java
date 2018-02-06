package com.zhongtie.work.widget;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.AttributeSet;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.zhongtie.work.R;
import com.zhongtie.work.db.CompanyUserData;
import com.zhongtie.work.db.CompanyUserData_Table;
import com.zhongtie.work.util.TextUtil;
import com.zhongtie.work.util.Util;
import com.zhongtie.work.util.image.ImageLoader;
import com.zhongtie.work.util.image.ImageLoaderUtil;

import java.io.File;

import io.reactivex.Flowable;
import io.reactivex.functions.BiFunction;

import static com.zhongtie.work.ui.safe.item.CreatePicItemView.HTTP;

/**
 * Date: 2018/1/9
 *
 * @author Chaek
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
        ImageLoader imageLoader = new ImageLoader.Builder().url(data.startsWith(HTTP) ? getMiniUrl(data) : "file://" + data).placeHolder(R.drawable.portrait).
                imgView(this).size(width, width).build();
        ImageLoaderUtil.getInstance().loadImage(getContext(), imageLoader);
    }


    public void loadImageSign(String data) {
        if (TextUtil.isEmpty(data)) {
            data = "";
        }
        ImageLoader imageLoader = new ImageLoader.Builder().url(data.startsWith(HTTP) ? data : "file://" + data).placeHolder(R.drawable.ic_def).
                imgView(this).build();
        ImageLoaderUtil.getInstance().loadImage(getContext(), imageLoader);
    }


    public void loadImagePic(String data) {
        if (TextUtil.isEmpty(data)) {
            data = "";
        }
        setImageURI(Uri.parse(data.startsWith(HTTP) ? getMiniUrl(data) : "file://" + data));
    }

    private String getMiniUrl(String data) {
        return data.replace(".jpg", "_mini.jpg");
    }

    /**
     * 根据用户ID加载本地文件夹照片
     *
     * @param userId 用户ID
     */
    public void loadImage(int userId) {
        //数据库查询出身份证号码
        Flowable.zip(Flowable.just(userId).map(integer -> SQLite.select().from(CompanyUserData.class)
                .where(CompanyUserData_Table.id.eq(integer))
                .querySingle()), Flowable.just(this), (BiFunction<CompanyUserData, BaseImageView, Object>) (companyUserData, baseImageView) -> {
            baseImageView.loadUserCard(companyUserData.getIdencode());
            return "";
        }).subscribe(o -> {
        }, throwable -> {
        });


    }

    /**
     * 根据身份证号码获取离线图片
     *
     * @param card 身份证号码
     */
    public void loadUserCard(String card) {
        ((Activity) getContext()).runOnUiThread(() -> {
            String file = Environment.getExternalStorageDirectory().getPath() + "/zhongtie/user_image/";
            File file2 = new File(file, Util.md532(card));
            Uri lowResUri, highResUri;
            lowResUri = Uri.parse("file://" + file2.toString());
            highResUri = Uri.parse("file://" + file2.toString());
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setLowResImageRequest(ImageRequest.fromUri(lowResUri))
                    .setImageRequest(ImageRequest.fromUri(highResUri))
                    .setOldController(getController())
                    .build();
            setController(controller);
            setTag(card);
        });

    }
}
