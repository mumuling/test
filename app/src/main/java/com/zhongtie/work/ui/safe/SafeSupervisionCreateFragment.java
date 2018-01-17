package com.zhongtie.work.ui.safe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.data.ProjectTeamEntity;
import com.zhongtie.work.ui.base.BasePresenterFragment;
import com.zhongtie.work.ui.image.MultiImageSelector;
import com.zhongtie.work.ui.image.MultiImageSelectorActivity;
import com.zhongtie.work.ui.safe.item.CreateEditContentItemView;
import com.zhongtie.work.ui.safe.item.CreateSelectTypeItemView;
import com.zhongtie.work.ui.safe.item.SafeCommonItemView;
import com.zhongtie.work.ui.safe.order.SafeDividerItemDecoration;
import com.zhongtie.work.ui.safe.presenter.SafeCreateContract;
import com.zhongtie.work.ui.safe.presenter.SafeCreatePresenterImpl;
import com.zhongtie.work.ui.safe.view.SafeCreateEditHeadView;
import com.zhongtie.work.ui.setting.CommonFragmentActivity;
import com.zhongtie.work.util.Util;
import com.zhongtie.work.util.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.zhongtie.work.ui.setting.CommonFragmentActivity.LIST;
import static com.zhongtie.work.ui.setting.CommonFragmentActivity.TITLE;
import static com.zhongtie.work.widget.DividerItemDecoration.VERTICAL_LIST;

/**
 * 安全督导创建界面
 * Auth:Cheek
 * date:2018.1.9
 */

public class SafeSupervisionCreateFragment extends BasePresenterFragment<SafeCreateContract.Presenter> implements SafeCreateContract.View {

    public static final String ID = "id";
    /**
     * 要是修改则带入ID 添加ID参数
     */
    private int mSafeOrderID;

    private SafeCreateEditHeadView mHeadInfoView;
    private RecyclerView mList;

    private CommonAdapter mCommonAdapter;
    private List<Object> mInfoList = new ArrayList<>();

    public static SafeSupervisionCreateFragment newInstance(int id) {
        Bundle args = new Bundle();
        SafeSupervisionCreateFragment fragment = new SafeSupervisionCreateFragment();
        args.putInt(ID, id);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutViewId() {
        if (getArguments() != null) {
            mSafeOrderID = getArguments().getInt(ID);
        }
        return R.layout.base_recyclerview;
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
        dividerItemDecoration.setDividerHeight(ViewUtils.dip2px(10));
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
    public String getEditSite() {
        return mHeadInfoView.getEditSite();
    }

    @Override
    public void createSuccess() {
        getActivity().finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == MultiImageSelector.REQUEST_CODE) {
                List<String> selectImgList = data.getStringArrayListExtra(MultiImageSelectorActivity.BASE_RESULT);
                mPresenter.setSelectImageList(selectImgList);
                mCommonAdapter.notifyDataSetChanged();
            } else if (requestCode == CommonFragmentActivity.USER_SELECT_CODE) {
                String title = data.getStringExtra(TITLE);
                List createUserEntities = (List) data.getSerializableExtra(LIST);
                mPresenter.setSelectUserInfoList(title, createUserEntities);
                mCommonAdapter.notifyDataSetChanged();
            }

        }

    }

    public static final String[] imageUrls = new String[]{
            "http://img.my.csdn.net/uploads/201309/01/1378037235_3453.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037235_9280.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037234_3539.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037234_6318.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037194_2965.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037193_1687.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037193_1286.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037192_8379.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037178_9374.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037177_1254.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037177_6203.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037152_6352.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037151_9565.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037151_7904.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037148_7104.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037129_8825.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037128_5291.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037128_3531.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037127_1085.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037095_7515.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037094_8001.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037093_7168.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037091_4950.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949643_6410.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949642_6939.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949630_4505.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949630_4593.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949629_7309.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949629_8247.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949615_1986.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949614_8482.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949614_3743.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949614_4199.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949599_3416.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949599_5269.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949598_7858.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949598_9982.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949578_2770.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949578_8744.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949577_5210.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949577_1998.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949482_8813.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949481_6577.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949480_4490.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949455_6792.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949455_6345.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949442_4553.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949441_8987.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949441_5454.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949454_6367.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949442_4562.jpg"};


}
