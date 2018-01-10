package com.zhongtie.work.ui.user;

import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.ui.base.BasePresenterFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Auth: Chaek
 * Date: 2018/1/9
 */

public class UserInfoFragment extends BasePresenterFragment<UserContract.Presenter> implements UserContract.View {

    private RecyclerView mList;
    private CommonAdapter commonAdapter;
    private List<Pair<String, String>> userInfoList;

    private View mFooterView;

    @Override
    protected UserContract.Presenter getPresenter() {
        return new UserPresenterImpl();
    }

    @Override
    public int getLayoutViewId() {
        return R.layout.base_recyclerview;
    }

    @Override
    public void initView() {
        mList = (RecyclerView) findViewById(R.id.list);
        mFooterView = LayoutInflater.from(getAppContext()).inflate(R.layout.layout_modify_pw_bottom, mList, false);
        commonAdapter = new CommonAdapter().register(UserInfoItemView.class);
        commonAdapter.addFooterView(mFooterView);
        mFooterView.findViewById(R.id.modify_password).setOnClickListener(view -> mPresenter.modfiyPassword());
    }

    @Override
    protected void initData() {
        userInfoList = new ArrayList<>();
        userInfoList.add(new Pair<>("姓名", "xxx"));
        userInfoList.add(new Pair<>("登录名", "xxx"));
        userInfoList.add(new Pair<>("密码", "xxx"));
        userInfoList.add(new Pair<>("性别", "男"));
        userInfoList.add(new Pair<>("身份证", "10324198702113446"));
        userInfoList.add(new Pair<>("职务", "xxx"));
        userInfoList.add(new Pair<>("工种", "xxx"));
        commonAdapter.setListData(userInfoList);
        mList.setAdapter(commonAdapter);
    }

    @Override
    public void modifyPasswordSuccess() {
        getActivity().finish();
    }

    @Override
    public void modifyPasswordFail() {

    }

    @Override
    public void setListUserInfoList(List<Pair<String, String>> listUserInfoList) {
        this.userInfoList = listUserInfoList;
    }
}
