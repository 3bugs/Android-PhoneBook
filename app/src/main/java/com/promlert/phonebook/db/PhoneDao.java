package com.promlert.phonebook.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.promlert.phonebook.db.entity.PhoneItem;

import java.util.List;

@Dao
public interface PhoneDao {

    @Query("SELECT * FROM phone")
    List<PhoneItem> getAll();

    @Query("SELECT * FROM phone WHERE province_id = :provinceId")
    List<PhoneItem> getByProvince(int provinceId);

    @Query("SELECT * FROM phone WHERE title LIKE '%' || :query || '%'")
    List<PhoneItem> search(String query);

    @Insert
    void addPhone(List<PhoneItem> phoneItemList);

}
