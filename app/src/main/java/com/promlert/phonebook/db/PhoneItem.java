package com.promlert.phonebook.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "phone")
public class PhoneItem implements Serializable {

    private static final String TAG = PhoneItem.class.getName();

    @PrimaryKey(autoGenerate = true)
    public final long id;
    @ColumnInfo(name = "title")
    public final String title;
    @ColumnInfo(name = "number")
    public final String number;
    @ColumnInfo(name = "province_id")
    public final int provinceId;

    public PhoneItem(long id, String title, String number, int provinceId) {
        this.id = id;
        this.title = title;
        this.number = number;
        this.provinceId = provinceId;
    }

    /*public Drawable getImageDrawable(Context context) {
        if (!"".equals(image)) {
            InputStream stream = null;
            AssetManager am = context.getAssets();
            try {
                stream = am.open(image);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "Error opening asset file: " + image);
            }
            return Drawable.createFromStream(stream, null);
        } else {
            return null;
        }
    }*/
}
