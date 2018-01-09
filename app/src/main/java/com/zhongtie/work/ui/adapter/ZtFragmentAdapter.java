package com.zhongtie.work.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.zhongtie.work.ui.base.BaseFragment;

import java.util.List;



public class ZtFragmentAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> fragmentList;

    public ZtFragmentAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
        super(fm);
        if (fragmentList == null || fragmentList.size() <= 0) {
            throw new NullPointerException("fragment list not null");
        }
        this.fragmentList = fragmentList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
