package com.zhongtie.work.ui.safe.order;


import android.support.v4.util.ArrayMap;

import com.zhongtie.work.R;
import com.zhongtie.work.app.App;
import com.zhongtie.work.app.Cache;
import com.zhongtie.work.data.ApproveEntity;
import com.zhongtie.work.data.CommonUserEntity;
import com.zhongtie.work.data.EndorseUserEntity;
import com.zhongtie.work.data.ReplyEntity;
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

import io.reactivex.functions.Function;

import static com.zhongtie.work.ui.safe.SafeSupervisionCreateFragment.imageUrls;

/**
 * Auth: Chaek
 * Date: 2018/1/12
 */

public class SafeDetailPresenterImpl extends BasePresenterImpl<SafeDetailContract.View> implements SafeDetailContract.Presenter {


    /**
     * 描述编辑数据
     */
    private EditContentEntity mDescribeEditContent;
    /**
     * 整改内容
     */
    private EditContentEntity mRectifyEditContent;

    /**
     * 图片信息
     */
    private CommonItemType<String> mPicItemType;

    private ArrayMap<String, CommonItemType> mTypeArrayMap;

    /**
     * @return 获取类型
     */
    private List<CommonItemType> fetchCommonItemTypeList() {
        String[] titleList = App.getInstance().getResources().getStringArray(R.array.create_item_title);
        String[] tip = App.getInstance().getResources().getStringArray(R.array.create_item_tip);
        List<CommonItemType> list = new ArrayList<>();
        List<CommonUserEntity> createUserEntities = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            CommonUserEntity c = new CommonUserEntity();
            c.setUserId(i);
            c.setUserName("测试" + 1);
            c.setUserPic(imageUrls[i]);
            createUserEntities.add(c);

        }
        int size = titleList.length;
        List<EndorseUserEntity> files = new ArrayList<>();
        for (int j = 0; j < 2; j++) {
            files.add(new EndorseUserEntity());
        }

        for (int i = 0; i < size; i++) {
            CommonItemType item = new CommonItemType<>(titleList[i], tip[i], R.drawable.plus, false);
            if (titleList[i].contains("检查")) {
                item.setTypeItemList(files);
            } else {
                item.setTypeItemList(createUserEntities);
            }
            mTypeArrayMap.put(titleList[i], item);
            list.add(item);
        }
        return list;
    }


    @Override
    public void getItemList(int safeOrderID) {
        Http.netServer(SafeApi.class)
                .eventDetails(Cache.getUserID(), safeOrderID)
                .map(new NetWorkFunc1<>())
                .compose(Network.networkIO())
                .subscribe(safeEventEntity -> {
                    setTitleUserInfo(safeEventEntity);
                    initItemList(safeEventEntity);
                }, throwable -> {
                });
    }


    private void initItemList(SafeEventEntity safeEventEntity) {
        List<Object> itemList = new ArrayList<>();
        mTypeArrayMap = new ArrayMap<>();
        //添加修改要求
        mRectifyEditContent = new EditContentEntity("整改要求", "", safeEventEntity.event_detail);
        itemList.add(mRectifyEditContent);

        //检查人
//        CommonItemType item = new CommonItemType<>("检查人", "", R.drawable.plus, false);
//        if (titleList[i].contains("检查")) {
//            item.setTypeItemList(files);
//        } else {
//            item.setTypeItemList(createUserEntities);
//        }
//        mTypeArrayMap.put(titleList[i], item);
//        list.add(item);

        itemList.addAll(fetchCommonItemTypeList());

        SafeEventModel safeEventModel=new SafeEventModel(safeEventEntity);

        itemList.add(safeEventModel.fetchReviewUserList());


        String replyTitle = "回复(" + safeEventEntity.replylist.size() + ")";
        itemList.add(replyTitle);

        for (int i = 0; i < safeEventEntity.replylist.size(); i++) {
            itemList.add(new ReplyEntity());
        }

        itemList.add("审批(4)");
        for (int i = 0; i < 2; i++) {
            itemList.add(new ApproveEntity());
        }

        mView.setItemList(itemList);

    }

    @Override
    public void setSelectImageList(List<String> selectImgList) {
        List<String> list = mPicItemType.getTypeItemList();
        for (String image : selectImgList) {
            if (!list.contains(image)) {
                list.add(image);
            }
        }
        mPicItemType.setTypeItemList(list);
    }

    @Override
    public void setSelectUserInfoList(String title, List createUserEntities) {
        CommonItemType itemType = mTypeArrayMap.get(title);
        if (itemType != null) {
            itemType.setTypeItemList(createUserEntities);
        }
    }

    public void setTitleUserInfo(SafeEventEntity titleUserInfo) {
        mView.setSafeDetailInfo(titleUserInfo);
    }

    private static class ConvertItemListFunc implements Function<SafeEventEntity, List<Object>> {
        @Override
        public List<Object> apply(SafeEventEntity safeEventEntity) throws Exception {
            return null;
        }
    }


}
