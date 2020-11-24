package com.example.medifacts.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medifacts.CategoryAdapter;
import com.example.medifacts.CategoryModel;
import com.example.medifacts.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public HomeFragment(){

    }

    private HomeViewModel homeViewModel;
    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        categoryRecyclerView = view.findViewById(R.id.category_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);

        List<CategoryModel> categoryModelList = new ArrayList<CategoryModel>();
        categoryModelList.add(new CategoryModel("link","Home"));
        categoryModelList.add(new CategoryModel("link","Devices"));
        categoryModelList.add(new CategoryModel("link","Covid Essentials"));
        categoryModelList.add(new CategoryModel("link","Fitness"));
        categoryModelList.add(new CategoryModel("link","Ayush"));
        categoryModelList.add(new CategoryModel("link","Mom's Care"));
        categoryModelList.add(new CategoryModel("link","Upload Prescription"));
        categoryModelList.add(new CategoryModel("link","Consult Doctor"));
        categoryModelList.add(new CategoryModel("link","Lab Tests"));

        categoryAdapter = new CategoryAdapter(categoryModelList);

        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
        return view;
    }
}