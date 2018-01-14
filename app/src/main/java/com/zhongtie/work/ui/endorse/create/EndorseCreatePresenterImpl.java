package com.zhongtie.work.ui.endorse.create;

import android.support.v4.util.ArrayMap;

import com.zhongtie.work.R;
import com.zhongtie.work.app.App;
import com.zhongtie.work.data.create.CommonItemType;
import com.zhongtie.work.ui.base.BasePresenterImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Auth:Cheek
 * date:2018.1.12
 */

public class EndorseCreatePresenterImpl extends BasePresenterImpl<EndorseCreateContract.View> implements EndorseCreateContract.Presenter {

    private ArrayMap<String, CommonItemType> mTypeArrayMap;

    /**
     * @return 获取类型
     */
    private List<CommonItemType> fetchCommonItemTypeList() {
        String[] titleList = App.getInstance().getResources().getStringArray(R.array.create_file_sign_item_title);
        String[] tip = App.getInstance().getResources().getStringArray(R.array.create_file_sign_item_tip);
        List<CommonItemType> list = new ArrayList<>();
        int size = titleList.length;
        for (int i = 0; i < size; i++) {
            CommonItemType item = new CommonItemType<>(titleList[i], tip[i], titleList[i].contains("上传") ? R.drawable.ic_file_up1 : R.drawable.plus, true);
            mTypeArrayMap.put(titleList[i], item);
            list.add(item);
        }
        return list;
    }


    @Override
    public void getItemList(int safeOrderID) {
        mTypeArrayMap = new ArrayMap<>();
        List<Object> itemList = new ArrayList<>();
        itemList.addAll(fetchCommonItemTypeList());
        mView.setItemList(itemList);
    }


    @Override
    public void setSelectUserInfoList(String title, List createUserEntities) {
        CommonItemType itemType = mTypeArrayMap.get(title);
        if (itemType != null) {
            itemType.setTypeItemList(createUserEntities);
        }
    }

}
