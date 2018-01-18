package com.zhongtie.work.ui.image;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.app.Constant;
import com.zhongtie.work.event.EventData;
import com.zhongtie.work.ui.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


/**
 * Multi image selector
 */
public class MultiImageSelectorActivity extends BaseActivity implements MultiImageSelectorFragment.Callback {
    private static final String TAG = "MultiImageSelectorActiv";

    // Single choice
    public static final int MODE_SINGLE = 0;
    // Multi choicez
    public static final int MODE_MULTI = 1;

    /**
     * Max image size，int，{@link #DEFAULT_IMAGE_SIZE} by default
     */
    public static final String EXTRA_SELECT_COUNT = "max_select_count";
    /**
     * Select mode，{@link #MODE_MULTI} by default
     */
    public static final String EXTRA_SELECT_MODE = "select_count_mode";
    /**
     * Whether show camera，true by default
     */
    public static final String EXTRA_SHOW_CAMERA = "show_camera";
    public static final String IS_CAMERA = "is_camera";
    /**
     * Result data set，ArrayList&lt;String&gt;
     */
    public static final String EXTRA_RESULT = "select_result";
    public static final String BASE_RESULT = "base_result";
    /**
     * Original data set
     */
    public static final String EXTRA_DEFAULT_SELECTED_LIST = "default_list";
    // Default image size
    private static final int DEFAULT_IMAGE_SIZE = 9;
    private static final int SELECT_IMG = 301;
    private ArrayList<String> resultList = new ArrayList<>();
    private ArrayList<String> imgList = new ArrayList<>();
    private TextView mSubmitButton;
    private int mDefaultCount = DEFAULT_IMAGE_SIZE;
    private boolean isCamera = false;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                finish();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initData() {
        setTitle(R.string.photo_select);
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.mis_activity_default;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        final Intent intent = getIntent();
        mDefaultCount = intent.getIntExtra(EXTRA_SELECT_COUNT, DEFAULT_IMAGE_SIZE);
        isCamera = intent.getBooleanExtra(IS_CAMERA, false);

        final int mode = intent.getIntExtra(EXTRA_SELECT_MODE, MODE_MULTI);
        final boolean isShow = intent.getBooleanExtra(EXTRA_SHOW_CAMERA, true);
        if (mode == MODE_MULTI && intent.hasExtra(EXTRA_DEFAULT_SELECTED_LIST)) {
            resultList = intent.getStringArrayListExtra(EXTRA_DEFAULT_SELECTED_LIST);
        }

        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_COUNT, mDefaultCount);
            bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_MODE, mode);
            bundle.putBoolean(MultiImageSelectorFragment.EXTRA_SHOW_CAMERA, isShow);
            bundle.putBoolean(IS_CAMERA, isCamera);
            bundle.putStringArrayList(MultiImageSelectorFragment.EXTRA_DEFAULT_SELECTED_LIST, resultList);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.image_grid, Fragment.instantiate(this, MultiImageSelectorFragment.class.getName(), bundle))
                    .commit();
        }

        mSubmitButton = findViewById(R.id.commit);
        if (getIntent().getIntExtra(EXTRA_SELECT_MODE, MODE_MULTI) == MODE_MULTI) {
            updateDoneText(resultList);
            mSubmitButton.setVisibility(View.VISIBLE);
            mSubmitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (resultList != null && resultList.size() > 0) {
                        compressedImage();
                        // Notify success
                    } else {
                        setResult(RESULT_CANCELED);
                        finish();
                    }

                }
            });
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }

    @Subscribe
    public void onEventMainThread(EventData event) {
        switch (event.flag) {
            case Constant.IMAGE_SELECT_COM:
                compressedImage();
                break;
            default:
        }
    }


    /**
     * 所有选择都压缩
     */
    private void compressedImage() {
        imgList = new ArrayList<>();
        showLoadDialog(getString(R.string.compress_image_loading));
        final Compressor compressedImage = new Compressor(this)
                .setMaxWidth(1280)
                .setMaxHeight(1280)
                .setQuality(90)
                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                .setDestinationDirectoryPath(getExternalCacheDir().toString()+"/image/");
        Flowable.fromIterable(resultList)
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return compressedImage.compressToFile(new File(s)).toString();
                    }
                }).compose(com.zhongtie.work.network.Network.networkIO())
                .toList().toFlowable()
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> list) throws Exception {
                        cancelDialog();
                        Intent data = new Intent();
                        data.putStringArrayListExtra(EXTRA_RESULT, (ArrayList<String>) list);
                        data.putStringArrayListExtra(BASE_RESULT, resultList);
                        setResult(RESULT_OK, data);
                        //通知图片回调
                        EventBus.getDefault().post(new EventData(SELECT_IMG, resultList));
                        finish();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }


    /**
     * Update done button by select image data
     *
     * @param resultList selected image data
     */
    private void updateDoneText(ArrayList<String> resultList) {
        int size = 0;
        if (resultList == null || resultList.size() <= 0) {
            mSubmitButton.setText(R.string.mis_action_done);
            mSubmitButton.setEnabled(false);
        } else {
            size = resultList.size();
            mSubmitButton.setEnabled(true);
        }
        mSubmitButton.setText(getString(R.string.mis_action_button_string,
                getString(R.string.mis_action_done), size, mDefaultCount));
    }

    @Override
    public void onSingleImageSelected(String path) {
        Intent data = new Intent();
        resultList.add(path);
        compressedImage();
    }

    @Override
    public void onImageSelected(String path) {
        if (!resultList.contains(path)) {
            resultList.add(path);
        }
        updateDoneText(resultList);
    }

    @Override
    public void onImageUnselected(String path) {
        if (resultList.contains(path)) {
            resultList.remove(path);
        }
        updateDoneText(resultList);
    }

    @Override
    public void onCameraShot(File imageFile) {
        if (imageFile != null) {
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(imageFile)));
            resultList.add(imageFile.getAbsolutePath());
            compressedImage();
        }
    }
}
