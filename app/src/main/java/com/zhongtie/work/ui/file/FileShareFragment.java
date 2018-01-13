package com.zhongtie.work.ui.file;

import android.content.Context;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.base.adapter.OnRecyclerItemClickListener;
import com.zhongtie.work.data.UserFileEntity;
import com.zhongtie.work.data.UserFolderEntity;
import com.zhongtie.work.ui.base.BaseFragment;
import com.zhongtie.work.ui.file.item.UserFileItemView;
import com.zhongtie.work.ui.file.item.UserFolderItemView;
import com.zhongtie.work.widget.EmptyFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Auth:Cheek
 * date:2018.1.13
 */

public class FileShareFragment extends BaseFragment implements OnRecyclerItemClickListener {
    private EmptyFragment mFileFragment;

    private CommonAdapter commonAdapter;
    private List<Object> mFileFolderList = new ArrayList<>();
    private OnFileChildListener onFileChildListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFileChildListener) {
            onFileChildListener = (OnFileChildListener) context;
        }
    }

    @Override
    public int getLayoutViewId() {
        return R.layout.flie_share_fragment;
    }

    @Override
    public void initView() {
        mFileFragment = (EmptyFragment) findViewById(R.id.file_fragment);

    }

    @Override
    protected void initData() {
        for (int i = 0; i < 10; i++) {
            mFileFolderList.add(new UserFolderEntity());
        }
        for (int i = 0; i < 10; i++) {
            mFileFolderList.add(new UserFileEntity());
        }

        commonAdapter = new CommonAdapter(mFileFolderList).register(UserFileItemView.class).register(UserFolderItemView.class);
        commonAdapter.setOnItemClickListener(this);
        mFileFragment.getRecyclerView().setAdapter(commonAdapter);
    }

    @Override
    public void onClick(Object o, int index) {
        if (o instanceof UserFolderEntity) {
            onFileChildListener.onNexFolder((UserFolderEntity) o);
        }
    }
}
