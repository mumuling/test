package com.zhongtie.work.ui.rewardpunish.presenter;


import android.support.v4.util.ArrayMap;

import com.github.gcacace.signaturepad.utils.SvgBuilder;
import com.zhongtie.work.R;
import com.zhongtie.work.app.App;
import com.zhongtie.work.app.Cache;
import com.zhongtie.work.data.RPRecordEntity;
import com.zhongtie.work.data.TeamNameEntity;
import com.zhongtie.work.data.create.CommonItemType;
import com.zhongtie.work.data.create.EditContentEntity;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.RewardPunishApi;
import com.zhongtie.work.ui.base.BasePresenterImpl;
import com.zhongtie.work.util.upload.UploadUtil;

import java.util.ArrayList;
import java.util.List;

import static com.zhongtie.work.ui.safe.SafeSupervisionCreateFragment.imageUrls;

/**
 * Auth: Chaek
 * Date: 2018/1/12
 */

public class RPDetailPresenterImpl extends BasePresenterImpl<RPDetailContract.View> implements RPDetailContract.Presenter {

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
    private int mPunishId;

    /**
     * @return 获取类型
     */
    private List<CommonItemType> fetchCommonItemTypeList() {
        String[] titleList = App.getInstance().getResources().getStringArray(R.array.create_reward_punish_item_title);
        String[] tip = App.getInstance().getResources().getStringArray(R.array.create_reward_punish_item_tip);
        List<CommonItemType> list = new ArrayList<>();
        List<RPRecordEntity> createUserEntities = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            RPRecordEntity c = new RPRecordEntity();
            c.setUserID(i);
            c.setEdit(false);
            c.setReplyContent("项目安全措施也技术指标没有达标请完善全部指标" +
                    "项目安全措施也技术指标没有达标请完善全部指标");
            c.setUserName("测试" + 1);
            c.setUserPic(imageUrls[i]);
            createUserEntities.add(c);
        }

        List<TeamNameEntity> list1 = new ArrayList<>();
        list1.add(new TeamNameEntity());
        list1.add(new TeamNameEntity());
        int size = titleList.length;
        for (int i = 0; i < size; i++) {
            CommonItemType item = new CommonItemType<>(titleList[i], tip[i], R.drawable.plus, false);
            if (i < size - 1) {
                item.setTypeItemList(createUserEntities);
            } else {
                item.setTypeItemList(list1);
            }
            mTypeArrayMap.put(titleList[i], item);
            list.add(item);
        }
        return list;
    }


    @Override
    public void getDetailInfo(int punishId) {
        this.mPunishId = punishId;
        List<Object> itemList = new ArrayList<>();
        mTypeArrayMap = new ArrayMap<>();

        //添加修改要求
        mRectifyEditContent = new EditContentEntity("摘要", "请输入整改要求", "由于十号线供电线路项目分部违反了分公司《中铁电" +
                "气化局集团城铁公司西南分公司安全生产奖罚办法》，" +
                "按照规定罚款5000元。");
        itemList.add(mRectifyEditContent);
        mDescribeEditContent = new EditContentEntity("详细情况", "请输入整改要求", "在2017年7月18日检查过程中发现，十号线供电线路" +
                "项目分部作业人员陈伟小组在民心佳园站-重庆北站北" +
                "广场站（区间左线）：曲线段使用梯车未采取防倾倒" +
                "措施，梯车上半部传递材料作业人员未系安全带。" +
                "17日已发现该问题：曲线作业无防倾倒措施，扶梯人" +
                "员违规站在曲线内侧，现场已勒令整改）予罚金" +
                "5000元整。");
        itemList.add(mDescribeEditContent);

        itemList.addAll(fetchCommonItemTypeList());
        mView.setItemList(itemList);
    }

    /**
     * 同意
     *
     * @param signPath 签名地址
     */
    @Override
    public void consentPunish(String signPath) {
        addDispose(UploadUtil.uploadSignPNG(signPath)
                .flatMap(img -> Http.netServer(RewardPunishApi.class).consentPunish(Cache.getUserID(), mPunishId, img.getPicname()))
                .compose(Network.convertDialogTip(mView))
                .subscribe(integer -> {
                    getDetailInfo(mPunishId);
                    mView.consentPunishSuccess();
                }, throwable -> {
                }));
    }

    /**
     * 退回
     *
     * @param signPath 签名路径
     * @param content  理由
     */
    @Override
    public void sendBackPunish(String signPath, String content) {
        addDispose(UploadUtil.uploadSignPNG(signPath)
                .flatMap(img -> Http.netServer(RewardPunishApi.class).sendBackPunish(Cache.getUserID(), mPunishId, img.getPicname(), content))
                .compose(Network.convertDialogTip(mView))
                .subscribe(integer -> {
                    getDetailInfo(mPunishId);
                }, throwable -> {
                }));
    }


    /**
     * 作废
     *
     * @param signPath 签名地址
     */
    @Override
    public void cancellationPunish(String signPath) {
        addDispose(UploadUtil.uploadSignPNG(signPath)
                .flatMap(img -> Http.netServer(RewardPunishApi.class).cancelPunish(Cache.getUserID(), mPunishId, img.getPicname()))
                .compose(Network.convertDialogTip(mView))
                .subscribe(integer -> {
                    getDetailInfo(mPunishId);
                }, throwable -> {
                }));
    }

    /**
     * 同意处罚
     *
     * @param signPath 签名路径
     */
    @Override
    public void signPunish(String signPath) {
        addDispose(UploadUtil.uploadSignPNG(signPath)
                .flatMap(img -> Http.netServer(RewardPunishApi.class).signPunish(Cache.getUserID(), mPunishId, img.getPicname()))
                .compose(Network.convertDialogTip(mView))
                .subscribe(integer -> {
                    getDetailInfo(mPunishId);
                }, throwable -> {
                }));

    }


}
