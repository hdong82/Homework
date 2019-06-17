package com.leo.test.bookshelf.adapter;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.leo.test.bookshelf.view.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 2017. 3. 20..
 *
 * @author leo
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<BaseFragment> fragmentList = new ArrayList<>();
    private final List<String> titleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(BaseFragment fragment, String title) {
        fragmentList.add(fragment);
        titleList.add(title);
    }

    @Override
    public BaseFragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    public void onRefresh(int position) {
        getItem(position).onRefresh();
    }
}
