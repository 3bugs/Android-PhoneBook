package com.promlert.phonebook.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

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

    public interface Callback {
        void onGetProvince(List<Province> provinceList);
    }
}
