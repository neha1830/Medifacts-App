package com.example.medifacts.ui.home;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.medifacts.CategoryAdapter;
import com.example.medifacts.CategoryModel;
import com.example.medifacts.GridProductLayoutAdapter;
import com.example.medifacts.HorizontalProductScrollAdapter;
import com.example.medifacts.HorizontalProductScrollModel;
import com.example.medifacts.R;
import com.example.medifacts.SliderAdapter;
import com.example.medifacts.SliderModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    public HomeFragment(){

    }


    private HomeViewModel homeViewModel;
    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private RecyclerView testing;



    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        categoryRecyclerView = view.findViewById(R.id.category_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);

        final List<CategoryModel> categoryModelList = new ArrayList<CategoryModel>();
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

        ////Banner Slider
        List<SliderModel> sliderModelList = new ArrayList<SliderModel>();

        sliderModelList.add(new SliderModel(R.mipmap.red_email,"#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.custom_error,"#077AE4"));

        sliderModelList.add(new SliderModel(R.mipmap.cart_black,"#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.profile_placeholder,"#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.home_icon,"#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.green_email,"#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.red_email,"#077AE4"));

        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.custom_error,"#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.cart_black,"#077AE4"));


        ////Banner Slider


        //horizontalProduct Layout



        List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.apple,"Apple Cider Vinegar","100% Natural Vinegar 500ml","450.00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.green_email,"Apple Cider Vinegar","100% Natural Vinegar 500ml","450.00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.red_email,"Apple Cider Vinegar","100% Natural Vinegar 500ml","450.00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.my_account,"Apple Cider Vinegar","100% Natural Vinegar 500ml","450.00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.cart_black,"Apple Cider Vinegar","100% Natural Vinegar 500ml","450.00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.custom_error,"Apple Cider Vinegar","100% Natural Vinegar 500ml","450.00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.ic_launcher_round,"Apple Cider Vinegar","100% Natural Vinegar 500ml","450.00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.app_icon,"Apple Cider Vinegar","100% Natural Vinegar 500ml","450.00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.ic_launcher_round,"Apple Cider Vinegar","100% Natural Vinegar 500ml","450.00"));


        //horizontalProduct Layout

        ///////////////////
        testing = view.findViewById(R.id.home_page_recycler_view);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        testing.setLayoutManager(testingLayoutManager);

        List<HomeViewModel> homeViewModelList = new ArrayList<>();
        homeViewModelList.add(new HomeViewModel(0,sliderModelList));
        homeViewModelList.add(new HomeViewModel(1,R.mipmap.upload_prescription,"#ff0000"));
        homeViewModelList.add(new HomeViewModel(2,"Deal of the Day",horizontalProductScrollModelList));
        homeViewModelList.add(new HomeViewModel(0,sliderModelList));
        homeViewModelList.add(new HomeViewModel(1,R.mipmap.upload_prescription,"#ffffff"));
        homeViewModelList.add(new HomeViewModel(2,"Deal of the Day",horizontalProductScrollModelList));
        homeViewModelList.add(new HomeViewModel(3,"Deal of the Day",horizontalProductScrollModelList));
        homeViewModelList.add(new HomeViewModel(0,sliderModelList));
        homeViewModelList.add(new HomeViewModel(1,R.mipmap.upload_prescription,"#fff000"));


        HomePageAdapter adapter = new HomePageAdapter(homeViewModelList);
        testing.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        /////////////////////

        return view;
    }
}