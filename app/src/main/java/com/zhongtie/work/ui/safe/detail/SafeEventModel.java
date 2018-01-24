package com.zhongtie.work.ui.safe.detail;

import com.zhongtie.work.R;
import com.zhongtie.work.data.ApproveEntity;
import com.zhongtie.work.data.CommonUserEntity;
import com.zhongtie.work.data.EndorseUserEntity;
import com.zhongtie.work.data.EventPicEntity;
import com.zhongtie.work.data.SafeEventEntity;
import com.zhongtie.work.data.TeamNameEntity;
import com.zhongtie.work.data.create.CommonItemType;
import com.zhongtie.work.util.TextUtil;
import com.zhongtie.work.util.TimeUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 解析安全督导详情
 * Auth:Cheek
 * date:2018.1.20
 */

public class SafeEventModel {
    private SafeEventEntity eventEntity;
    private List<Integer> atUserList = new ArrayList<>();
    boolean isEdit;

    public SafeEventModel(SafeEventEntity eventEntity) {
        this(eventEntity, false);
    }

    public SafeEventModel(SafeEventEntity eventEntity, boolean isEdit) {
        this.isEdit = isEdit;
        this.eventEntity = eventEntity;
        for (ApproveEntity approveEntity : eventEntity.atlist) {
            atUserList.add(approveEntity.getUserid());
        }
    }

    /**
     * 安全督导详情 获取检查人数据 会展示界面签名数据 所以需要重新垢找一个类
     */
    public CommonItemType fetchCheckUserList() {
        List<EndorseUserEntity> endorseUserList = new ArrayList<>(eventEntity.signlist.size());
        for (int j = 0; j < eventEntity.signlist.size(); j++) {
            ApproveEntity entity = eventEntity.signlist.get(j);
            EndorseUserEntity endorseUserEntity = new EndorseUserEntity();
            endorseUserEntity.setUsername(entity.username);
            endorseUserEntity.setDetail(entity.detail);
            endorseUserEntity.setPic(entity.userpic);
            endorseUserEntity.setTime(entity.time);
            endorseUserEntity.setUrl(entity.url);
            endorseUserEntity.setUserid(entity.userid);
            endorseUserList.add(endorseUserEntity);
        }
        Collections.sort(endorseUserList, (o1, o2) -> {
            if (TimeUtils.formatSignTime(o1.getTime()) > TimeUtils.formatSignTime(o2.getTime())) {
                return -1;
            }
            return 1;
        });

        CommonItemType checkUser = new CommonItemType<>("检查人", "", R.drawable.plus, isEdit);
        checkUser.setTypeItemList(endorseUserList);

        return checkUser;
    }

    /**
     * 详情展示验证人就是审核人
     */
    public List<ApproveEntity> getDetailReviewUserList() {
        List<ApproveEntity> reviewUserList = new ArrayList<>();
        for (int j = 0, count = eventEntity.reviewlist.size(); j < count; j++) {
            ApproveEntity review = eventEntity.reviewlist.get(j);
            if (TextUtil.isEmpty(review.url) || TextUtil.isEmpty(review.time)) {
                continue;
            }
            reviewUserList.add(review);
        }
        return reviewUserList;
    }


    /**
     * 判断是否属于AT人
     *
     * @param userId 用户ID
     * @return true 属于AT false不是
     */
    private boolean isAt(int userId) {
        return atUserList.contains(userId);
    }

    /**
     * 修改界面获取检查人基本信息
     */
    public CommonItemType getModifyCheckList() {
        List<CommonUserEntity> modifyCheckList = new ArrayList<>(eventEntity.signlist.size());
        for (int i = 0, count = eventEntity.signlist.size(); i < count; i++) {
            ApproveEntity approveEntity = eventEntity.signlist.get(i);
            CommonUserEntity userEntity = new CommonUserEntity(approveEntity);
            userEntity.setAt(isAt(approveEntity.getUserid()));
            modifyCheckList.add(userEntity);
        }
        String reviewTitle = "检查人";
        CommonItemType<CommonUserEntity> reviewUser = new CommonItemType<>(reviewTitle, "向右滑动查看更多", R.drawable.plus, isEdit);
        reviewUser.setTypeItemList(modifyCheckList);
        return reviewUser;
    }

    /**
     * 修改界面获取 验证人 信息
     */
    public CommonItemType getModifyReviewList() {
        List<CommonUserEntity> modifyReviewList = new ArrayList<>();
        for (int j = 0, count = eventEntity.reviewlist.size(); j < count; j++) {
            ApproveEntity review = eventEntity.reviewlist.get(j);
            CommonUserEntity userEntity = new CommonUserEntity(review);
            userEntity.setAt(isAt(review.getUserid()));

            modifyReviewList.add(userEntity);
        }
        CommonItemType<CommonUserEntity> reviewUser = new CommonItemType<>("验证人", "最多两人", R.drawable.plus, isEdit);
        reviewUser.setTypeItemList(modifyReviewList);
        return reviewUser;
    }

    /**
     * 修改界面获取 整改人
     */
    public CommonItemType getRelatedList() {
        List<CommonUserEntity> modifyRelatedList = new ArrayList<>(eventEntity.relatedlist.size());
        //获取姓名list
        for (int i = 0, count = eventEntity.relatedlist.size(); i < count; i++) {
            CommonUserEntity userEntity = new CommonUserEntity(eventEntity.relatedlist.get(i));
            userEntity.setAt(isAt(userEntity.getUserId()));

            modifyRelatedList.add(userEntity);
        }
        CommonItemType<CommonUserEntity> modifyRelated = new CommonItemType<>("整改人", "向右滑动查看更新", R.drawable.plus, isEdit);
        modifyRelated.setTypeItemList(modifyRelatedList);
        return modifyRelated;
    }


    /**
     * 查阅组
     */
    public CommonItemType fetchReadList() {
        CommonItemType<TeamNameEntity> reviewUser = new CommonItemType<>("查阅组", "向右滑动查看更新", R.drawable.plus, isEdit);
        reviewUser.setTypeItemList(eventEntity.getReadlist());
        return reviewUser;
    }

    /**
     * 获取图片地址
     *
     * @return 图片信息
     */
    public List<String> getEventPicList() {
        List<String> pic = new ArrayList<>();
        for (EventPicEntity picEntity : eventEntity.getEventpiclist()) {
            pic.add(picEntity.getPicurl());
        }
        return pic;
    }


}
