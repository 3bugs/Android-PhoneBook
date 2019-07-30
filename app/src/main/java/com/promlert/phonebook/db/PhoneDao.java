package com.promlert.phonebook.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PhoneDao {

    @Query("SELECT * FROM phone")
    List<PhoneItem> getAll();

    @Query("SELECT * FROM phone WHERE province_id = :provinceId")
    List<PhoneItem> getByProvince(int provinceId);

    @Insert
    void addPhone(List<PhoneItem> phoneItemList);

}
