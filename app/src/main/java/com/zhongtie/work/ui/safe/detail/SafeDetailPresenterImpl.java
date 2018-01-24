package com.zhongtie.work.ui.safe.detail;


import com.zhongtie.work.app.Cache;
import com.zhongtie.work.data.ApproveEntity;
import com.zhongtie.work.data.EndorseUserEntity;
import com.zhongtie.work.data.SafeEventEntity;
import com.zhongtie.work.data.create.CommonItemType;
import com.zhongtie.work.data.create.EditContentEntity;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.NetWorkFunc1;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.SafeApi;
import com.zhongtie.work.ui.base.BasePresenterImpl;
import com.zhongtie.work.util.L;
import com.zhongtie.work.util.TextUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Flowable;

/**
 * Auth: Chaek
 * Date: 2018/1/12
 */

public class SafeDetailPresenterImpl extends BasePresenterImpl<SafeDetailContract.View> implements SafeDetailContract.Presenter {

    boolean isInit;

    private List<Object> itemList = new ArrayList<>();

    private SafeEventEntity eventEntity;
    private boolean isShowCheck;

    private boolean isHideNullItem;

    @Override
    public void showCheck() {
        isShowCheck = false;
        initItemList(eventEntity);
    }

    @Override
    public void changeCheckListState() {
        if (isShowCheck) {
            Iterator<Object> iterator = itemList.iterator();
            while (iterator.hasNext()) {
                Object o = iterator.next();
                if (o instanceof EndorseUserEntity) {
                    iterator.remove();
                }
            }
            isShowCheck = false;
            mView.setCheckCount(0);
        } else {
            SafeEventModel safeEventModel = new SafeEventModel(eventEntity);
            CommonItemType checkUser = safeEventModel.fetchCheckUserList();
            mView.setCheckCount(checkUser.getTypeItemList().size());
            itemList.addAll(isHideNullItem ? 1 : 2, checkUser.getTypeItemList());
            isShowCheck = true;
            mView.setCheckCount(checkUser.getTypeItemList().size());
        }
    }

    @Override
    public void getItemList(int safeOrderID) {
        if (!isInit) {
            mView.initLoading();
        }
        addDispose(Http.netServer(SafeApi.class)
                .eventDetails(Cache.getUserID(), safeOrderID)
                .map(new NetWorkFunc1<>())
                .compose(Network.networkIO())
                .subscribe(safeEventEntity -> {
                    this.eventEntity = safeEventEntity;
                    mView.setEventStatus(safeEventEntity.butstate);
                    setTitleUserInfo(safeEventEntity);
                    initItemList(safeEventEntity);
                    isInit = true;
                }, throwable -> {
                    throwable.printStackTrace();
                    String msg = throwable.getMessage();
                    if ("您无权查看".equals(msg)) {
                        mView.noLookAuthority();
                    } else {
                        mView.initFail();
                    }
                }));
    }


    private void initItemList(SafeEventEntity safeEventEntity) {
        addDispose(Flowable.fromCallable(() -> {
            itemList = new ArrayList<>();
            isHideNullItem = true;
            //添加修改要求
            if (!TextUtil.isEmpty(safeEventEntity.event_changemust)) {
                EditContentEntity mRectifyEditContent = new EditContentEntity("整改要求", "", safeEventEntity.event_changemust);
                itemList.add(mRectifyEditContent);
                isHideNullItem = false;
            }

            SafeEventModel safeEventModel = new SafeEventModel(safeEventEntity);
            CommonItemType checkUser = safeEventModel.fetchCheckUserList();
            itemList.add(checkUser);
            //检查人的由主list 展示 避免过多情况引起性能问题
            if (isShowCheck) {
                mView.setCheckCount(checkUser.getTypeItemList().size());
                itemList.addAll(checkUser.getTypeItemList());
            }else {
                mView.setCheckCount(0);
            }
            checkUser.setTypeItemList(new ArrayList());

            itemList.add(safeEventModel.getModifyReviewList());
            CommonItemType relate = safeEventModel.getRelatedList();
            if (!relate.getTypeItemList().isEmpty()) {
                itemList.add(relate);
            }
            itemList.add(safeEventModel.fetchReadList());
            String replyTitle = "已回复(" + safeEventEntity.replylist.size() + ")";
            itemList.add(replyTitle);
            itemList.addAll(safeEventEntity.replylist);

            List<ApproveEntity> approveEntities = safeEventModel.getDetailReviewUserList();
            String approveTitle = "已验证(" + approveEntities.size() + ")";
            itemList.add(approveTitle);
            itemList.addAll(approveEntities);
            L.e("------------", System.currentTimeMillis() + "");
            return isHideNullItem;
        }).compose(Network.networkIO())
                .subscribe(isHideNullItem -> {
                    mView.initSuccess();
                    mView.setItemList(itemList, isHideNullItem);
                    L.e("------------", System.currentTimeMillis() + "");
                }, throwable -> {
                    mView.initFail();
                }));

    }

    @Override
    public void setSelectImageList(List<String> selectImgList) {

    }

    @Override
    public void setSelectUserInfoList(String title, List createUserEntities) {

    }

    private void setTitleUserInfo(SafeEventEntity titleUserInfo) {
        mView.setSafeDetailInfo(titleUserInfo);
    }


}
