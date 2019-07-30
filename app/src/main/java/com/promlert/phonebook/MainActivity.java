package com.promlert.phonebook;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.promlert.phonebook.adapter.MyPagerAdapter;
import com.promlert.phonebook.db.PhoneRepository;
import com.promlert.phonebook.db.ProvinceRepository;
import com.promlert.phonebook.db.entity.PhoneItem;
import com.promlert.phonebook.db.entity.Province;
import com.promlert.phonebook.db.entity.ProvinceWithPhoneList;
import com.promlert.phonebook.fragment.ProvinceListFragment;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements
        ProvinceListFragment.ProvinceListFragmentListener {

    private static final String TAG = MainActivity.class.getName();

    private ViewPager mViewPager;

    private List<ProvinceWithPhoneList> mProvinceWithPhoneList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProvinceRepository provinceRepository = new ProvinceRepository(MainActivity.this);
        provinceRepository.getAllProvince(new ProvinceRepository.Callback() {
            @Override
            public void onGetProvince(List<Province> provinceList) {
                Log.i(TAG, "---------------------------------------------------");
                Log.i(TAG, "Phone list count: " + provinceList.size());
                Log.i(TAG, "---------------------------------------------------");

                for (Province province : provinceList) {
                    String msg = String.format(
                            Locale.getDefault(),
                            "%d: %s, Zone ID: %d",
                            province.id, province.name, province.zoneId
                    );
                    Log.i(TAG, msg);
                }
            }

            @Override
            public void onGetProvinceWithPhoneList(List<ProvinceWithPhoneList> provinceWithPhoneList) {
            }
        });

        PhoneRepository phoneRepository = new PhoneRepository(MainActivity.this);
        phoneRepository.getAllPhone(new PhoneRepository.Callback() {
            @Override
            public void onGetPhone(List<PhoneItem> phoneItemList) {
                Log.i(TAG, "---------------------------------------------------");
                Log.i(TAG, "Phone list count: " + phoneItemList.size());
                Log.i(TAG, "---------------------------------------------------");

                for (PhoneItem phoneItem : phoneItemList) {
                    String msg = String.format(
                            Locale.getDefault(),
                            "%d: %s, %s, Province ID: %d",
                            phoneItem.id, phoneItem.title, phoneItem.number, phoneItem.provinceId
                    );
                    Log.i(TAG, msg);
                }
            }
        });

        provinceRepository.getAllProvinceWithPhoneList(new ProvinceRepository.Callback() {
            @Override
            public void onGetProvince(List<Province> provinceList) {
            }

            @Override
            public void onGetProvinceWithPhoneList(List<ProvinceWithPhoneList> provinceWithPhoneList) {
                mProvinceWithPhoneList = provinceWithPhoneList;
                setupViews();

                Log.i(TAG, "---------------------------------------------------");
                Log.i(TAG, "Province with phone list count: " + provinceWithPhoneList.size());
                Log.i(TAG, "---------------------------------------------------");

                for (ProvinceWithPhoneList p : provinceWithPhoneList) {
                    String msg = String.format(
                            Locale.getDefault(),
                            "Province %s, Phone item count: %d",
                            p.province.name, p.phoneItemList.size()
                    );
                    Log.i(TAG, msg);
                }
            }
        });
    }

    private void setupViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setupViewPagerAndTabs();
    }

    private void setupViewPagerAndTabs() {
        MyPagerAdapter adapter = new MyPagerAdapter(
                getSupportFragmentManager(), mProvinceWithPhoneList
        );
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setAdapter(adapter);

        SmartTabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            //todo:
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(ProvinceWithPhoneList provinceWithPhoneList) {

    }
}
