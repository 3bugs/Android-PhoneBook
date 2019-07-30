package com.promlert.phonebook.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.promlert.phonebook.db.entity.Province;
import com.promlert.phonebook.db.entity.ProvinceWithPhoneList;

import java.util.List;

@Dao
public interface ProvinceDao {

    @Query("SELECT * FROM province")
    List<Province> getAll();

    @Query("SELECT * FROM province WHERE zone_id = :zoneId")
    List<Province> getByZone(int zoneId);

    //https://developer.android.com/reference/android/arch/persistence/room/Transaction.html
    @Transaction
    @Query("SELECT * FROM province")
    List<ProvinceWithPhoneList> getWithPhoneList();

    @Insert
    void addProvince(List<Province> provinceList);
}
