package com.zhongtie.work.ui.rewardpunish;

import android.support.v4.util.ArrayMap;

import com.zhongtie.work.R;
import com.zhongtie.work.app.App;
import com.zhongtie.work.data.RPRecordEntity;
import com.zhongtie.work.data.create.EventTypeEntity;
import com.zhongtie.work.data.create.CommonItemType;
import com.zhongtie.work.data.create.SelectEventTypeItem;
import com.zhongtie.work.data.CommonUserEntity;
import com.zhongtie.work.data.create.EditContentEntity;
import com.zhongtie.work.ui.base.BasePresenterImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Auth:Cheek
 * date:2018.1.12
 */

public class RPCreatePresenterImpl extends BasePresenterImpl<RewardPunishCreateContract.View> implements RewardPunishCreateContract.Presenter {
    /**
     * 描述编辑数据
     */
    private EditContentEntity mDescribeEditContent;
    /**
     * 整改内容
     */
    private EditContentEntity mRectifyEditContent;
    private ArrayMap<String, CommonItemType> mTypeArrayMap;

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
            mTypeArrayMap.put(titleList[i], item);
            list.add(item);
        }
        return list;
    }

    private SelectEventTypeItem fetchSelectTypeItemData() {
        SelectEventTypeItem selectTypeItem = new SelectEventTypeItem("问题类型");
        String[] typeList = App.getInstance().getResources().getStringArray(R.array.type_list);
        List<EventTypeEntity> list = new ArrayList<>();
        int size = typeList.length;
        for (int i = 0; i < size; i++) {
            list.add(new EventTypeEntity(typeList[i], i, false));
        }
        selectTypeItem.setTypeItemList(list);
        return selectTypeItem;
    }

    @Override
    public void getItemList(int safeOrderID) {
        List<Object> itemList = new ArrayList<>();
        mTypeArrayMap = new ArrayMap<>();
        mDescribeEditContent = new EditContentEntity("摘要", "请输入摘要说明", "");
        mRectifyEditContent = new EditContentEntity("详细情况", "请输入详细情况", "");

        //添加描述
        itemList.add(mDescribeEditContent);
        //添加修改要求
        itemList.add(mRectifyEditContent);
        itemList.addAll(fetchCommonItemTypeList());
        mView.setItemList(itemList);
    }


    @Override
    public void setSelectUserInfoList(String title, List createUserEntities) {
        CommonItemType itemType = mTypeArrayMap.get(title);
        if (itemType != null) {
            if (!title.contains("查阅")) {
                List<RPRecordEntity> list = new ArrayList<>();
                for (int i = 0; i < createUserEntities.size(); i++) {
                    CommonUserEntity createUserEntity = (CommonUserEntity) createUserEntities.get(i);
                    RPRecordEntity rpRecordEntity = new RPRecordEntity();
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
