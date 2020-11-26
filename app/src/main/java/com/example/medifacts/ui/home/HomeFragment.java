package com.example.medifacts.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.medifacts.CategoryAdapter;
import com.example.medifacts.CategoryModel;
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

    ///// Banner Slider
    private ViewPager2 bannerSliderViewPager;
    private List<SliderModel> sliderModelList;
    private int currentPage = 2;
    private Timer timer;
    final private long DELAY_TIME = 3000;
    final private long PERIOD_TIME = 3000;
    ///// Banner Slider

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

        ////Banner Slider
        bannerSliderViewPager = view.findViewById(R.id.banner_slider_view_pager);
        sliderModelList = new ArrayList<SliderModel>();
        sliderModelList.add(new SliderModel(R.mipmap.red_email));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher));

        sliderModelList.add(new SliderModel(R.mipmap.custom_error));
        sliderModelList.add(new SliderModel(R.mipmap.cart_black));
        sliderModelList.add(new SliderModel(R.mipmap.profile_placeholder));
        sliderModelList.add(new SliderModel(R.mipmap.home_icon));
        sliderModelList.add(new SliderModel(R.mipmap.green_email));
        sliderModelList.add(new SliderModel(R.mipmap.red_email));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher));
        sliderModelList.add(new SliderModel(R.mipmap.banner));

        sliderModelList.add(new SliderModel(R.mipmap.custom_error));
        sliderModelList.add(new SliderModel(R.mipmap.cart_black));

        bannerSliderViewPager.setAdapter(new SliderAdapter(sliderModelList,bannerSliderViewPager));
        bannerSliderViewPager.setClipToPadding(false);
        bannerSliderViewPager.setPageTransformer(new MarginPageTransformer(20));

        ViewPager2.OnPageChangeCallback onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE){
                    pageLooper();
                }
            }
        };
        bannerSliderViewPager.registerOnPageChangeCallback(onPageChangeCallback);
        startBannerSlideShow();

        bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                pageLooper();
                stopBannerSlideShow();
                if (event.getAction()==MotionEvent.ACTION_UP){
                    startBannerSlideShow();
                }
                return false;
            }
        });

        ////Banner Slider
        return view;
    }

    //// Banner Slider

    private void pageLooper(){
        if (currentPage == sliderModelList.size()-2){
            currentPage = 2;
            bannerSliderViewPager.setCurrentItem(currentPage,false);
        }
        if (currentPage == 1){
            currentPage = sliderModelList.size()-3;
            bannerSliderViewPager.setCurrentItem(currentPage,false);
        }
    }

    private void startBannerSlideShow(){
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if (currentPage >= sliderModelList.size()){
                    currentPage = 1;
                }
                bannerSliderViewPager.setCurrentItem(currentPage++,true);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, DELAY_TIME,PERIOD_TIME);
    }
    private void stopBannerSlideShow(){
        timer.cancel();
    }


    //// Banner Slider
}