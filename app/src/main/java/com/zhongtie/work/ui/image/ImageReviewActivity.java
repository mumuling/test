package com.zhongtie.work.ui.image;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.zhongtie.work.R;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.ui.base.BaseActivity;
import com.zhongtie.work.util.FileUtils;
import com.zhongtie.work.util.TextUtil;
import com.zhongtie.work.widget.MultiTouchViewPager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import me.relex.photodraweeview.PhotoDraweeView;

import static com.zhongtie.work.util.FileUtils.ZHONGTIE_DOWNLOAD;

/**
 * 图片预览界面
 * @author: Chaek
 * @date: 2018/1/22
 */

public class ImageReviewActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private static final String KEY_IMAGE_LIST = "image_list";
    private static final String KEY_POSITION = "position";

    private MultiTouchViewPager mViewPager;
    private List<String> mImageList = new ArrayList<>();

    public static void start(Context context, int position, List<Object> imageList) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < imageList.size(); i++) {
            list.add((String) imageList.get(i));
        }
        Intent starter = new Intent(context, ImageReviewActivity.class);
        starter.putExtra(KEY_POSITION, position);
        starter.putStringArrayListExtra(KEY_IMAGE_LIST, (ArrayList<String>) list);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.activity_image_review;
    }

    @Override
    protected void initView() {
        mViewPager = findViewById(R.id.view_pager);
        LinearLayout imageDownLayout = findViewById(R.id.image_down_layout);
        imageDownLayout.setOnClickListener(v -> downCurentItem());


    }


    /**
     * 根据url获取图片
     */
    private void downCurentItem() {
        Flowable.create(new FlowableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(FlowableEmitter<Bitmap> emitter) throws Exception {
                ImageRequest imageRequest = ImageRequestBuilder
                        .newBuilderWithSource(TextUtil.fromUri(mImageList.get(mViewPager.getCurrentItem())))
                        .setProgressiveRenderingEnabled(true)
                        .build();
                ImagePipeline imagePipeline = Fresco.getImagePipeline();
                DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, this);
                dataSource.subscribe(new BaseBitmapDataSubscriber() {
                    @Override
                    public void onNewResultImpl(Bitmap bitmap) {
                        emitter.onNext(bitmap);
                        emitter.onComplete();
                    }

                    @Override
                    public void onFailureImpl(DataSource dataSource) {
                        emitter.onError(new NullPointerException(""));
                        emitter.onComplete();
                    }
                }, CallerThreadExecutor.getInstance());
            }
        }, BackpressureStrategy.ERROR).map(FileUtils::saveBitmap)
                .compose(Network.networkDialog(this, "正在下载图片"))
                .subscribe(file -> showToast("下载成功,在" + ZHONGTIE_DOWNLOAD + "文件夹查看"), throwable -> showToast("下载失败"));
    }

    @Override
    protected void initData() {
        mImageList = getIntent().getStringArrayListExtra(KEY_IMAGE_LIST);
        int currentPosition = getIntent().getIntExtra(KEY_POSITION, 0);
        DraweePagerAdapter adapter = new DraweePagerAdapter();
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(currentPosition);
        mViewPager.addOnPageChangeListener(this);

        setTitle(getString(R.string.image_review_title, currentPosition + 1, mImageList.size()));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setTitle(getString(R.string.image_review_title, position + 1, mImageList.size()));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class DraweePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup viewGroup, int position) {
            final PhotoDraweeView photoDraweeView = new PhotoDraweeView(viewGroup.getContext());
            PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
            controller.setUri(TextUtil.fromUri(mImageList.get(position)));
            controller.setOldController(photoDraweeView.getController());
            controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
                @Override
                public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                    super.onFinalImageSet(id, imageInfo, animatable);
                    if (imageInfo == null) {
                        return;
                    }
                    photoDraweeView.update(imageInfo.getWidth(), imageInfo.getHeight());
                }
            });
            photoDraweeView.setController(controller.build());
            try {
                viewGroup.addView(photoDraweeView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return photoDraweeView;
        }
    }
}
