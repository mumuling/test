package com.zhongtie.work.ui.safe.presenter;


import android.support.v4.util.ArrayMap;

import com.zhongtie.work.R;
import com.zhongtie.work.app.App;
import com.zhongtie.work.app.Cache;
import com.zhongtie.work.data.ProjectTeamEntity;
import com.zhongtie.work.data.TeamNameEntity;
import com.zhongtie.work.data.create.CommonItemType;
import com.zhongtie.work.data.create.CreateUserEntity;
import com.zhongtie.work.data.create.EditContentEntity;
import com.zhongtie.work.data.create.EventTypeEntity;
import com.zhongtie.work.data.create.SelectEventTypeItem;
import com.zhongtie.work.db.CacheSafeEventTable;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.SafeApi;
import com.zhongtie.work.ui.base.BasePresenterImpl;
import com.zhongtie.work.util.TextUtil;
import com.zhongtie.work.util.upload.UploadUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

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

        boolean isLeader = Cache.isLeader();

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

        //////////////////////////////
        boolean isCheckValue = checkValue();
        ProjectTeamEntity companyTeam = mView.getCompanyTeamEntity();
        //领导不验证不验证 劳务公司
        if (companyTeam == null && !isLeader && isCheckValue) {
            mView.showToast("请选择劳务公司");
            return;
        }


        List<EventTypeEntity> typeList = mCommonItemType.getCheckEventTypeList();
        if (typeList.isEmpty() && isCheckValue) {
            mView.showToast("请选择问题类型");
            return;
        }

        String rectify = mRectifyEditContent.getContent();
        if (TextUtil.isEmpty(rectify) && isCheckValue) {
            mView.showToast("请输入整改内容");
            return;
        }

        CommonItemType<CreateUserEntity> relatedUser = mGroupTypeArrayMap.get("整改人");
        if (rectify.isEmpty() && isCheckValue) {
            mView.showToast("请选择整改人");
            return;
        }

        ////////////////////////////////////

        String describe = mDescribeEditContent.getContent();
        if (TextUtil.isEmpty(describe)) {
            mView.showToast("请输入问题描述");
            return;
        }

        List<String> picList = mPicItemType.getTypeItemList();
        if (picList.isEmpty()) {
            mView.showToast("请至少选择一张图片");
            return;
        }


        CacheSafeEventTable cache = new CacheSafeEventTable();
        //整改人
        String related = !isCheckValue ? "" : relatedUser.getSelectUserIDList();

        cache.setRead(related);
        cache.setImageList(picList);
        cache.setUserid(Integer.valueOf(Cache.getUserID()));
        cache.setCompany(unit.getProjectTeamID());
        cache.setTime(mView.getSelectDate());
        cache.setLocal(site);
        if (companyTeam == null) {
            cache.setWorkerteam(0);
        } else {
            cache.setWorkerteam(companyTeam.getProjectTeamID());
        }
        String type = mCommonItemType.getCheckEventTypeString();
        cache.setTroubletype(type);
        cache.setDetail(describe);
        cache.setChangemust(rectify);


        CommonItemType<CreateUserEntity> checkUser = mGroupTypeArrayMap.get("检查人");
        if (checkUser.getTypeItemList().isEmpty()) {
            mView.showToast("请选择检查人");
            return;
        }
        String check = checkUser.getSelectUserIDList();
        cache.setChecker(check);

        CommonItemType<CreateUserEntity> reviewUser = mGroupTypeArrayMap.get("验证人");

        if (reviewUser.getTypeItemList().size() != 2) {
            mView.showToast("验证人有且只能有2个人");
            return;
        }
        cache.setReview(reviewUser.getSelectUserIDList());


        //计算所有AT人数
        String atUser = getAtListString(checkUser, reviewUser, relatedUser);
        cache.setAt(atUser);

        //查阅组
        CommonItemType<TeamNameEntity> temLis = mGroupTypeArrayMap.get("查阅组");
        if (temLis.getTypeItemList().isEmpty()) {
            mView.showToast("请选择查阅组");
            return;
        }
        String read = temLis.getTeamIDList();
        cache.setRead(read);

        UploadUtil.uploadListJPGIDList(cache.getImageList())
                .map(s -> {
                    cache.setPic(s);
                    return cache;
                })
                .map(CacheSafeEventTable::getOfficeEventMap)
                .flatMap(officeMap -> Http.netServer(SafeApi.class)
                        .createEventList(officeMap))
                .compose(Network.networkConvertDialog(mView))
                .onErrorReturn(throwable -> {
                    cache.setUploadStatus(0);
                    cache.save();
                    return 0;
                })
                .subscribe(integer -> mView.createSuccess(), throwable -> {
                });

    }

    /**
     * 是否要验证参数值
     *
     * @return true 存在一项有数据 则会验证 如下几个参数 false 则不会验证 可以传空
     */
    private boolean checkValue() {
        ProjectTeamEntity companyTeam = mView.getCompanyTeamEntity();
        boolean isLeader = Cache.isLeader();
        if (companyTeam != null && !isLeader) {
            return true;
        }
        List<EventTypeEntity> typeList = mCommonItemType.getCheckEventTypeList();
        if (!typeList.isEmpty()) {
            return true;
        }

        String rectify = mRectifyEditContent.getContent();
        if (!TextUtil.isEmpty(rectify)) {
            return true;
        }

        CommonItemType<CreateUserEntity> relatedUser = mGroupTypeArrayMap.get("整改人");
        if (!relatedUser.getTypeItemList().isEmpty()) {
            return true;
        }

        return false;
    }

    private String getAtListString(CommonItemType<CreateUserEntity> checkUser, CommonItemType<CreateUserEntity> reviewUser, CommonItemType<CreateUserEntity> relatedUser) {
        List<CreateUserEntity> entities = new ArrayList<>();
        entities.addAll(checkUser.getTypeItemList());
        entities.addAll(reviewUser.getTypeItemList());
        entities.addAll(relatedUser.getTypeItemList());
        return Flowable.fromIterable(entities)
                .filter(CreateUserEntity::isAt)
                .map(CreateUserEntity::getUserId)
                .distinct()
                .map(integer -> integer + ",")
                .toList()
                .map(strings -> {
                    StringBuilder builder = new StringBuilder();
                    for (String data : strings) {
                        builder.append(data);
                    }
                    if (builder.length() > 0) {
                        builder.delete(builder.length() - 1, builder.length());
                    }
                    return builder.toString();
                }).toFlowable()
                .blockingSingle();
    }

}
