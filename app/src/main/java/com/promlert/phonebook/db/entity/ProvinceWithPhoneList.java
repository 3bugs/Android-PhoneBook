package com.promlert.phonebook.db.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ProvinceWithPhoneList {

    @Embedded
    public final Province province;
    @Relation(parentColumn = "id", entityColumn = "province_id")
    public final List<PhoneItem> phoneItemList;

    public ProvinceWithPhoneList(Province province, List<PhoneItem> phoneItemList) {
        this.province = province;
        this.phoneItemList = phoneItemList;
    }
}
