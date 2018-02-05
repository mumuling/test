package com.zhongtie.work.ui.rewardpunish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.data.ProjectTeamEntity;
import com.zhongtie.work.data.RewardPunishDetailEntity;
import com.zhongtie.work.data.SelectSafeEventEntity;
import com.zhongtie.work.event.PunishCreateEvent;
import com.zhongtie.work.ui.base.BasePresenterFragment;
import com.zhongtie.work.ui.rewardpunish.adapter.RewardPunishCommonItemView;
import com.zhongtie.work.ui.rewardpunish.presenter.RPCreatePresenterImpl;
import com.zhongtie.work.ui.rewardpunish.presenter.RewardPunishCreateContract;
import com.zhongtie.work.ui.safe.SafeSupervisionCreateActivity;
import com.zhongtie.work.ui.safe.item.CreateEditContentItemView;
import com.zhongtie.work.ui.setting.CommonFragmentActivity;
import com.zhongtie.work.util.Util;
import com.zhongtie.work.util.ResourcesUtils;
import com.zhongtie.work.util.parse.BindKey;
import com.zhongtie.work.widget.SafeDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.zhongtie.work.ui.setting.CommonFragmentActivity.LIST;
import static com.zhongtie.work.ui.setting.CommonFragmentActivity.TITLE;
import static com.zhongtie.work.widget.DividerItemDecoration.VERTICAL_LIST;

/**
 * 安全处罚修改与创建
 *
 * @author Chaek
 * @date:2018.1.9
 */

public class RewardPunishCreateFragment extends BasePresenterFragment<RewardPunishCreateContract.Presenter> implements RewardPunishCreateContract.View {

    private static final String ID = "id";

    @BindKey(ID)
    private int mSafeOrderID;
    private RPCreateHeadView mHeadInfoView;
    private RecyclerView mList;
    private CommonAdapter mCommonAdapter;
    private List<Object> mInfoList = new ArrayList<>();


    public static void start(Context context, int id) {
        Bundle bundle = new Bundle();
        bundle.putInt(ID, id);
        SafeSupervisionCreateActivity.newInstance(context, RewardPunishCreateFragment.class, context.getString(R.string.safe_punish_title), bundle);
    }

    public static void start(Context context) {
        start(context, 0);
    }

    @Override
    public int getLayoutViewId() {
        return R.layout.common_status_list;
    }

    @Override
    public void initView() {
        mList = (RecyclerView) findViewById(R.id.list);
        mHeadInfoView = new RPCreateHeadView(getActivity());
        initAdapter();
    }

    private void initAdapter() {
        mCommonAdapter = new CommonAdapter(mInfoList)
                //输入数据
                .register(CreateEditContentItemView.class)
                //基本界面
                .register(RewardPunishCommonItemView.class);
        mCommonAdapter.addHeaderView(mHeadInfoView);

        View mFooterView = LayoutInflater.from(getAppContext()).inflate(R.layout.layout_modify_pw_bottom, mList, false);

        TextView mSubmit = mFooterView.findViewById(R.id.modify_password);
        mSubmit.setText(R.string.submit);
        mSubmit.setOnClickListener(v -> mPresenter.createRewardPunish());
        mCommonAdapter.addFooterView(mFooterView);
    }


    @Override
    protected void initData() {
        SafeDividerItemDecoration dividerItemDecoration = new SafeDividerItemDecoration(getContext(), VERTICAL_LIST);
        dividerItemDecoration.setLineColor(Util.getColor(R.color.line2));
        dividerItemDecoration.setDividerHeight(ResourcesUtils.dip2px(10));
        dividerItemDecoration.setEndPosition(5);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(mCommonAdapter);
        mPresenter.getRewardPunishItemList(mSafeOrderID);
    }

    @Override
    protected RewardPunishCreateContract.Presenter getPresenter() {
        return new RPCreatePresenterImpl();
    }

    @Override
    public void setItemList(List<Object> itemList) {
        mCommonAdapter.setListData(itemList);
        mCommonAdapter.notifyDataSetChanged();
    }

    @Override
    public int getPunishAmount() {
        return mHeadInfoView.getInputPunishAmount();
    }

    @Override
    public ProjectTeamEntity getPunishUnit() {
        return mHeadInfoView.getUnitEntity();
    }

    @Override
    public String getCreateCode() {
        return mHeadInfoView.getCreateSafeCode();
    }

    @Override
    public SelectSafeEventEntity getSafeEventData() {
        return mHeadInfoView.getSupervisorInfoEntity();
    }

    @Override
    public void createSuccess() {
        getActivity().finish();
        new PunishCreateEvent().post();
    }

    @Override
    public void setHeadEditInfo(RewardPunishDetailEntity data) {
        mHeadInfoView.setHeadEditInfo(data);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CommonFragmentActivity.USER_SELECT_CODE) {
                String title = data.getStringExtra(TITLE);
                List createUserEntities = (List) data.getSerializableExtra(LIST);
                mPresenter.setSelectUserInfoList(title, createUserEntities);
                mCommonAdapter.notifyDataSetChanged();
            }

        }

    }

}
