package com.promlert.phonebook.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

@SuppressLint("StaticFieldLeak")
public class PhoneRepository {

    private static final String TAG = PhoneRepository.class.getName();

    private Context mContext;

    public PhoneRepository(Context context) {
        this.mContext = context;
    }

    public void getAllPhone(Callback callback) {
        new AsyncTask<Void, Void, List<PhoneItem>>() {
            @Override
            protected List<PhoneItem> doInBackground(Void... voids) {
                AppDatabase db = AppDatabase.getInstance(mContext);
                return db.phoneDao().getAll();
            }

            @Override
            protected void onPostExecute(List<PhoneItem> phoneItemList) {
                super.onPostExecute(phoneItemList);
                if (callback != null) {
                    callback.onGetPhone(phoneItemList);
                }
            }
        }.execute();
    }

    public interface Callback {
        void onGetPhone(List<PhoneItem> phoneItemList);
    }
}
