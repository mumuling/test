package com.zhongtie.work.ui.endorse.detail;


import android.support.v4.util.ArrayMap;

import com.zhongtie.work.R;
import com.zhongtie.work.app.App;
import com.zhongtie.work.data.EndorseUserEntity;
import com.zhongtie.work.data.TeamNameEntity;
import com.zhongtie.work.data.create.CommonItemType;
import com.zhongtie.work.data.create.CreateUserEntity;
import com.zhongtie.work.ui.base.BasePresenterImpl;
import com.zhongtie.work.ui.file.select.NormalFile;

import java.util.ArrayList;
import java.util.List;

import static com.zhongtie.work.ui.safe.SafeSupervisionCreateFragment.imageUrls;

/**
 * Auth: Chaek
 * Date: 2018/1/12
 */

public class EndorseDetailPresenterImpl extends BasePresenterImpl<EndorseDetailContract.View> implements EndorseDetailContract.Presenter {


    private ArrayMap<String, CommonItemType> mTypeArrayMap;

    /**
     * @return 获取类型
     */
    private List<CommonItemType> fetchCommonItemTypeList() {
        String[] titleList = App.getInstance().getResources().getStringArray(R.array.create_file_sign_detail_title);
        String[] tip = App.getInstance().getResources().getStringArray(R.array.create_file_sign_detail_tip);
        List<CommonItemType> list = new ArrayList<>();
        int size = titleList.length;
        List<CreateUserEntity> createUserEntities = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            CreateUserEntity c = new CreateUserEntity();
            c.setUserId(i);
            c.setUserName("测试" + 1);
            c.setUserPic(imageUrls[i]);
            createUserEntities.add(c);

        }
        List<TeamNameEntity> list1 = new ArrayList<>();
        list1.add(new TeamNameEntity());
        list1.add(new TeamNameEntity());
        for (int i = 0; i < size; i++) {
            CommonItemType item = new CommonItemType<>(titleList[i], tip[i], R.drawable.plus, false);
            mTypeArrayMap.put(titleList[i], item);
            if (titleList[i].contains("查阅")) {
                item.setTypeItemList(list1);
            } else if (titleList[i].contains("签认文件")) {
                List<NormalFile> files = new ArrayList<>();
                NormalFile normalFile = new NormalFile();
                normalFile.setDate(System.currentTimeMillis());
                normalFile.setPath("/测试文件.xls");
                normalFile.setSize(1012 * 20);
                files.add(normalFile);
                item.setTypeItemList(files);
            } else if (titleList[i].contains("签认人")) {
                item.setTypeItemList(createUserEntities);
            } else {
                List<EndorseUserEntity> files = new ArrayList<>();
                for (int j = 0; j < 10; j++) {
                    files.add(new EndorseUserEntity());
                }
                item.setTypeItemList(files);
            }

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
    public void setSelectImageList(List<String> selectImgList) {

    }

    @Override
    public void setSelectUserInfoList(String title, List createUserEntities) {

    }
}
