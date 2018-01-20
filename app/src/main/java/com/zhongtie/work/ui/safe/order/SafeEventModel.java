package com.zhongtie.work.ui.safe.order;

import com.zhongtie.work.R;
import com.zhongtie.work.data.CommonUserEntity;
import com.zhongtie.work.data.EndorseUserEntity;
import com.zhongtie.work.data.ReplyEntity;
import com.zhongtie.work.data.SafeEventEntity;
import com.zhongtie.work.data.TeamNameEntity;
import com.zhongtie.work.data.create.CommonItemType;
import com.zhongtie.work.util.TextUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析安全督导详情
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
        //获取姓名list
        List<String> checkUserNameList = TextUtil.getPicList(eventEntity.getEvent_checker());
        for (int j = 0; j < checkUserNameList.size(); j++) {
            EndorseUserEntity endorseUserEntity=new EndorseUserEntity();
            endorseUserEntity.setUsername(checkUserNameList.get(j));
            endorseUserList.add(endorseUserEntity);
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

    /**
     * 整改人
     */
    public CommonItemType fetchRelatedUserList() {
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
        String reviewTitle = "整改人";
        CommonItemType reviewUser = new CommonItemType<>(reviewTitle, "", R.drawable.plus, false);
        reviewUser.setTypeItemList(reviewUserList);
        return reviewUser;
    }

    /**
     * 修改界面获取检查人
     */
    public CommonItemType getModifyCheckList() {
        List<CommonUserEntity> modifyCheckList = new ArrayList<>();
        //获取姓名list
        List<String> checkUserNameList = TextUtil.getPicList(eventEntity.getEvent_checker());
        for (int i = 0, count = checkUserNameList.size(); i < count; i++) {
            CommonUserEntity userEntity = new CommonUserEntity();
            userEntity.setUserName(checkUserNameList.get(i));
            userEntity.setSelect(true);
            modifyCheckList.add(userEntity);
        }
        String reviewTitle = "检查人";
        CommonItemType<CommonUserEntity> reviewUser = new CommonItemType<>(reviewTitle, "向右滑动查看更新", R.drawable.plus, false);
        reviewUser.setTypeItemList(modifyCheckList);
        return reviewUser;
    }

    /**
     * 修改界面获取 验证人
     */
    public CommonItemType getModifyReviewList() {
        List<CommonUserEntity> modifyReviewList = new ArrayList<>();
        for (int j = 0, count = eventEntity.reviewlist.size(); j < count; j++) {
            SafeEventEntity.ReviewlistBean review = eventEntity.reviewlist.get(j);
            CommonUserEntity userEntity = new CommonUserEntity(review);
            modifyReviewList.add(userEntity);
        }
        CommonItemType<CommonUserEntity> reviewUser = new CommonItemType<>("验证人", "最多两人", R.drawable.plus, false);
        reviewUser.setTypeItemList(modifyReviewList);
        return reviewUser;
    }

    /**
     * 修改界面获取 整改人
     */
    public CommonItemType getModifyRelatedList() {
        List<CommonUserEntity> modifyRelatedList = new ArrayList<>();
        //获取姓名list
        List<String> checkUserNameList = TextUtil.getPicList(eventEntity.getEvent_related());
        for (int i = 0, count = checkUserNameList.size(); i < count; i++) {
            CommonUserEntity userEntity = new CommonUserEntity();
            userEntity.setUserName(checkUserNameList.get(i));
            userEntity.setSelect(true);
            modifyRelatedList.add(userEntity);
        }
        CommonItemType<CommonUserEntity> modifyRelated = new CommonItemType<>("整改人", "向右滑动查看更新", R.drawable.plus, false);
        modifyRelated.setTypeItemList(modifyRelatedList);
        return modifyRelated;
    }


    /**
     * 查阅组
     */
    public CommonItemType fetchReadList() {
        List<TeamNameEntity> reviewUserList = new ArrayList<>();
        //获取姓名list
        List<String> checkUserNameList = TextUtil.getPicList(eventEntity.getEvent_read());
        for (int j = 0, count = checkUserNameList.size(); j < count; j++) {
            String review = checkUserNameList.get(j);
            TeamNameEntity team = new TeamNameEntity();
            team.setTeamName(review);
            reviewUserList.add(team);
        }
        CommonItemType<TeamNameEntity> reviewUser = new CommonItemType<>("查阅组", "向右滑动查看更新", R.drawable.plus, false);
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


}
