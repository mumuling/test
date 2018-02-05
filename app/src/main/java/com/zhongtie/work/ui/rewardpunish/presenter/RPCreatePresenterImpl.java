package com.zhongtie.work.ui.rewardpunish.presenter;

import android.support.v4.util.ArrayMap;

import com.zhongtie.work.R;
import com.zhongtie.work.app.App;
import com.zhongtie.work.app.Cache;
import com.zhongtie.work.data.CommonUserEntity;
import com.zhongtie.work.data.ProjectTeamEntity;
import com.zhongtie.work.data.RPRecordEntity;
import com.zhongtie.work.data.RewardPunishDetailEntity;
import com.zhongtie.work.data.create.CommonItemType;
import com.zhongtie.work.data.create.EditContentEntity;
import com.zhongtie.work.event.PunishDetailUpdateEvent;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.NetWorkFunc1;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.RewardPunishApi;
import com.zhongtie.work.ui.base.BasePresenterImpl;
import com.zhongtie.work.util.TextUtil;
import com.zhongtie.work.util.ResourcesUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 安全奖罚 创建与编辑
 * Auth:Cheek
 * date:2018.1.12
 */

public class RPCreatePresenterImpl extends BasePresenterImpl<RewardPunishCreateContract.View> implements RewardPunishCreateContract.Presenter {
    /**
     * 摘要信息
     */
    private EditContentEntity mAbstract;
    /**
     * 详细信息
     */
    private EditContentEntity mDescribe;
    private ArrayMap<String, CommonItemType> mItemArrayMap;

    private int mPunishId;

    private RewardPunishDetailEntity detailEntity;

    private List<Object> mPunishItemList;

    /**
     * @return 获取类型
     */
    private List<CommonItemType> fetchCommonItemTypeList() {
        String[] titleList = App.getInstance().getResources().getStringArray(R.array.create_reward_punish_item_title);
        String[] tip = App.getInstance().getResources().getStringArray(R.array.create_reward_punish_item_tip);
        List<CommonItemType> list = new ArrayList<>();
        int size = titleList.length;
        for (int i = 0; i < size; i++) {
            CommonItemType item = new CommonItemType<>(titleList[i], tip[i], R.drawable.plus, true);
            mItemArrayMap.put(titleList[i], item);
            list.add(item);
        }
        return list;
    }


    @Override
    public void getRewardPunishItemList(int orderId) {

        if (orderId <= 0) {
            List<Object> itemList = new ArrayList<>();
            mItemArrayMap = new ArrayMap<>();
            mAbstract = new EditContentEntity("摘要", "请输入摘要说明", "");
            mDescribe = new EditContentEntity("详细情况", "请输入详细情况", "");
            //添加描述
            itemList.add(mAbstract);
            //添加修改要求
            itemList.add(mDescribe);
            itemList.addAll(fetchCommonItemTypeList());
            mView.setItemList(itemList);
        } else {
            fetchPunishData(orderId);
        }
    }

    private void fetchPunishData(int punishId) {
        this.mPunishId = punishId;
        mView.initLoading();
        addDispose(Http.netServer(RewardPunishApi.class)
                .punishDetails(Cache.getUserID(), punishId)
                .map(new NetWorkFunc1<>())
                .map(data -> {
                    mPunishItemList = new ArrayList<>();
                    mItemArrayMap = new ArrayMap<>();

                    mAbstract = new EditContentEntity("摘要", "请输入摘要说明", data.getSummary());
                    mPunishItemList.add(mAbstract);
                    mDescribe = new EditContentEntity("详细情况", "请输入详细情况", data.getContent());
                    mPunishItemList.add(mDescribe);
                    TransformationPunishModel transModel = new TransformationPunishModel(data, true);
                    CommonItemType punishUser = transModel.fetchPunishUserItem();
                    mPunishItemList.add(punishUser);
                    mItemArrayMap.put(punishUser.getTitle(), punishUser);

                    CommonItemType punishLeader = transModel.fetchPunishLeaderItem();
                    mPunishItemList.add(punishLeader);
                    mItemArrayMap.put(punishLeader.getTitle(), punishLeader);

                    CommonItemType readGroup = transModel.fetchReadList();
                    mPunishItemList.add(readGroup);
                    mItemArrayMap.put(readGroup.getTitle(), readGroup);

                    return data;
                })
                .delay(500, TimeUnit.MILLISECONDS)
                .compose(Network.networkIO())
                .subscribe(data -> {
                    mView.setHeadEditInfo(data);
                    mView.setItemList(mPunishItemList);
                    mView.initSuccess();
                }, throwable -> {
                    throwable.printStackTrace();
                    mView.initFail();
                }));
    }

