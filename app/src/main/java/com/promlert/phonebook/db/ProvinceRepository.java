package com.promlert.phonebook.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.promlert.phonebook.db.entity.Province;
import com.promlert.phonebook.db.entity.ProvinceWithPhoneList;

import java.util.List;

@SuppressLint("StaticFieldLeak")
public class ProvinceRepository {

    private static final String TAG = ProvinceRepository.class.getName();

    private Context mContext;

    public ProvinceRepository(Context context) {
        this.mContext = context;
    }

    public void getAllProvince(Callback callback) {
        new AsyncTask<Void, Void, List<Province>>() {
            @Override
            protected List<Province> doInBackground(Void... voids) {
                AppDatabase db = AppDatabase.getInstance(mContext);
                return db.provinceDao().getAll();
            }

            @Override
            protected void onPostExecute(List<Province> provinceList) {
                super.onPostExecute(provinceList);
                if (callback != null) {
                    callback.onGetProvince(provinceList);
                }
            }
        }.execute();
    }

    public void getAllProvinceWithPhoneList(Callback callback) {
        new AsyncTask<Void, Void, List<ProvinceWithPhoneList>>() {
            @Override
            protected List<ProvinceWithPhoneList> doInBackground(Void... voids) {
                AppDatabase db = AppDatabase.getInstance(mContext);
                return db.provinceDao().getWithPhoneList();
            }

            @Override
            protected void onPostExecute(List<ProvinceWithPhoneList> provinceWithPhoneList) {
                super.onPostExecute(provinceWithPhoneList);
                if (callback != null) {
                    callback.onGetProvinceWithPhoneList(provinceWithPhoneList);
                }
            }
        }.execute();
    }

    public interface Callback {
        void onGetProvince(List<Province> provinceList);
        void onGetProvinceWithPhoneList(List<ProvinceWithPhoneList> provinceWithPhoneList);
    }
}
