package com.zhongtie.work.ui.endorse.create;

import android.support.v4.util.ArrayMap;

import com.zhongtie.work.R;
import com.zhongtie.work.app.App;
import com.zhongtie.work.data.create.CommonItemType;
import com.zhongtie.work.ui.base.BasePresenterImpl;
import com.zhongtie.work.ui.file.select.NormalFile;
import com.zhongtie.work.util.TextUtil;
import com.zhongtie.work.util.ViewUtils;
import com.zhongtie.work.util.upload.UploadUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件签认
 *
 * @author Chaek
 * @date:2018.1.12
 */

public class EndorseCreatePresenterImpl extends BasePresenterImpl<EndorseCreateContract.View> implements EndorseCreateContract.Presenter {

    private ArrayMap<String, CommonItemType> mItemMap;

    /**
     * @return 获取类型
     */
    private List<CommonItemType> fetchCommonItemTypeList() {
        String[] titleList = App.getInstance().getResources().getStringArray(R.array.create_file_sign_item_title);
        String[] tip = App.getInstance().getResources().getStringArray(R.array.create_file_sign_item_tip);
        List<CommonItemType> list = new ArrayList<>();
        int size = titleList.length;
        for (int i = 0; i < size; i++) {
            String title = titleList[i];
            int icon = title.contains("上传") ? R.drawable.ic_file_up1 : R.drawable.plus;
            CommonItemType item = new CommonItemType<>(title, tip[i], icon, true);
            mItemMap.put(titleList[i], item);
            list.add(item);
        }
        return list;
    }


    @Override
    public void getItemList(int safeOrderID) {
        mItemMap = new ArrayMap<>();
        List<Object> itemList = new ArrayList<>();
        itemList.addAll(fetchCommonItemTypeList());
        mView.setItemList(itemList);
    }


    @Override
    public void setSelectList(String title, List createUserEntities) {
        CommonItemType itemType = mItemMap.get(title);
        if (itemType != null) {
            itemType.setTypeItemList(createUserEntities);
        }
    }

    @Override
    public void createEndorse() {
        String title = mView.getEndorseTitle();
        if (TextUtil.isEmpty(title)) {
            mView.showToast(R.string.endorse_input_title_tip);
            return;
        }

        for (String key : mItemMap.keySet()) {
            CommonItemType itemType = mItemMap.get(key);
            if (itemType.getTypeItemList().isEmpty()) {
                mView.showToast("请选择" + itemType.getTitle());
                return;
            }
        }
        String signUserList = mItemMap.get(ViewUtils.getString(R.string.endorse_sign_user_title)).getSelectUserIDList();
        String readGroupList = mItemMap.get(ViewUtils.getString(R.string.endorse_read_group_title)).getTeamIDList();
        List<NormalFile> normalFiles = mItemMap.get(ViewUtils.getString(R.string.endorse_upload_file_title)).getTypeItemList();
        String filePath = normalFiles.get(0).getPath();


    }


}