    @Override
    public void createRewardPunish() {

        String code = mView.getCreateCode();
        if (TextUtil.isEmpty(code)) {
            mView.showToast(R.string.toast_input_punish_code);
            return;
        }
        ProjectTeamEntity teamEntity = mView.getPunishUnit();
        if (teamEntity == null) {
            mView.showToast(R.string.toast_punish_select_unit);
            return;
        }

        int punishAmount = mView.getPunishAmount();
        if (punishAmount <= 0) {
            mView.showToast(R.string.toast_punish_amount);
            return;
        }
        //摘要信息
        String summary = mAbstract.getContent();
        if (TextUtil.isEmpty(summary)) {
            mView.showToast(R.string.toast_punish_abstract);
            return;
        }
        String describe = mDescribe.getContent();
        if (TextUtil.isEmpty(describe)) {
            mView.showToast(R.string.toast_punish_describe);
            return;
        }

        for (String key : mItemArrayMap.keySet()) {
            CommonItemType itemType = mItemArrayMap.get(key);
            if (itemType.getTypeItemList().isEmpty()) {
                mView.showToast("请选择" + itemType.getTitle());
                return;
            }
        }


        ArrayMap<String, Object> createData = new ArrayMap<>();


        createData.put("tax_userid", Cache.getUserID());
        createData.put("tax_company", Cache.getSelectCompany());
        createData.put("tax_number", code);
        createData.put("tax_unit", teamEntity.getProjectTeamName());
        createData.put("tax_summary", summary);
        createData.put("tax_detail", describe);
        int eventId = mView.getSafeEventData() == null ? 0 : mView.getSafeEventData().getId();
        createData.put("tax_eventid", eventId);

        CommonItemType itemType = mItemArrayMap.get(ResourcesUtils.getString(R.string.punish_leader_title));
        RPRecordEntity lead = (RPRecordEntity) itemType.getTypeItemList().get(0);
        createData.put("tax_leader", lead.getUserID());
        createData.put("tax_money", punishAmount);
        createData.put("tax_reader", mItemArrayMap.get(ResourcesUtils.getString(R.string.punish_read_group_title)).getTeamIDList());
        CommonItemType safer = mItemArrayMap.get(ResourcesUtils.getString(R.string.punish_safe_title));
        RPRecordEntity saferItem = (RPRecordEntity) safer.getTypeItemList().get(0);
        createData.put("tax_safer", saferItem.getUserID());


        if (mPunishId > 0) {
            createData.put("id", mPunishId);
            addDispose(Http.netServer(RewardPunishApi.class)
                    .editPunishEvent(createData)
                    .compose(Network.convertDialogTip(mView))
                    .subscribe(integer -> {
                        mView.showToast(R.string.punish_edit_success);
                        mView.createSuccess();
                        new PunishDetailUpdateEvent().post();
                    }, throwable -> {
                    }));
        } else {
            addDispose(Http.netServer(RewardPunishApi.class)
                    .createPunishEvent(createData)
                    .compose(Network.convertDialogTip(mView))
                    .subscribe(integer -> {
                        mView.showToast(R.string.punish_create_success);
                        mView.createSuccess();

                    }, throwable -> {
                    }));
        }
    }


    @Override
    public void setSelectUserInfoList(String title, List createUserEntities) {
        CommonItemType itemType = mItemArrayMap.get(title);
        if (itemType != null) {
            if (!title.contains(ResourcesUtils.getString(R.string.punish_read_group_title))) {
                //编辑界面对选择的人进行转换
                List<RPRecordEntity> list = new ArrayList<>();
                for (int i = 0; i < createUserEntities.size(); i++) {
                    CommonUserEntity user = (CommonUserEntity) createUserEntities.get(i);
                    RPRecordEntity rpRecordEntity = new RPRecordEntity();
                    rpRecordEntity.setUserName(user.getUserName());
                    rpRecordEntity.setUserID(user.getUserId());
                    rpRecordEntity.setUserPic(user.getUserPic());
                    rpRecordEntity.setEdit(true);
                    list.add(rpRecordEntity);
                }
                itemType.setTypeItemList(list);
            } else {
                itemType.setTypeItemList(createUserEntities);
            }
        }
    }

}
