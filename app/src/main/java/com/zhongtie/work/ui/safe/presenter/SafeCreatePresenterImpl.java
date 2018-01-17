package com.zhongtie.work.ui.safe.presenter;


import android.support.v4.util.ArrayMap;

import com.zhongtie.work.R;
import com.zhongtie.work.app.App;
import com.zhongtie.work.data.ProjectTeamEntity;
import com.zhongtie.work.data.create.CommonItemType;
import com.zhongtie.work.data.create.EventTypeEntity;
import com.zhongtie.work.data.create.SelectEventTypeItem;
import com.zhongtie.work.data.create.EditContentEntity;
import com.zhongtie.work.ui.base.BasePresenterImpl;
import com.zhongtie.work.util.TextUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 安全督导创建的实现
 * Date: 2018/1/12
 *
 * @author Chaek
 */
public class SafeCreatePresenterImpl extends BasePresenterImpl<SafeCreateContract.View> implements SafeCreateContract.Presenter {

    /**
     * 选择类型
     */
    private SelectEventTypeItem mCommonItemType;
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
    /**
     * map 储存为
     */
    private ArrayMap<String, CommonItemType> mGroupTypeArrayMap;

    /**
     * 获取输入安全督导的基本类型
     *
     * @return 获取类型list
     */
    private List<CommonItemType> fetchCommonItemTypeList() {
        String[] titleList = App.getInstance().getResources().getStringArray(R.array.create_item_title);
        String[] tip = App.getInstance().getResources().getStringArray(R.array.create_item_tip);
        List<CommonItemType> list = new ArrayList<>();
        for (int i = 0, size = titleList.length; i < size; i++) {
            String title = titleList[i];
            CommonItemType item = new CommonItemType<>(title, tip[i], R.drawable.plus, true);
            mGroupTypeArrayMap.put(title, item);
            list.add(item);
        }
        return list;
    }

    private SelectEventTypeItem fetchSelectTypeItemData() {
        mCommonItemType = new SelectEventTypeItem("问题类型");
        String[] typeList = App.getInstance().getResources().getStringArray(R.array.type_list);
        List<EventTypeEntity> list = new ArrayList<>();
        int size = typeList.length;
        for (int i = 0; i < size; i++) {
            list.add(new EventTypeEntity(typeList[i], i, false));
        }
        mCommonItemType.setTypeItemList(list);
        return mCommonItemType;
    }

    @Override
    public void getItemList(int safeOrderID) {
        List<Object> itemList = new ArrayList<>();
        mGroupTypeArrayMap = new ArrayMap<>();

        mCommonItemType = fetchSelectTypeItemData();
        itemList.add(fetchSelectTypeItemData());
        mDescribeEditContent = new EditContentEntity("描述", "请输入事件描述", "");
        //添加描述
        itemList.add(mDescribeEditContent);
        //添加修改要求
        mRectifyEditContent = new EditContentEntity("整改要求", "请输入整改要求", "");
        itemList.add(mRectifyEditContent);

        //添加图片
        mPicItemType = new CommonItemType<String>("上传照片", "最多12张", R.drawable.ic_cam, true);
        itemList.add(mPicItemType);
        itemList.addAll(fetchCommonItemTypeList());
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
        CommonItemType itemType = mGroupTypeArrayMap.get(title);
        if (itemType != null) {
            itemType.setTypeItemList(createUserEntities);
        }
    }

    @Override
    public void createSafeOrder() {
        String site = mView.getEditSite();
        if (TextUtil.isEmpty(site)) {
            mView.showToast(R.string.safe_create_input_site);
            return;
        }
        ProjectTeamEntity unit = mView.getCompanyUnitEntity();
        if (unit == null) {
            mView.showToast("请选择单位");
            return;
        }
        ProjectTeamEntity companyTeam = mView.getCompanyTeamEntity();
        if (companyTeam == null) {
            mView.showToast("请选择劳务公司");
            return;
        }
        List<EventTypeEntity> typeList = mCommonItemType.getCheckEventTypeList();
        if (typeList.isEmpty()) {
            mView.showToast("请选择问题类型");
            return;
        }
        String describe = mDescribeEditContent.getContent();
        if (TextUtil.isEmpty(describe)) {
            mView.showToast("请输入问题描述");
            return;
        }
        String rectify = mRectifyEditContent.getContent();
        if (TextUtil.isEmpty(rectify)) {
            mView.showToast("请输入整改内容");
            return;
        }

        List<String> picList = mPicItemType.getTypeItemList();
        if (picList.isEmpty()) {
            mView.showToast("请至少选择一张图片");
            return;
        }

        for (String key : mGroupTypeArrayMap.keySet()) {
            CommonItemType itemType = mGroupTypeArrayMap.get(key);
            if (itemType.getTypeItemList().isEmpty()) {
                mView.showToast("请选择" + itemType.getTitle());
                return;
            }
        }


    }
}
