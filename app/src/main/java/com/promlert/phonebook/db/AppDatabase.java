package com.promlert.phonebook.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.promlert.phonebook.R;
import com.promlert.phonebook.db.entity.PhoneItem;
import com.promlert.phonebook.db.entity.Province;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database(entities = {PhoneItem.class, Province.class}, exportSchema = false, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "phonebook.db";

    public abstract PhoneDao phoneDao();
    public abstract ProvinceDao provinceDao();

    private static AppDatabase mInstance;

    public static synchronized AppDatabase getInstance(final Context context) {
        if (mInstance == null) {
            mInstance = Room
                    .databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            DB_NAME
                    )
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            insertInitialData(context);
                        }
                    })
                    .build();
        }
        return mInstance;
    }

    private static void insertInitialData(final Context context) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                /*เพิ่มข้อมูลจังหวัด*/
                String[] provinceData = context.getResources().getStringArray(R.array.province_data);
                List<Province> provinceList = new ArrayList<>();
                for (String data : provinceData) {
                    String[] dataPart = data.split("@");
                    provinceList.add(new Province(
                            0,
                            dataPart[0].trim(), //ชื่อจังหวัด
                            Integer.parseInt(dataPart[1].trim())) //ID ภาค (แท็บ)
                    );
                }
                getInstance(context).provinceDao().addProvince(provinceList);

                /*เพิ่มข้อมูลเบอร์โทรของสถานที่*/
                String[] phoneData = context.getResources().getStringArray(R.array.phone_data);
                List<PhoneItem> phoneItemList = new ArrayList<>();
                for (String data : phoneData) {
                    String[] dataPart = data.split("@");
                    phoneItemList.add(new PhoneItem(
                            0,
                            dataPart[0].trim(), //ชื่อสถานที่
                            dataPart[1].trim(), //เบอร์โทร
                            Integer.parseInt(dataPart[2].trim())) //ID จังหวัด
                    );
                }
                getInstance(context).phoneDao().addPhone(phoneItemList);
            }
        });
    }
}
