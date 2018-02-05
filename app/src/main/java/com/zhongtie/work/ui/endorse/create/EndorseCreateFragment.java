package com.zhongtie.work.ui.endorse.create;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.data.CommonUserEntity;
import com.zhongtie.work.ui.base.BasePresenterFragment;
import com.zhongtie.work.ui.file.select.NormalFile;
import com.zhongtie.work.ui.safe.SafeSupervisionCreateActivity;
import com.zhongtie.work.widget.SafeDividerItemDecoration;
import com.zhongtie.work.ui.setting.CommonFragmentActivity;
import com.zhongtie.work.util.Util;
import com.zhongtie.work.util.ResourcesUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.zhongtie.work.ui.setting.CommonFragmentActivity.LIST;
import static com.zhongtie.work.ui.setting.CommonFragmentActivity.TITLE;
import static com.zhongtie.work.widget.DividerItemDecoration.VERTICAL_LIST;

/**
 * 文件签认创建
 * Auth:Cheek
 * date:2018.1.9
 */

public class EndorseCreateFragment extends BasePresenterFragment<EndorseCreateContract.Presenter> implements EndorseCreateContract.View {

    public static final String ID = "id";
    private int mSafeOrderID;
    private View mHeadInfoView;
    private EditText mTitleEdit;
    private RecyclerView mList;
    private CommonAdapter mCommonAdapter;
    private List<Object> mInfoList = new ArrayList<>();


    public static void start(Context context, int endorssId) {
        Bundle bundle = new Bundle();
        bundle.putInt(ID, endorssId);
        SafeSupervisionCreateActivity.newInstance(context, EndorseCreateFragment.class, context.getString(R.string.endorse_file_title), bundle);
    }

    public static void start(Context context) {
        start(context, 0);
    }


    @Override
    public int getLayoutViewId() {
        if (getArguments() != null) {
            mSafeOrderID = getArguments().getInt(ID);
        }
        return R.layout.file_endorse_create_fragment;
    }

    @Override
    public void initView() {
        mList = (RecyclerView) findViewById(R.id.list);
        mHeadInfoView = LayoutInflater.from(getContext()).inflate(R.layout.layout_file_endorse_head, mList, false);
        mTitleEdit = (EditText) findViewById(R.id.title_edit);
        initAdapter();
    }

    private void initAdapter() {
        mCommonAdapter = new CommonAdapter(mInfoList)
                //基本界面
                .register(EndorseCommonItemView.class);
        mCommonAdapter.addHeaderView(mHeadInfoView);
    }


    @Override
    protected void initData() {
        SafeDividerItemDecoration dividerItemDecoration = new SafeDividerItemDecoration(getContext(), VERTICAL_LIST);
        dividerItemDecoration.setLineColor(Util.getColor(R.color.line2));
        dividerItemDecoration.setDividerHeight(ResourcesUtils.dip2px(10));
        dividerItemDecoration.setEndPosition(3);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(mCommonAdapter);
        mPresenter.getItemList(mSafeOrderID);
    }

    /**
     * 选择签认文件
     *
     * @param normalFile 文件信息
     */
    @Subscribe
    public void selectFileEvent(NormalFile normalFile) {
        List<NormalFile> normalFiles = new ArrayList<>();
        normalFiles.add(normalFile);
        mPresenter.setSelectList("上传文件", normalFiles);
        mCommonAdapter.notifyDataSetChanged();
    }

    @Override
    protected EndorseCreateContract.Presenter getPresenter() {
        return new EndorseCreatePresenterImpl();
    }

    @Override
    public void setItemList(List<Object> itemList) {
        mCommonAdapter.setListData(itemList);
        mCommonAdapter.notifyDataSetChanged();
    }

    @Override
    public String getEndorseTitle() {
        return mTitleEdit.getText().toString();
    }

    @Override
    public void createSuccess() {

    }

    @Override
    public void modifySuccess() {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CommonFragmentActivity.USER_SELECT_CODE) {
                String title = data.getStringExtra(TITLE);
                List<CommonUserEntity> createUserEntities = (List<CommonUserEntity>) data.getSerializableExtra(LIST);
                mPresenter.setSelectList(title, createUserEntities);
                mCommonAdapter.notifyDataSetChanged();
            }

        }

    }


}
