package com.promlert.phonebook;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.promlert.phonebook.db.entity.PhoneItem;
import com.promlert.phonebook.db.entity.ProvinceWithPhoneList;

import java.util.List;

import static com.promlert.phonebook.MainActivity.KEY_PROVINCE;

public class PhoneListActivity extends AppCompatActivity {

    private static final String TAG = PhoneListActivity.class.getName();
    private static final int PERMISSIONS_REQUEST_CALL_PHONE = 1;

    private ProvinceWithPhoneList mProvinceWithPhone;
    private PhoneItem mClickedPhoneItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_list);

        Intent intent = getIntent();
        String provinceJson = intent.getStringExtra(KEY_PROVINCE);
        mProvinceWithPhone = new Gson().fromJson(provinceJson, ProvinceWithPhoneList.class);

        setupViews();
    }

    private void setupViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(mProvinceWithPhone.province.name);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(PhoneListActivity.this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                PhoneListActivity.this,
                layoutManager.getOrientation()
        );
        setupRecyclerView(layoutManager, dividerItemDecoration);
    }

    private void setupRecyclerView(LinearLayoutManager layoutManager, DividerItemDecoration dividerItemDecoration) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        TextView emptyView = findViewById(R.id.empty_view);

        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);

        if (mProvinceWithPhone.phoneItemList.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(dividerItemDecoration);
            recyclerView.setAdapter(new PhoneListAdapter(
                    PhoneListActivity.this,
                    mProvinceWithPhone.phoneItemList,
                    new PhoneListActivityListener() {
                        @Override
                        public void onItemClick(PhoneItem phoneItem) {
                            Snackbar.make(
                                    findViewById(R.id.content),
                                    "หมายเลข " + phoneItem.number,
                                    Snackbar.LENGTH_INDEFINITE
                            )
                                    .setAction("ยืนยันโทรออก", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            mClickedPhoneItem = phoneItem;
                                            checkPhoneCallPermission();
                                        }
                                    })
                                    .show();
                        }
                    }
            ));
        } else {
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    private void checkPhoneCallPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        PhoneListActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        PERMISSIONS_REQUEST_CALL_PHONE
                );
            } else {
                makePhoneCall();
            }
        } else {
            makePhoneCall();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CALL_PHONE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makePhoneCall();
                }
                break;
        }
    }

    @SuppressLint("MissingPermission")
    private void makePhoneCall() {
        Toast.makeText(
                PhoneListActivity.this,
                "กำลังโทรออกไปยังหมายเลข " + mClickedPhoneItem.number,
                Toast.LENGTH_LONG
        ).show();

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + mClickedPhoneItem.number));
        startActivity(intent);
    }

    private static class PhoneListAdapter extends RecyclerView.Adapter<PhoneListAdapter.ViewHolder> {

        private Context mContext;
        private List<PhoneItem> mPhoneItem;
        private PhoneListActivityListener mListener;

        PhoneListAdapter(Context context, List<PhoneItem> phoneItemList, PhoneListActivityListener listener) {
            this.mContext = context;
            this.mPhoneItem = phoneItemList;
            this.mListener = listener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_phone, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            PhoneItem item = mPhoneItem.get(position);
            holder.mPhoneItem = item;
            holder.mPhoneTitleTextView.setText(item.title);
            holder.mPhoneNumberTextView.setText(item.number);
        }

        @Override
        public int getItemCount() {
            return mPhoneItem.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final View mRootView;
            final TextView mPhoneTitleTextView;
            final TextView mPhoneNumberTextView;

            PhoneItem mPhoneItem;

            ViewHolder(View view) {
                super(view);
                mRootView = view;
                mPhoneTitleTextView = view.findViewById(R.id.phone_title_text_view);
                mPhoneNumberTextView = view.findViewById(R.id.phone_number_text_view);
                mRootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onItemClick(mPhoneItem);
                    }
                });
            }
        }
    }

    interface PhoneListActivityListener {
        void onItemClick(PhoneItem phoneItem);
    }
}
