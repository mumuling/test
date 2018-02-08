package com.zhongtie.work.ui.safe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.data.ProjectTeamEntity;
import com.zhongtie.work.data.SafeEventEntity;
import com.zhongtie.work.event.ExitEvent;
import com.zhongtie.work.event.SafeCreateEvent;
import com.zhongtie.work.list.OnActivityKeyListener;
import com.zhongtie.work.ui.base.BasePresenterFragment;
import com.zhongtie.work.ui.image.MultiImageSelector;
import com.zhongtie.work.ui.image.MultiImageSelectorActivity;
import com.zhongtie.work.ui.safe.item.CreateEditContentItemView;
import com.zhongtie.work.ui.safe.item.CreateSelectTypeItemView;
import com.zhongtie.work.ui.safe.item.SafeCommonItemView;
import com.zhongtie.work.ui.safe.presenter.SafeCreateContract;
import com.zhongtie.work.ui.safe.presenter.SafeCreatePresenterImpl;
import com.zhongtie.work.ui.safe.view.SafeCreateEditHeadView;
import com.zhongtie.work.ui.setting.CommonFragmentActivity;
import com.zhongtie.work.util.ResourcesUtils;
import com.zhongtie.work.util.Util;
import com.zhongtie.work.widget.SafeDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.zhongtie.work.ui.setting.CommonFragmentActivity.LIST;
import static com.zhongtie.work.ui.setting.CommonFragmentActivity.TITLE;
import static com.zhongtie.work.widget.DividerItemDecoration.VERTICAL_LIST;

/**
 * 安全督导创建界面
 *
 * @author Chaek
 * @date:2018.1.9
 */

public class SafeSupervisionCreateFragment extends BasePresenterFragment<SafeCreateContract.Presenter> implements SafeCreateContract.View, OnActivityKeyListener {
    public static final String ID = "id";
    /**
     * 要是修改则带入ID 添加ID参数
     */
    private int mSafeOrderID;

    private RecyclerView mList;
    private CommonAdapter mCommonAdapter;
    private SafeCreateEditHeadView mHeadInfoView;
    private List<Object> mInfoList = new ArrayList<>();

    public static Bundle newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(ID, id);
        return args;
    }


    @Override
    public int getLayoutViewId() {
        if (getArguments() != null) {
            mSafeOrderID = getArguments().getInt(ID);
        }
        return R.layout.common_status_list;
    }

    @Override
    public void initView() {
        mList = (RecyclerView) findViewById(R.id.list);
        mHeadInfoView = new SafeCreateEditHeadView(getActivity());
        initAdapter();
    }

    /**
     * 初始化比安全督导输入的基本元素 采用recyclerList实现界面动态变化
     * 避免后期修改需求更改
     */
    private void initAdapter() {
        mCommonAdapter = new CommonAdapter(mInfoList)
                //选择类别
                .register(CreateSelectTypeItemView.class)
                //输入数据
                .register(CreateEditContentItemView.class)
                //基本界面
                .register(SafeCommonItemView.class);
        mCommonAdapter.addHeaderView(mHeadInfoView);
        View mFooterView = LayoutInflater.from(getAppContext()).inflate(R.layout.layout_modify_pw_bottom, mList, false);
        mCommonAdapter.addFooterView(mFooterView);
        TextView mModifyPassword = mFooterView.findViewById(R.id.modify_password);
        mModifyPassword.setText(R.string.submit);
        mModifyPassword.setOnClickListener(v -> mPresenter.createSafeOrder());
    }


    @Override
    protected void initData() {
        SafeDividerItemDecoration dividerItemDecoration = new SafeDividerItemDecoration(getContext(), VERTICAL_LIST);
        dividerItemDecoration.setLineColor(Util.getColor(R.color.line2));
        dividerItemDecoration.setDividerHeight(ResourcesUtils.dip2px(10));
        dividerItemDecoration.setEndPosition(8);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(mCommonAdapter);
        mPresenter.getItemList(mSafeOrderID);
    }

    @Override
    protected SafeCreateContract.Presenter getPresenter() {
        return new SafeCreatePresenterImpl();
    }

    @Override
    public void setItemList(List<Object> itemList) {
        this.mInfoList.clear();
        this.mInfoList.addAll(itemList);
        mCommonAdapter.notifyDataSetChanged();
    }



    @Override
    public void onClickRefresh() {
        super.onClickRefresh();
    }

    @Override
    public String getSelectDate() {
        return mHeadInfoView.getSelectDateTime();
    }

    @Override
    public ProjectTeamEntity getCompanyUnitEntity() {
        return mHeadInfoView.getCompanyUnitEntity();
    }

    @Override
    public ProjectTeamEntity getCompanyTeamEntity() {
        return mHeadInfoView.getCompanyOfferEntity();
    }

    @Override
    public boolean isShowWorkTeam() {
        return mHeadInfoView.isShowCompanySelect();
    }

    @Override
    public String getEditSite() {
        return mHeadInfoView.getEditSite();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        new SafeCreateEvent().post();
    }

    @Override
    public void createSuccess() {
        getActivity().finish();
    }

    @Override
    public void setModifyInfo(SafeEventEntity titleUserInfo) {
        mHeadInfoView.setModifyInfo(titleUserInfo);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == MultiImageSelector.REQUEST_CODE) {
                List<String> selectImgList = data.getStringArrayListExtra(MultiImageSelectorActivity.BASE_RESULT);
                mPresenter.setSelectImageList(selectImgList);
                mCommonAdapter.notifyItemChanged(4);
            } else if (requestCode == CommonFragmentActivity.USER_SELECT_CODE) {
                String title = data.getStringExtra(TITLE);
                List createUserEntities = (List) data.getSerializableExtra(LIST);
                mPresenter.setSelectUserInfoList(title, createUserEntities);
                mCommonAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean isFetchBackEvent() {
        return true;
    }

    @Override
    public void onClickBack() {
        super.onClickBack();
        new MaterialDialog.Builder(getActivity())
                .title(R.string.dialog_tip)
                .content(R.string.dialog_edit_exit_tip)
                .positiveText(R.string.confirm)
                .negativeText(R.string.cancel)
                .onPositive((dialog, which) -> {
                    getActivity().finish();
                }).onNegative((dialog, which) -> dialog.dismiss())
                .build().show();
    }
}
