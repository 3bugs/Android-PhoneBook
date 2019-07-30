package com.promlert.phonebook.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.promlert.phonebook.db.entity.ProvinceWithPhoneList;
import com.promlert.phonebook.fragment.ProvinceListFragment;

import java.util.ArrayList;
import java.util.List;

public class MyPagerAdapter extends FragmentPagerAdapter {

    private static final String[] mTabTitles = {
            "aaa", "bbb", "ccc", "ddd", "eee"
    };

    private List<ProvinceWithPhoneList> mProvinceWithPhoneList;

    public MyPagerAdapter(FragmentManager fm, List<ProvinceWithPhoneList> provinceWithPhoneList) {
        super(fm);
        mProvinceWithPhoneList = provinceWithPhoneList;
    }

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
