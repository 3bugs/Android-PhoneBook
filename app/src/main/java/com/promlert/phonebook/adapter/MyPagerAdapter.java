package com.promlert.phonebook.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.promlert.phonebook.db.entity.ProvinceWithPhoneList;
import com.promlert.phonebook.fragment.ProvinceListFragment;

import java.util.ArrayList;
import java.util.List;

public class MyPagerAdapter extends FragmentPagerAdapter {

    private List<ProvinceWithPhoneList> mProvinceWithPhoneList;
    private String[] mTabTitles;

    public MyPagerAdapter(FragmentManager fm,
                          List<ProvinceWithPhoneList> provinceWithPhoneList,
                          String[] tabTitles) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mProvinceWithPhoneList = provinceWithPhoneList;
        mTabTitles = tabTitles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        List<ProvinceWithPhoneList> provinceList = new ArrayList<>();
        for (ProvinceWithPhoneList p : mProvinceWithPhoneList) {
            if (p.province.zoneId == position + 1) {
                provinceList.add(p);
            }
        }
        return ProvinceListFragment.newInstance(provinceList);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles[position];
    }

    @Override
    public int getCount() {
        return mTabTitles.length;
    }
}
