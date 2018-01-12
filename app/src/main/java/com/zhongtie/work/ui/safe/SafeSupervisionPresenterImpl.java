package com.zhongtie.work.ui.safe;

import com.zhongtie.work.model.SafeSupervisionEnity;
import com.zhongtie.work.ui.base.BasePresenterImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Auth:Cheek
 * date:2018.1.9
 */

public class SafeSupervisionPresenterImpl extends BasePresenterImpl<SafeSupervisionContract.View> implements SafeSupervisionContract.Presenter {
    @Override
    public void fetchPageList(String date, int type, int page) {
        List<SafeSupervisionEnity> safeSupervisionEnities = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            safeSupervisionEnities.add(new SafeSupervisionEnity());
        }
        mView.setSafeSupervisionList(safeSupervisionEnities);
    }
}
