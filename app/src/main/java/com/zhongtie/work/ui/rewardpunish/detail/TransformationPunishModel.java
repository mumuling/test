package com.zhongtie.work.ui.rewardpunish.detail;

import com.zhongtie.work.R;
import com.zhongtie.work.app.App;
import com.zhongtie.work.data.RPRecordEntity;
import com.zhongtie.work.data.RewardPunishDetailEntity;
import com.zhongtie.work.data.TeamNameEntity;
import com.zhongtie.work.data.create.CommonItemType;
import com.zhongtie.work.util.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 安全奖罚数据转换
 * Auth:Cheek
 * date:2018.1.27
 */

public class TransformationPunishModel {
    private RewardPunishDetailEntity detailEntity;
    private boolean isEdit;

    public TransformationPunishModel(RewardPunishDetailEntity detailEntity) {
        this(detailEntity, false);
    }

    public TransformationPunishModel(RewardPunishDetailEntity detailEntity, boolean isEdit) {
        this.detailEntity = detailEntity;
        this.isEdit = isEdit;
    }

    /**
     * 获取安全监察对象惩罚人
     */
    public CommonItemType fetchPunishUserItem() {
        List<RPRecordEntity> endorseUserList = new ArrayList<>();
        String safeSupervision = App.getInstance().getString(R.string.safe_supervision_item_title);
        CommonItemType checkUser = new CommonItemType<>(safeSupervision, ViewUtils.getString(R.string.punish_item_tip), R.drawable.plus, isEdit);
        checkUser.setTypeItemList(endorseUserList);
        return checkUser;
    }

    /**
     * 被处理对象负责人
     */
    public CommonItemType fetchPunishLeaderItem() {
        List<RPRecordEntity> endorseUserList = new ArrayList<>();
        String safeSupervision = App.getInstance().getString(R.string.punish_leader_item_title);
        CommonItemType checkUser = new CommonItemType<>(safeSupervision, ViewUtils.getString(R.string.punish_item_tip), R.drawable.plus, isEdit);
        checkUser.setTypeItemList(endorseUserList);
        return checkUser;
    }

    /**
     * 查阅组
     */
    public CommonItemType fetchReadList() {
        CommonItemType<TeamNameEntity> reviewUser = new CommonItemType<>(ViewUtils.getString(R.string.punish_read_group_title), ViewUtils.getString(R.string.right_slide_look_more), R.drawable.plus, isEdit);
        return reviewUser;
    }

}
