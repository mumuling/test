package com.zhongtie.work.ui.file;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.base.adapter.OnRecyclerItemClickListener;
import com.zhongtie.work.ui.base.BaseFragment;
import com.zhongtie.work.ui.file.select.Directory;
import com.zhongtie.work.ui.file.select.FileLoaderCallbacks;
import com.zhongtie.work.ui.file.select.FilterResultCallback;
import com.zhongtie.work.ui.file.select.NormalFile;
import com.zhongtie.work.ui.file.select.NormalFileItemView;
import com.zhongtie.work.widget.DividerItemDecoration;
import com.zhongtie.work.widget.EmptyFragment;
import com.zhongtie.work.widget.KDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;

import static com.zhongtie.work.ui.file.select.FileLoaderCallbacks.TYPE_FILE;

/**
 * Auth:Cheek
 * date:2018.1.13
 */

public class FileSelectFragment extends BaseFragment implements OnRecyclerItemClickListener<NormalFile>, FilterResultCallback<NormalFile>, Runnable {
    private EmptyFragment mFileFragment;
    private ArrayList<NormalFile> mSelectedList = new ArrayList<>();
    public static final String SELECT_FILE = "select_file";

    private CommonAdapter commonAdapter;
    private OnFileChildListener onFileChildListener;
    private String[] suffix = {"xlsx", "xls", "doc", "dOcX", "ppt", ".pptx", "pdf"};

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFileChildListener) {
            onFileChildListener = (OnFileChildListener) context;
        }
    }

    @Override
    public int getLayoutViewId() {
        return R.layout.file_select_fragment;
    }

    @Override
    public void initView() {
        mFileFragment = (EmptyFragment) findViewById(R.id.file_fragment);
        initLoading();
        loadData();
    }

    @Override
    protected void initData() {
        commonAdapter = new CommonAdapter().register(NormalFileItemView.class);
        commonAdapter.setOnItemClickListener(this);
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), KDividerItemDecoration.VERTICAL);
        decoration.setDividerHeight(1);
        mFileFragment.getRecyclerView().addItemDecoration(decoration);
        mFileFragment.getRecyclerView().setAdapter(commonAdapter);
    }

    private void loadData() {
        getActivity().getSupportLoaderManager().initLoader(3, null,
                new FileLoaderCallbacks(getActivity(), FileSelectFragment.this, TYPE_FILE, suffix));
    }

    @Override
    public void onClick(NormalFile normalFile, int index) {
        normalFile.post();
        Intent selectFile = new Intent();
        selectFile.putExtra(SELECT_FILE, normalFile);
        getActivity().setResult(Activity.RESULT_OK, selectFile);
        getActivity().finish();
    }

    @Override
    public void onResult(List<Directory<NormalFile>> directories) {
        initSuccess();
        List<NormalFile> list = new ArrayList<>();
        for (Directory<NormalFile> directory : directories) {
            list.addAll(directory.getFiles());
        }
        Flowable.fromIterable(list)
                .filter((AppendOnlyLinkedArrayList.NonThrowingPredicate<NormalFile>) normalFile -> normalFile.getSize() > 0)
                .toList()
                .toFlowable()
                .subscribe(normalFiles -> {
                    commonAdapter.setListData(list);
                    commonAdapter.notifyDataSetChanged();
                }, throwable -> {

                });

    }

    @Override
    public void run() {

    }
}
