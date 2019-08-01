package com.promlert.phonebook.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.promlert.phonebook.R;
import com.promlert.phonebook.db.entity.ProvinceWithPhoneList;

import java.util.List;

public class ProvinceRecyclerViewAdapter extends RecyclerView.Adapter<ProvinceRecyclerViewAdapter.ViewHolder> {

    private final List<ProvinceWithPhoneList> mDataList;
    private final ProvinceListFragment.ProvinceListFragmentListener mListener;

    ProvinceRecyclerViewAdapter(List<ProvinceWithPhoneList> items,
                                       ProvinceListFragment.ProvinceListFragmentListener listener) {
        mDataList = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_province, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        ProvinceWithPhoneList item = mDataList.get(position);
        holder.mProvince = item;
        holder.mProvinceNameTextView.setText(item.province.name);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final View mRootView;
        final TextView mProvinceNameTextView;

        ProvinceWithPhoneList mProvince;

        ViewHolder(View view) {
            super(view);
            mRootView = view;
            mProvinceNameTextView = view.findViewById(R.id.province_name_text_view);
            mRootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(mProvince);
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mProvinceNameTextView.getText() + "'";
        }
    }
}
