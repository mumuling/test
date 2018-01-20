package com.zhongtie.work.ui.safe.order;


import com.zhongtie.work.app.Cache;
import com.zhongtie.work.data.SafeEventEntity;
import com.zhongtie.work.data.create.CommonItemType;
import com.zhongtie.work.data.create.EditContentEntity;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.NetWorkFunc1;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.SafeApi;
import com.zhongtie.work.ui.base.BasePresenterImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Auth: Chaek
 * Date: 2018/1/12
 */

public class SafeDetailPresenterImpl extends BasePresenterImpl<SafeDetailContract.View> implements SafeDetailContract.Presenter {

    boolean isInit;

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
                    setTitleUserInfo(safeEventEntity);
                    initItemList(safeEventEntity);
                    mView.initSuccess();
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
        mView.setEventStatus(safeEventEntity.butstate);

        List<Object> itemList = new ArrayList<>();
        //添加修改要求
        EditContentEntity mRectifyEditContent = new EditContentEntity("整改要求", "", safeEventEntity.event_detail);
        itemList.add(mRectifyEditContent);

        SafeEventModel safeEventModel = new SafeEventModel(safeEventEntity);
        itemList.add(safeEventModel.fetchCheckUserList());
        itemList.add(safeEventModel.getModifyReviewList());
        CommonItemType relate = safeEventModel.getModifyRelatedList();
        if (!relate.getTypeItemList().isEmpty()) {
            itemList.add(relate);
        }
        itemList.add(safeEventModel.fetchReadList());

        String replyTitle = "已回复(" + safeEventEntity.replylist.size() + ")";
        itemList.add(replyTitle);
        itemList.addAll(safeEventEntity.replylist);

        String approveTitle = "已审批(" + safeEventEntity.signlist.size() + ")";
        itemList.add(approveTitle);
        itemList.addAll(safeEventEntity.signlist);

        mView.setItemList(itemList);

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
