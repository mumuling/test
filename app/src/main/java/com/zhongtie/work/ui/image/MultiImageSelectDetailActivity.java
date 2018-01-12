package com.zhongtie.work.ui.image;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;
import com.zhongtie.work.R;
import com.zhongtie.work.app.Constant;
import com.zhongtie.work.event.EventData;
import com.zhongtie.work.ui.base.BaseActivity;
import com.zhongtie.work.util.L;
import com.zhongtie.work.widget.MultiTouchViewPager;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.relex.photodraweeview.PhotoDraweeView;


/**
 * 查看图片详情 打开图片
 */
public class MultiImageSelectDetailActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private static final String TAG = "MultiImageSelectDetailActivity";
    public static final String IMG_LIST_PATH = "list";
    public static final String DEFAULT_INDEX = "defaultIndex";
    public static final String SELECT_LIST = "select_list";

    private List<Image> mDrawables;
    private List<String> mSelectImageList;
    private int defaultIndex;
    private ImageView mCheckMark;
    private TextView mDoneBtn;

    @Override
    protected void initData() {
        setTitle("选择图片");
    }

    /**
     * Update done button by select image data
     *
     * @param resultList selected image data
     */
    private void updateDoneText(ArrayList<String> resultList) {
        int size = 0;
        if (resultList == null || resultList.size() <= 0) {
            mDoneBtn.setText(R.string.mis_action_done);
            mDoneBtn.setEnabled(false);
        } else {
            size = resultList.size();
            mDoneBtn.setEnabled(true);
        }
        mDoneBtn.setText(getString(R.string.image_select_btn,
                size, selectImageCount()));
    }

    /**
     * notify callback
     *
     * @param image image data
     */
    private void selectImageFromGrid(Image image) {
        if (image != null) {
            if (selectMode() == MultiImageSelectorFragment.MODE_MULTI) {
                if (mSelectImageList.contains(image.path)) {
                    mSelectImageList.remove(image.path);
                    EventBus.getDefault().post(new EventData(Constant.IMAGE_SELECT_REMOVE, image));
                    mCheckMark.setImageResource(R.drawable.mis_check_selected);
                } else {
                    if (selectImageCount() == mSelectImageList.size()) {
                        showToast(R.string.mis_msg_amount_limit);
                        return;
                    }
                    mSelectImageList.add(image.path);
                    EventBus.getDefault().post(new EventData(Constant.IMAGE_SELECT_ADD, image));
                    mCheckMark.setImageResource(R.drawable.mis_check_unselected);
                }
                updateDoneText((ArrayList<String>) mSelectImageList);
            }
        }
    }


    private int selectMode() {
        return getIntent().getIntExtra(MultiImageSelectorFragment.EXTRA_SELECT_MODE, MultiImageSelectorFragment.MODE_MULTI);
    }

    private int selectImageCount() {
        return getIntent().getIntExtra(MultiImageSelectorFragment.EXTRA_SELECT_COUNT, 9);
    }

    @Override
    protected int getLayoutViewId() {
        mDrawables = getIntent().getParcelableArrayListExtra(IMG_LIST_PATH);
        mSelectImageList = getIntent().getStringArrayListExtra(SELECT_LIST);
        if (mSelectImageList == null) {
            mSelectImageList = new ArrayList<>();
        }
        defaultIndex = getIntent().getIntExtra(DEFAULT_INDEX, 0);
        return R.layout.activity_viewpager;
    }

    @Override
    protected void initView() {
        MultiTouchViewPager viewPager = (MultiTouchViewPager) findViewById(R.id.view_pager);
        LinearLayout mImageChoose = (LinearLayout) findViewById(R.id.image_choose);
        mDoneBtn=findViewById(R.id.submit);
        mCheckMark = (ImageView) findViewById(R.id.checkmark);
        mImageChoose.setOnClickListener(this);
        findViewById(R.id.image_choose_bottom).setVisibility(selectMode() == MultiImageSelectorFragment.MODE_SINGLE ? View.GONE : View.VISIBLE);
        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(new DraweePagerAdapter());
        viewPager.setCurrentItem(defaultIndex);

        if (selectMode() == MultiImageSelectorFragment.MODE_MULTI) {
            mDoneBtn.setVisibility(View.VISIBLE);
            mDoneBtn.setOnClickListener(view -> {
                if (mSelectImageList != null && mSelectImageList.size() > 0) {
                    EventBus.getDefault().post(new EventData(Constant.IMAGE_SELECT_COM, mSelectImageList));
                    finish();
                } else {
                    setResult(RESULT_CANCELED);
                    EventBus.getDefault().post(new EventData(Constant.IMAGE_SELECT_CANCEL));
                    finish();
                }

            });
        } else {
            mDoneBtn.setText(R.string.confirm);
            mDoneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new EventData(Constant.IMAGE_SELECT_ADD, mDrawables.get(defaultIndex)));
                    finish();
                }
            });
        }
        updateDoneText((ArrayList<String>) mSelectImageList);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.image_choose:
                selectImageFromGrid(mDrawables.get(defaultIndex));
                break;
        }
    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        defaultIndex = position;
        if (mSelectImageList != null && mSelectImageList.contains(mDrawables.get(position).path)) {
            mCheckMark.setImageResource(R.drawable.mis_check_unselected);
        } else {
            mCheckMark.setImageResource(R.drawable.mis_check_selected);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class DraweePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mDrawables.size();
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
            controller.setUri(Uri.fromFile(new File(mDrawables.get(position).path)));
            controller.setOldController(photoDraweeView.getController());
            controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
                @Override
                public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                    super.onFinalImageSet(id, imageInfo, animatable);
                    if (imageInfo == null) {
                        return;
                    }
                    L.i(TAG, imageInfo.getWidth() + "-" + imageInfo.getHeight());
                    photoDraweeView.update(imageInfo.getWidth(), imageInfo.getHeight());
                }
            });
            L.i(TAG, mDrawables.get(position).path);
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
