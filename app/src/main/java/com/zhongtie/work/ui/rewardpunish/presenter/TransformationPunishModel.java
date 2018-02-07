package com.zhongtie.work.ui.rewardpunish.presenter;

import com.zhongtie.work.R;
import com.zhongtie.work.app.App;
import com.zhongtie.work.data.ApproveEntity;
import com.zhongtie.work.data.CommonUserEntity;
import com.zhongtie.work.data.PunishAtUserEntity;
import com.zhongtie.work.data.PunishGoBackEntity;
import com.zhongtie.work.data.PunishReadEntity;
import com.zhongtie.work.data.RPRecordEntity;
import com.zhongtie.work.data.RewardPunishDetailEntity;
import com.zhongtie.work.data.TeamNameEntity;
import com.zhongtie.work.data.create.CommonItemType;
import com.zhongtie.work.ui.rewardpunish.adapter.PrRecordItemView;
import com.zhongtie.work.util.L;
import com.zhongtie.work.util.TextUtil;
import com.zhongtie.work.util.TimeUtils;
import com.zhongtie.work.util.ResourcesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 安全奖罚数据转换
 * Auth:Cheek
 * date:2018.1.27
 */

class TransformationPunishModel {
    private RewardPunishDetailEntity detailEntity;
    private boolean isEdit;

    private List<Integer> atUserList;

    TransformationPunishModel(RewardPunishDetailEntity detailEntity) {
        this(detailEntity, false);
    }

    TransformationPunishModel(RewardPunishDetailEntity detailEntity, boolean isEdit) {
        this.detailEntity = detailEntity;
        this.isEdit = isEdit;
        atUserList = new ArrayList<>();

        if (detailEntity.getAtuserlist() != null) {
            for (PunishAtUserEntity approveEntity : detailEntity.getAtuserlist()) {
                atUserList.add(approveEntity.getId());
            }
        }
    }

    /**
     * 同意信息 如果同意签字则不显示 退回信息
     *
     * @return 统一的布局对应类
     */
    private RPRecordEntity getAgreeSupervisionData() {
        RPRecordEntity punishUserData = new RPRecordEntity();
        punishUserData.setEdit(isEdit);
        punishUserData.setUserPic(detailEntity.getSupervisionUserPic());
        punishUserData.setUserID(detailEntity.getSupervisionId());
        punishUserData.setUserName(detailEntity.getSupervisionUserName());
        punishUserData.setSignatureImg(detailEntity.getSupervisionUserSignImg());
        punishUserData.setSignTime(TimeUtils.formatPunishDetailTime(detailEntity.getSupervisionSignTime()));
        punishUserData.setState(PrRecordItemView.PUNISH_AGREE);
        return punishUserData;
    }

    private CommonUserEntity getUpdateAgreeSupervisionData() {
        CommonUserEntity punishUserData = new CommonUserEntity();
        punishUserData.setSelect(true);
        punishUserData.setAt(atUserList.contains(detailEntity.getSupervisionId()));
        punishUserData.setUserId(detailEntity.getSupervisionId());
        punishUserData.setUserName(detailEntity.getSupervisionUserName());
        punishUserData.setUserPic(detailEntity.getSupervisionUserPic());
        return punishUserData;
    }

    /**
     * 被处罚单位负责人
     *
     * @return
     */
    private RPRecordEntity getPunishLeaderData() {
        RPRecordEntity leaderData = new RPRecordEntity();
        leaderData.setEdit(isEdit);
        leaderData.setUserID(detailEntity.getPunishLeaderId());
        leaderData.setUserName(detailEntity.getPunishLeaderName());
        leaderData.setUserPic(detailEntity.getPunishLeaderPic());
        leaderData.setState(PrRecordItemView.PUNISH_SIGN);

        if (!isEdit) {
            //不是编辑状态才显示签名等信息
            leaderData.setSignatureImg(detailEntity.getPunishLeadSign());
            leaderData.setSignTime(detailEntity.getPunishLeaderSignTime());
            leaderData.setSignTime(TimeUtils.formatPunishDetailTime(detailEntity.getPunishLeaderSignTime()));
        }
        return leaderData;
    }

    private CommonUserEntity getUpdatePunishLeaderData() {
        CommonUserEntity leaderData = new CommonUserEntity();
        leaderData.setSelect(true);
        leaderData.setAt(atUserList.contains(detailEntity.getPunishLeaderId()));
        leaderData.setUserId(detailEntity.getPunishLeaderId());
        leaderData.setUserName(detailEntity.getPunishLeaderName());
        leaderData.setUserPic(detailEntity.getPunishLeaderPic());
        return leaderData;
    }


