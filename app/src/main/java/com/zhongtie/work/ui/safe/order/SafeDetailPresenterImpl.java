package com.zhongtie.work.ui.safe.order;


import android.support.v4.util.ArrayMap;

import com.zhongtie.work.R;
import com.zhongtie.work.app.App;
import com.zhongtie.work.data.ApproveEntity;
import com.zhongtie.work.data.ReplyEntity;
import com.zhongtie.work.data.create.CommonItemType;
import com.zhongtie.work.data.create.CreateUserEntity;
import com.zhongtie.work.data.create.EditContentEntity;
import com.zhongtie.work.ui.base.BasePresenterImpl;
import com.zhongtie.work.ui.safe.SafeCreateContract;

import java.util.ArrayList;
import java.util.List;

import static com.zhongtie.work.ui.safe.SafeSupervisionCreateFragment.imageUrls;

/**
 * Auth: Chaek
 * Date: 2018/1/12
 */

public class SafeDetailPresenterImpl extends BasePresenterImpl<SafeCreateContract.View> implements SafeCreateContract.Presenter {


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
        List<CreateUserEntity> createUserEntities=new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            CreateUserEntity c=new CreateUserEntity();
            c.setUserId(i);
            c.setUserName("测试"+1);
            c.setUserPic(imageUrls[i]);
            createUserEntities.add(c);

        }
        int size = titleList.length;
        for (int i = 0; i < size; i++) {
            CommonItemType item = new CommonItemType<>(titleList[i], tip[i], R.drawable.plus, false);
            item.setTypeItemList(createUserEntities);
            mTypeArrayMap.put(titleList[i], item);
            list.add(item);
        }
        return list;
    }


    @Override
    public void getItemList(int safeOrderID) {
        List<Object> itemList = new ArrayList<>();
        mTypeArrayMap = new ArrayMap<>();

        //添加修改要求
        mRectifyEditContent = new EditContentEntity("整改要求", "请输入整改要求", "确定确定确定确定确定确定确定确定确定确定确定确定确定确定确定确定确定确定确定确定确定确定确定确定确定确定确定确定确定确定确定确定");
        itemList.add(mRectifyEditContent);

        itemList.addAll(fetchCommonItemTypeList());

        itemList.add("回复(3)");
        for (int i = 0; i < 3; i++) {
            itemList.add(new ReplyEntity());
        }
        itemList.add("审批(4)");
        for (int i = 0; i < 4; i++) {
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

    @Override
    public void createSafeOrder() {

    }


}
