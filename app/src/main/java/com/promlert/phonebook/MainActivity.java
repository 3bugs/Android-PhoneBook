package com.promlert.phonebook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.ferfalk.simplesearchview.SimpleSearchView;
import com.google.gson.Gson;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.promlert.phonebook.adapter.MyPagerAdapter;
import com.promlert.phonebook.db.PhoneRepository;
import com.promlert.phonebook.db.ProvinceRepository;
import com.promlert.phonebook.db.entity.PhoneItem;
import com.promlert.phonebook.db.entity.Province;
import com.promlert.phonebook.db.entity.ProvinceWithPhoneList;
import com.promlert.phonebook.etc.Utils;
import com.promlert.phonebook.fragment.ProvinceListFragment;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements
        ProvinceListFragment.ProvinceListFragmentListener {

    private static final String TAG = MainActivity.class.getName();
    public static final String KEY_PROVINCE = "province";

    private SimpleSearchView mSearchView;
    private ProgressBar mProgressBar;

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
        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.GONE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setupViewPagerAndTabs();

        mSearchView = findViewById(R.id.search_view);
    }

    private void setupViewPagerAndTabs() {
        String[] tabTitles = getResources().getStringArray(R.array.tab_data);

        MyPagerAdapter adapter = new MyPagerAdapter(
                getSupportFragmentManager(), mProvinceWithPhoneList, tabTitles
        );
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        SmartTabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(item);
        mSearchView.enableVoiceSearch(true);
        mSearchView.setHint("ค้นหา");
        mSearchView.setOnQueryTextListener(new SimpleSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                doSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextCleared() {
                return false;
            }
        });
        //mSearchView.setTabLayout(findViewById(R.id.tab_layout));
        return true;
    }

    private void doSearch(String query) {
        mProgressBar.setVisibility(View.VISIBLE);

        PhoneRepository phoneRepository = new PhoneRepository(MainActivity.this);
        phoneRepository.search(query, new PhoneRepository.Callback() {
            @Override
            public void onGetPhone(List<PhoneItem> phoneItemList) {
                ProvinceWithPhoneList provinceWithPhoneList = new ProvinceWithPhoneList(
                        new Province(0, "ผลการค้นหา '" + query.trim() + "'", 0),
                        phoneItemList
                );
                showPhoneList(provinceWithPhoneList);
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (mSearchView.onActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (mSearchView.onBackPressed()) {
            return;
        }

        super.onBackPressed();
    }

    @Override
    public void onItemClick(ProvinceWithPhoneList provinceWithPhone) {
        showPhoneList(provinceWithPhone);
    }

    private void showPhoneList(ProvinceWithPhoneList provinceWithPhone) {
        Utils.hideKeyboard(MainActivity.this);

        Intent intent = new Intent(MainActivity.this, PhoneListActivity.class);
        intent.putExtra(KEY_PROVINCE, new Gson().toJson(provinceWithPhone));
        startActivity(intent);
    }
}
