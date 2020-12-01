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
import androidx.lifecycle.ViewModelProvider;
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

    ///// Banner Slider
    private ViewPager2 bannerSliderViewPager;
    private List<SliderModel> sliderModelList;
    private int currentPage = 2;
    private Timer timer;
    final private long DELAY_TIME = 3000;
    final private long PERIOD_TIME = 3000;
    ///// Banner Slider

    ///upload prescription
    private ImageView uploadPrescription;
    private ConstraintLayout uploadPrescriptionContainer;

    ///upload prescription

    //horizontalProduct Layout
    private TextView horizontalLayoutTitle;
    private Button horizontalViewAllButton;
    private RecyclerView horizontalRecyclerView;


    //horizontalProduct Layout


    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
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


        SliderAdapter sliderAdapter = new SliderAdapter(sliderModelList);
        bannerSliderViewPager.setAdapter(sliderAdapter);
        bannerSliderViewPager.setClipToPadding(false);
        bannerSliderViewPager.setClipChildren(false);
        bannerSliderViewPager.setOffscreenPageLimit(3);
        bannerSliderViewPager.setPageTransformer(new MarginPageTransformer(20));
        bannerSliderViewPager.setCurrentItem(currentPage);

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
                if (state == ViewPager2.SCROLL_STATE_IDLE){
                    pageLooper();
                }
            }
        };
        bannerSliderViewPager.registerOnPageChangeCallback(onPageChangeCallback);
        startBannerSlideShow();

        bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
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

        ///upload prescription
        uploadPrescription = view.findViewById(R.id.upload_prescription);
        uploadPrescriptionContainer = view.findViewById(R.id.upload_prescription_strip_container);

        uploadPrescription.setImageResource(R.mipmap.upload_prescription);
        uploadPrescriptionContainer.setBackgroundColor(Color.parseColor("#facb5c"));
        ///upload prescription

        //horizontalProduct Layout

        horizontalLayoutTitle = view.findViewById(R.id.horizontal_scroll_layout_title);
        horizontalViewAllButton = view.findViewById(R.id.horizontal_scroll_viewAll_button);
        horizontalRecyclerView = view.findViewById(R.id.horizontal_scroll_layout_recyclerView);

        List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.apple,"Apple Cider Vinegar","100% Natural Vinegar 500ml","450.00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.green_email,"Apple Cider Vinegar","100% Natural Vinegar 500ml","450.00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.red_email,"Apple Cider Vinegar","100% Natural Vinegar 500ml","450.00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.my_account,"Apple Cider Vinegar","100% Natural Vinegar 500ml","450.00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.cart_black,"Apple Cider Vinegar","100% Natural Vinegar 500ml","450.00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.custom_error,"Apple Cider Vinegar","100% Natural Vinegar 500ml","450.00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.ic_launcher_round,"Apple Cider Vinegar","100% Natural Vinegar 500ml","450.00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.app_icon,"Apple Cider Vinegar","100% Natural Vinegar 500ml","450.00"));

        HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollModelList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        horizontalRecyclerView.setLayoutManager(linearLayoutManager);

        horizontalRecyclerView.setAdapter(horizontalProductScrollAdapter);
        horizontalProductScrollAdapter.notifyDataSetChanged();


        //horizontalProduct Layout

        ///Grid Product Layout
        TextView gridLayoutTitle = view.findViewById(R.id.grid_product_layout_title);
        Button gridLayoutViewAllBtn = view.findViewById(R.id.grid_product_layout_viewAll);
        GridView gridView = view.findViewById(R.id.grid_product_layout_gridView);

        gridView.setAdapter(new GridProductLayoutAdapter(horizontalProductScrollModelList));
        ///Grid Product Layout

        ///////////////////
        RecyclerView testing = view.findViewById(R.id.testing);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        testing.setLayoutManager(testingLayoutManager);

        List<HomeViewModel> homeViewModelList = new ArrayList<>();
        homeViewModelList.add(new HomeViewModel(0,sliderModelList));
        homeViewModelList.add(new HomeViewModel(1,R.mipmap.upload_prescription,"#ff0000"));
//        homeViewModelList.add(new HomeViewModel(2,"Deal of the Day",horizontalProductScrollModelList));
        homeViewModelList.add(new HomeViewModel(0,sliderModelList));
        homeViewModelList.add(new HomeViewModel(1,R.mipmap.upload_prescription,"#ffffff"));

        homeViewModelList.add(new HomeViewModel(0,sliderModelList));
        homeViewModelList.add(new HomeViewModel(1,R.mipmap.upload_prescription,"#fff000"));


        HomePageAdapter adapter = new HomePageAdapter(homeViewModelList);
        testing.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        /////////////////////

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