package com.promlert.phonebook;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.promlert.phonebook.db.PhoneItem;
import com.promlert.phonebook.db.PhoneRepository;
import com.promlert.phonebook.db.Province;
import com.promlert.phonebook.db.ProvinceRepository;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

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
    }
}
