package com.zhongtie.work.ui.endorse;

import com.zhongtie.work.data.EndorseEntity;
import com.zhongtie.work.ui.base.BasePresenterImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Auth:Cheek
 * date:2018.1.13
 */

public class EndorsePresenterImpl extends BasePresenterImpl<EndorseListContract.View> implements EndorseListContract.Presenter {


    @Override
    public void fetchPageList(String date, int type, int page) {
        List<EndorseEntity> endorseEntities = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            endorseEntities.add(new EndorseEntity());
        }
        mView.setSafeSupervisionList(endorseEntities);
    }
}