    /**
     * 获取退回信息
     *
     * @return 所有退回状态
     */
    private List<RPRecordEntity> getSendBackList() {
        List<RPRecordEntity> backList = new ArrayList<>();
        List<PunishGoBackEntity> goBackEntities = detailEntity.getSendBackList();
        for (int i = 0, len = goBackEntities.size(); i < len; i++) {
            PunishGoBackEntity backEntity = goBackEntities.get(i);
            RPRecordEntity recordEntity = new RPRecordEntity();
            recordEntity.setEdit(isEdit);
            recordEntity.setUserID(backEntity.getId());
            recordEntity.setUserName(backEntity.getName());
            recordEntity.setUserPic(backEntity.getPicture());
            recordEntity.setSignatureImg(backEntity.getSign());
            recordEntity.setReplyContent(backEntity.getContent());
            recordEntity.setSignTime((backEntity.getAddtime()));
            recordEntity.setState(PrRecordItemView.PUNISH_BACK);
            backList.add(recordEntity);
        }
        return backList;
    }

    /**
     * 获取安全监察人
     */
    CommonItemType fetchPunishUserItem() {
        List<RPRecordEntity> endorseUserList = new ArrayList<>();
        if (TextUtil.isEmpty(detailEntity.getSupervisionUserSignImg()) && !isEdit) {
            if (detailEntity.getSendBackList() != null && !detailEntity.getSendBackList().isEmpty()) {
                endorseUserList.addAll(getSendBackList());
            } else {
                endorseUserList.add(getAgreeSupervisionData());
            }
        } else {
            endorseUserList.add(getAgreeSupervisionData());
        }
        String safeSupervision = App.getInstance().getString(R.string.safe_supervision_item_title);
        CommonItemType<RPRecordEntity> checkUser = new CommonItemType<>(safeSupervision, ResourcesUtils.getString(R.string.punish_item_tip), R.drawable.plus, isEdit);
        checkUser.setTypeItemList(endorseUserList);
        return checkUser;
    }

    /**
     * @return 修改的监察人
     */
    CommonItemType fetchUpdatePunishUserItem() {
        List<CommonUserEntity> endorseUserList = new ArrayList<>();
        endorseUserList.add(getUpdateAgreeSupervisionData());
        String safeSupervision = App.getInstance().getString(R.string.safe_supervision_item_title);
        CommonItemType<CommonUserEntity> checkUser = new CommonItemType<>(safeSupervision, ResourcesUtils.getString(R.string.punish_item_tip), R.drawable.plus, isEdit);
        checkUser.setTypeItemList(endorseUserList);
        return checkUser;
    }

    /**
     * 被处理对象负责人
     */
    CommonItemType fetchPunishLeaderItem() {
        List<RPRecordEntity> leadUserList = new ArrayList<>();
        leadUserList.add(getPunishLeaderData());
        String safeSupervision = App.getInstance().getString(R.string.punish_leader_title);
        CommonItemType<RPRecordEntity> checkUser = new CommonItemType<>(safeSupervision, ResourcesUtils.getString(R.string.punish_item_tip), R.drawable.plus, isEdit);
        checkUser.setTypeItemList(leadUserList);
        return checkUser;
    }

    /**
     * 被处理修改对象负责人
     */
    CommonItemType fetchUpdatePunishLeaderItem() {
        List<CommonUserEntity> leadUserList = new ArrayList<>();
        leadUserList.add(getUpdatePunishLeaderData());
        String safeSupervision = App.getInstance().getString(R.string.punish_leader_title);
        CommonItemType<CommonUserEntity> checkUser = new CommonItemType<>(safeSupervision, ResourcesUtils.getString(R.string.punish_item_tip), R.drawable.plus, isEdit);
        checkUser.setTypeItemList(leadUserList);
        return checkUser;
    }

    /**
     * 查阅组
     */
    CommonItemType fetchReadList() {
        List<TeamNameEntity> teamNameEntities = new ArrayList<>();
        for (int i = 0; i < detailEntity.getReaderlist().size(); i++) {
            PunishReadEntity punishRead = detailEntity.getReaderlist().get(i);
            TeamNameEntity teamNameEntity = new TeamNameEntity();
            teamNameEntity.setSelect(true);
            teamNameEntity.setTeamId(punishRead.getId());
            teamNameEntity.setTeamName(punishRead.getName());
            teamNameEntities.add(teamNameEntity);
        }

        CommonItemType<TeamNameEntity> readGroupItem = new CommonItemType<>(ResourcesUtils.getString(R.string.punish_read_group_title), ResourcesUtils.getString(R.string.right_slide_look_more), R.drawable.plus, isEdit);
        readGroupItem.setTypeItemList(teamNameEntities);
        return readGroupItem;
    }

}
