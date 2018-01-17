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
import com.zhongtie.work.util.upload.UploadData;
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

        CacheSafeEventTable cache = new CacheSafeEventTable();

        ArrayMap<String, Object> postDataList = new ArrayMap<>();
        //当前登录用户
        postDataList.put("event_userid", Cache.getUserID());
        cache.setUserid(Integer.valueOf(Cache.getUserID()));
        //选择所属公司
        postDataList.put("event_company", unit.getProjectTeamID());
        cache.setCompany(unit.getProjectTeamID());

        //选择时间因为默认赋值所以不做验证
        postDataList.put("event_time", mView.getSelectDate());
        cache.setTime(mView.getSelectDate());

        //输入的地点
        postDataList.put("event_local", site);
        cache.setLocal(site);

        //劳务公司
        postDataList.put("event_workerteam", companyTeam.getProjectTeamID());
        cache.setWorkerteam(companyTeam.getProjectTeamID());

        String type = mCommonItemType.getCheckEventTypeString();
        //选择的问题类型
        postDataList.put("event_troubletype", type);
        cache.setTroubletype(type);

        //描述
        postDataList.put("event_detail", describe);
        cache.setDetail(describe);

        //整个要求
        postDataList.put("event_changemust", rectify);
        cache.setChangemust(rectify);


        CommonItemType<CreateUserEntity> checkUser = mGroupTypeArrayMap.get("检查人");
        String check = checkUser.getSelectUserIDList();
        postDataList.put("event_checker", check);
        cache.setChecker(check);

        CommonItemType<CreateUserEntity> reviewUser = mGroupTypeArrayMap.get("验证人");
//        postDataList.put("event_review", reviewUser.getSelectUserIDList());
        postDataList.put("event_review", "12,12");
        cache.setReview("");


        CommonItemType<CreateUserEntity> relatedUser = mGroupTypeArrayMap.get("整改人");
        String related = relatedUser.getSelectUserIDList();
        postDataList.put("event_related", relatedUser.getSelectUserIDList());
        cache.setRelated(related);


        //集合
        List<CreateUserEntity> entities = new ArrayList<>();
        entities.addAll(checkUser.getTypeItemList());
        entities.addAll(reviewUser.getTypeItemList());
        entities.addAll(relatedUser.getTypeItemList());
        String atUser = Flowable.fromIterable(entities)
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
                    if (builder.length() > 0)
                        builder.delete(builder.length() - 1, builder.length());
                    return builder.toString();
                }).toFlowable()
                .blockingSingle();
        postDataList.put("event_at", atUser);
        cache.setAt(atUser);

        CommonItemType<TeamNameEntity> temLis = mGroupTypeArrayMap.get("查阅组");
        String read = temLis.getTeamIDList();
        postDataList.put("event_read", temLis.getTeamIDList());
        cache.setRead(read);


        Flowable.fromIterable(picList)
                .flatMap(UploadUtil::uploadJPG)
                .toList()
                .toFlowable()
                .flatMap(uploadData -> {
                    StringBuilder builder = new StringBuilder();
                    for (UploadData data : uploadData) {
                        builder.append(data.getPicid());
                        builder.append(",");
                    }
                    if (builder.length() > 0)
                        builder.delete(builder.length() - 1, builder.length());
                    postDataList.put("event_pic", builder.toString());
                    cache.setPic(builder.toString());
                    return Http.netServer(SafeApi.class).createEventList(postDataList);
                })
                .compose(Network.networkConvertDialog(mView))
                .subscribe(integer -> mView.createSuccess(), throwable -> {
                });


    }

}
