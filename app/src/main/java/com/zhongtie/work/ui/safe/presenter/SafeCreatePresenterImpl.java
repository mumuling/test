package com.zhongtie.work.ui.safe.presenter;


import android.support.v4.util.ArrayMap;

import com.zhongtie.work.R;
import com.zhongtie.work.app.App;
import com.zhongtie.work.app.Cache;
import com.zhongtie.work.data.CommonUserEntity;
import com.zhongtie.work.data.ProjectTeamEntity;
import com.zhongtie.work.data.SafeEventEntity;
import com.zhongtie.work.data.TeamNameEntity;
import com.zhongtie.work.data.create.CommonItemType;
import com.zhongtie.work.data.create.EditContentEntity;
import com.zhongtie.work.data.create.EventTypeEntity;
import com.zhongtie.work.data.create.SelectEventTypeItem;
import com.zhongtie.work.db.CacheSafeEventTable;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.NetWorkFunc1;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.SafeApi;
import com.zhongtie.work.ui.base.BasePresenterImpl;
import com.zhongtie.work.ui.safe.detail.SafeEventModel;
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

    private List<Object> itemList;

    /**
     * 获取输入安全督导的基本类型
     *
     * @return 获取类型list
     */
    private List<CommonItemType> fetchCommonItemTypeList() {
        mGroupTypeArrayMap = new ArrayMap<>();

        String[] titleList = App.getInstance().getResources().getStringArray(R.array.create_item_title);
        String[] tip = App.getInstance().getResources().getStringArray(R.array.create_item_tip);

        List<CommonItemType> list = new ArrayList<>();
        for (int i = 0, size = titleList.length; i < size; i++) {
            String title = titleList[i];
            CommonItemType itemType = new CommonItemType<>(title, tip[i], R.drawable.plus, true);
            mGroupTypeArrayMap.put(title, itemType);
            list.add(itemType);
        }
        return list;
    }

    private SelectEventTypeItem fetchSelectTypeItemData(List<String> selectTypeList) {
        mCommonItemType = new SelectEventTypeItem("问题类型");
        String[] typeList = App.getInstance().getResources().getStringArray(R.array.type_list);
        List<EventTypeEntity> list = new ArrayList<>();
        int size = typeList.length;
        for (int i = 0; i < size; i++) {
            String typeTitle = typeList[i];
            list.add(new EventTypeEntity(typeTitle, i, selectTypeList.contains(typeTitle)));
        }
        mCommonItemType.setTypeItemList(list);
        return mCommonItemType;
    }

    @Override
    public void getItemList(int safeOrderID) {
        if (safeOrderID > 0) {
            fetchEventInfoData(safeOrderID);
        } else {
            itemList = new ArrayList<>();
            mCommonItemType = fetchSelectTypeItemData(new ArrayList<>());
            itemList.add(mCommonItemType);
            addEventCommonTitleList();

            itemList.addAll(fetchCommonItemTypeList());
            mView.setItemList(itemList);
        }
    }

    private void addEventCommonTitleList() {
        mDescribeEditContent = new EditContentEntity("描述", "请输入事件描述", "");
        //添加描述
        itemList.add(mDescribeEditContent);
        //添加修改要求
        mRectifyEditContent = new EditContentEntity("整改要求", "请输入整改要求", "");
        itemList.add(mRectifyEditContent);
        //添加图片
        mPicItemType = new CommonItemType<>("传图片", "最多12张", R.drawable.ic_cam, true);
        itemList.add(mPicItemType);
    }

    private void fetchEventInfoData(int safeOrderID) {
        mView.initLoading();
        addDispose(Http.netServer(SafeApi.class)
                .eventDetails(Cache.getUserID(), safeOrderID)
                .map(new NetWorkFunc1<>())
                .compose(Network.networkIO())
                .subscribe(safeEventEntity -> {
                    setTitleUserInfo(safeEventEntity);
                    initItemList(safeEventEntity);
                    mView.initSuccess();
                }, throwable -> {
                    mView.initFail();
                }));
    }

    public void setTitleUserInfo(SafeEventEntity titleUserInfo) {
        mView.setModifyInfo(titleUserInfo);
    }

    /**
     * 初始化编辑页面
     *
     * @param eventInfo 安全督导基本信息
     */
    private void initItemList(SafeEventEntity eventInfo) {
        itemList = new ArrayList<>();
        mCommonItemType = fetchSelectTypeItemData(TextUtil.getPicList(eventInfo.getEvent_troubletype()));
        itemList.add(mCommonItemType);
        addEventCommonTitleList();

        mDescribeEditContent.setContent(eventInfo.getEvent_detail());
        mRectifyEditContent.setContent(eventInfo.getEvent_changemust());
        mPicItemType.setTypeItemList(TextUtil.getPicList(eventInfo.getEvent_pic()));

        mGroupTypeArrayMap = new ArrayMap<>();

        SafeEventModel safeEventModel = new SafeEventModel(eventInfo);
        CommonItemType checkUser = safeEventModel.getModifyCheckList();
        mGroupTypeArrayMap.put(checkUser.getTitle(), checkUser);
        CommonItemType reviewUser = safeEventModel.getModifyReviewList();
        mGroupTypeArrayMap.put(reviewUser.getTitle(), reviewUser);
        CommonItemType relate = safeEventModel.getModifyRelatedList();
        mGroupTypeArrayMap.put(relate.getTitle(), relate);
        CommonItemType temLis = safeEventModel.fetchReadList();
        mGroupTypeArrayMap.put(temLis.getTitle(), temLis);
        itemList.add(checkUser);
        itemList.add(reviewUser);
        itemList.add(relate);
        itemList.add(temLis);

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
        //领导不验证不验证 劳务公司 还有必须显示劳务公司才验证
        if (mView.isShowWorkTeam() && companyTeam == null && !isLeader && isCheckValue) {
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

        CommonItemType<CommonUserEntity> relatedUser = mGroupTypeArrayMap.get("整改人");
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

        cache.setUnit(unit.getProjectTeamName());
        cache.setRelated(related);
        cache.setImageList(picList);
        cache.setUserid(Integer.valueOf(Cache.getUserID()));
        cache.setCompany(unit.getProjectTeamID());
        cache.setTime(mView.getSelectDate());
        cache.setLocal(site);
        if (companyTeam == null) {
            cache.setWorkerteam("");
        } else {
            cache.setWorkerteam(companyTeam.getProjectTeamName());
        }
        String type = mCommonItemType.getCheckEventTypeString();
        cache.setTroubletype(type);
        cache.setDetail(describe);
        cache.setChangemust(rectify);


        CommonItemType<CommonUserEntity> checkUser = mGroupTypeArrayMap.get("检查人");
        if (checkUser.getTypeItemList().isEmpty()) {
            mView.showToast("请选择检查人");
            return;
        }
        String check = checkUser.getSelectUserIDList();
        cache.setChecker(check);

        CommonItemType<CommonUserEntity> reviewUser = mGroupTypeArrayMap.get("验证人");

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

        addDispose(UploadUtil.uploadListJPGIDList(cache.getImageList())
                .map(s -> {
                    cache.setPic(s);
                    return cache;
                })
                .map(CacheSafeEventTable::getOfficeEventMap)
                .flatMap(officeMap -> Http.netServer(SafeApi.class).createEventList(officeMap))
                .compose(Network.convertDialog(mView, R.string.create_safe_event_title))
                .onErrorReturn(throwable -> {
                    cache.setUploadStatus(0);
                    cache.save();
                    return 0;
                })
                .subscribe(integer -> mView.createSuccess(), throwable -> {
                }));

    }

    /**
     * 是否要验证参数值
     *
     * @return true 存在一项有数据 则会验证下面所有的参数是否为空 如下几个参数 false 则不会验证 可以传空
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

        CommonItemType<CommonUserEntity> relatedUser = mGroupTypeArrayMap.get("整改人");
        if (!relatedUser.getTypeItemList().isEmpty()) {
            return true;
        }
        return false;
    }

    private String getAtListString(CommonItemType<CommonUserEntity> checkUser, CommonItemType<CommonUserEntity> reviewUser, CommonItemType<CommonUserEntity> relatedUser) {
        List<CommonUserEntity> entities = new ArrayList<>();
        entities.addAll(checkUser.getTypeItemList());
        entities.addAll(reviewUser.getTypeItemList());
        entities.addAll(relatedUser.getTypeItemList());
        return Flowable.fromIterable(entities)
                .filter(CommonUserEntity::isAt)
                .map(CommonUserEntity::getUserId)
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
