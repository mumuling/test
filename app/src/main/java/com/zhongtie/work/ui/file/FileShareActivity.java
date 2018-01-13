package com.zhongtie.work.ui.file;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zhongtie.work.Fragments;
import com.zhongtie.work.R;
import com.zhongtie.work.data.UserFolderEntity;
import com.zhongtie.work.ui.base.BaseActivity;
import com.zhongtie.work.ui.select.CommonSelectSearchActivity;

/**
 * Auth:Cheek
 * date:2018.1.13
 */

public class FileShareActivity extends BaseActivity implements OnFileChildListener {
    private TextView mFilePathName;
    public static void newInstance(Context context) {
        context.startActivity(new Intent(context, FileShareActivity.class));
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.flie_share_activity;
    }

    @Override
    protected void initView() {
        setTitle("文件共享");
        setRightText("搜索");
        mFilePathName = (TextView) findViewById(R.id.file_path_name);

    }

    @Override
    protected void onClickRight() {
        super.onClickRight();
        CommonSelectSearchActivity.newInstance(this, FileShareFragment.class, "请输入关键字");
    }

    @Override
    protected void initData() {
        Fragments.with(this)
                .fragment(FileShareFragment.class)
                .removeOld(false)
                .single(false)
                .into(R.id.fragment_content);
    }

    @Override
    public void onNexFolder(UserFolderEntity userFolderEntity) {
        mFilePathName.setVisibility(View.VISIBLE);
        mFilePathName.setText("当前:文件");
        Fragments.with(this)
                .fragment(FileSelectFragment.class)
                .removeOld(false)
                .single(false)
                .addToBackStack()
                .into(R.id.fragment_content);

    }
}
