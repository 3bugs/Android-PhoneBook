package com.promlert.phonebook.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProvinceDao {

    @Query("SELECT * FROM province")
    List<Province> getAll();

    @Query("SELECT * FROM province WHERE zone_id = :zoneId")
    List<Province> getByZone(int zoneId);

    @Insert
    void addProvince(List<Province> provinceList);

}
