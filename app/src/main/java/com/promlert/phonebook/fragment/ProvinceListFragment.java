package com.promlert.phonebook.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.promlert.phonebook.R;
import com.promlert.phonebook.db.entity.ProvinceWithPhoneList;

import java.util.ArrayList;
import java.util.List;

public class ProvinceListFragment extends Fragment {

    private static final String TAG = ProvinceListFragment.class.getName();
    private static final String ARG_PROVINCE_JSON = "province_json";
    private static final int COLUMN_COUNT = 1;

    private ProvinceListFragmentListener mListener;
    private List<ProvinceWithPhoneList> mProvince;

    public ProvinceListFragment() {
    }

    public static ProvinceListFragment newInstance(List<ProvinceWithPhoneList> provinceWithPhoneList) {
        ProvinceListFragment fragment = new ProvinceListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PROVINCE_JSON, new Gson().toJson(provinceWithPhoneList));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            String provinceJson = getArguments().getString(ARG_PROVINCE_JSON);
            mProvince = new Gson().fromJson(
                    provinceJson,
                    new TypeToken<ArrayList<ProvinceWithPhoneList>>() {
                    }.getType());
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_province_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (COLUMN_COUNT <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, COLUMN_COUNT));
            }
            recyclerView.setAdapter(new ProvinceRecyclerViewAdapter(mProvince, mListener));
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ProvinceListFragmentListener) {
            mListener = (ProvinceListFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ProvinceListFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface ProvinceListFragmentListener {
        void onItemClick(ProvinceWithPhoneList provinceWithPhoneList);
    }
}
