package com.example.moham.takafol.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> MyFragments = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return MyFragments.get(position);
    }

    @Override
    public int getCount() {
        return MyFragments.size();
    }

    public void AddFragment(Fragment fragment) {
        MyFragments.add(fragment);
    }
}
