package com.promlert.phonebook.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "province")
public class Province implements Serializable {

    private static final String TAG = Province.class.getName();

    @PrimaryKey(autoGenerate = true)
    public final long id;
    @ColumnInfo(name = "name")
    public final String name;
    @ColumnInfo(name = "zone_id")
    public final int zoneId;

    public Province(long id, String name, int zoneId) {
        this.id = id;
        this.name = name;
        this.zoneId = zoneId;
    }
}
