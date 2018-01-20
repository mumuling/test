package com.zhongtie.work.ui.safe.order;

import com.zhongtie.work.R;
import com.zhongtie.work.data.ApproveEntity;
import com.zhongtie.work.data.CommonUserEntity;
import com.zhongtie.work.data.EndorseUserEntity;
import com.zhongtie.work.data.ReplyEntity;
import com.zhongtie.work.data.SafeEventEntity;
import com.zhongtie.work.data.create.CommonItemType;
import com.zhongtie.work.util.TextUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Auth:Cheek
 * date:2018.1.20
 */

public class SafeEventModel {
    private SafeEventEntity eventEntity;

    public SafeEventModel(SafeEventEntity eventEntity) {
        this.eventEntity = eventEntity;
    }

    /**
     * 检查人
     */
    public CommonItemType fetchCheckUserList() {
        List<EndorseUserEntity> endorseUserList = new ArrayList<>();
        for (int j = 0; j < 3; j++) {
            endorseUserList.add(new EndorseUserEntity());
        }
        CommonItemType checkUser = new CommonItemType<>("检查人", "", R.drawable.plus, false);
        checkUser.setTypeItemList(endorseUserList);
        return checkUser;
    }

    /**
     * 验证人
     */
    public CommonItemType fetchReviewUserList() {
        List<CommonUserEntity> reviewUserList = new ArrayList<>();
        for (int j = 0, count = eventEntity.reviewlist.size(); j < count; j++) {
            SafeEventEntity.ReviewlistBean review = eventEntity.reviewlist.get(j);
            CommonUserEntity userEntity = new CommonUserEntity();
            userEntity.setUserId(review.userid);
            userEntity.setUserPic(review.userpic);
            userEntity.setUserName(review.username);
            userEntity.setSelect(true);
            reviewUserList.add(userEntity);
        }
        String reviewTitle = "验证人";
        CommonItemType reviewUser = new CommonItemType<>(reviewTitle, "", R.drawable.plus, false);
        reviewUser.setTypeItemList(reviewUserList);
        return reviewUser;
    }

    public List<ReplyEntity> fetchReplyList() {
        List<ReplyEntity> replyList = new ArrayList<>();
        return replyList;
    }

    /**
     * @return 照片list
     */
    public List<String> fetchPicList() {
        List<String> integers = new ArrayList<>();
        String[] t = eventEntity.event_pic.split(",");
        for (String aT : t) {
            if (TextUtil.isEmpty(aT)) {
                continue;
            }
            integers.add(aT);
        }
        return integers;
    }

    /**
     * 审核人
     */
    public List<ApproveEntity> fetchSignList() {
        List<ApproveEntity> signList = new ArrayList<>();
        for (int j = 0, count = eventEntity.signlist.size(); j < count; j++) {
            SafeEventEntity.ReviewlistBean review = (SafeEventEntity.ReviewlistBean) eventEntity.signlist.get(j);
            ApproveEntity userEntity = new ApproveEntity();
            signList.add(userEntity);
        }
        return signList;
    }

}
