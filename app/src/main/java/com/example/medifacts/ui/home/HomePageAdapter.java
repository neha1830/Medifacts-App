package com.example.medifacts.ui.home;

import android.annotation.SuppressLint;
import android.graphics.Color;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.medifacts.GridProductLayoutAdapter;
import com.example.medifacts.HorizontalProductScrollAdapter;
import com.example.medifacts.HorizontalProductScrollModel;
import com.example.medifacts.R;
import com.example.medifacts.SliderAdapter;
import com.example.medifacts.SliderModel;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageAdapter extends RecyclerView.Adapter {
    private List<HomeViewModel> homeViewModelList;

    public HomePageAdapter(List<HomeViewModel> homeViewModelList) {
        this.homeViewModelList = homeViewModelList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (homeViewModelList.get(position).getType()) {
            case 0:
                return HomeViewModel.BANNER_SLIDER;
            case 1:
                return HomeViewModel.UPLOAD_PRESCRIPTION_STRIP;
            case 2:
                return HomeViewModel.HORIZONTAL_PRODUCT_VIEW;
            case 3:
                return HomeViewModel.GRID_PRODUCT_VIEW;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case HomeViewModel.BANNER_SLIDER:
                View bannerSliderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sliding_ad_banner, parent, false);
                return new BannerSliderViewholder(bannerSliderView);
            case HomeViewModel.UPLOAD_PRESCRIPTION_STRIP:
                View uploadPrescriptionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.upload_prescription_strip, parent, false);
                return new UploadPrescriptionViewholder(uploadPrescriptionView);
            case HomeViewModel.HORIZONTAL_PRODUCT_VIEW:
                View horizontalProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_layout, parent, false);
                return new HorizontalProductViewholder(horizontalProductView);
            case HomeViewModel.GRID_PRODUCT_VIEW:
                View gridProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_product_layout, parent, false);
                return new GridProductViewholder(gridProductView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (homeViewModelList.get(position).getType()) {
            case HomeViewModel.BANNER_SLIDER:
                List<SliderModel> sliderModelList = homeViewModelList.get(position).getSliderModelList();
                ((BannerSliderViewholder) holder).setBannerSliderViewPager(sliderModelList);
                break;
            case HomeViewModel.UPLOAD_PRESCRIPTION_STRIP:
                int resource = homeViewModelList.get(position).getResource();
                String color = homeViewModelList.get(position).getBackgroundColor();
                ((UploadPrescriptionViewholder) holder).setUploadPrescription(resource, color);
                break;
            case HomeViewModel.HORIZONTAL_PRODUCT_VIEW:
                String horizontalLayoutTitle = homeViewModelList.get(position).getTitle();
                List<HorizontalProductScrollModel> horizontalProductScrollModelList = homeViewModelList.get(position).getHorizontalProductScrollModelList();
                ((HorizontalProductViewholder)holder).setHorizontalProductLayout(horizontalProductScrollModelList,horizontalLayoutTitle);
                break;
            case HomeViewModel.GRID_PRODUCT_VIEW:
                String gridLayoutTitle= homeViewModelList.get(position).getTitle();
                List<HorizontalProductScrollModel> gridProductScrollModelList = homeViewModelList.get(position).getHorizontalProductScrollModelList();
                ((GridProductViewholder)holder).setGridProductLayout(gridProductScrollModelList,gridLayoutTitle);
                break;
            default:
                return;
        }
    }

    @Override
    public int getItemCount() {
        return homeViewModelList.size();
    }

    public class BannerSliderViewholder extends RecyclerView.ViewHolder {
        private ViewPager2 bannerSliderViewPager;
        private int currentPage = 2;
        private Timer timer;
        final private long DELAY_TIME = 3500;
        final private long PERIOD_TIME = 3500;

        public BannerSliderViewholder(@NonNull View itemView) {
            super(itemView);
            bannerSliderViewPager = itemView.findViewById(R.id.banner_slider_view_pager);

        }

        private void setBannerSliderViewPager(final List<SliderModel> sliderModelList) {
            final SliderAdapter sliderAdapter = new SliderAdapter(sliderModelList);
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
                    if (state == ViewPager2.SCROLL_STATE_IDLE) {
                        pageLooper(sliderModelList);
                    }
                }
            };
            bannerSliderViewPager.registerOnPageChangeCallback(onPageChangeCallback);
            startBannerSlideShow(sliderModelList);

            bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    pageLooper(sliderModelList);
                    stopBannerSlideShow();
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        startBannerSlideShow(sliderModelList);
                    }
                    return false;
                }
            });
        }

        private void pageLooper(List<SliderModel> sliderModelList) {
            if (currentPage == sliderModelList.size() - 2) {
                currentPage = 2;
                bannerSliderViewPager.setCurrentItem(currentPage, false);
            }
            if (currentPage == 1) {
                currentPage = sliderModelList.size() - 3;
                bannerSliderViewPager.setCurrentItem(currentPage, false);
            }
        }

        private void startBannerSlideShow(final List<SliderModel> sliderModelList) {
            final Handler handler = new Handler();
            final Runnable update = new Runnable() {
                @Override
                public void run() {
                    if (currentPage >= sliderModelList.size()) {
                        currentPage = 1;
                    }
                    bannerSliderViewPager.setCurrentItem(currentPage++, true);
                }
            };
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);
                }
            }, DELAY_TIME, PERIOD_TIME);
        }

        private void stopBannerSlideShow() {
            timer.cancel();
        }
    }

    public class UploadPrescriptionViewholder extends RecyclerView.ViewHolder {

        private ImageView uploadPrescription;
        private ConstraintLayout uploadPrescriptionContainer;


        public UploadPrescriptionViewholder(@NonNull View itemView) {
            super(itemView);
            uploadPrescription = itemView.findViewById(R.id.upload_prescription);
            uploadPrescriptionContainer = itemView.findViewById(R.id.upload_prescription_strip_container);
        }

        private void setUploadPrescription(int resource, String color) {
            uploadPrescription.setImageResource(resource);
            uploadPrescriptionContainer.setBackgroundColor(Color.parseColor(color));
        }
    }

    public class HorizontalProductViewholder extends RecyclerView.ViewHolder {
        private TextView horizontalLayoutTitle;
        private Button horizontalViewAllButton;
        private RecyclerView horizontalRecyclerView;
//
        public HorizontalProductViewholder(@NonNull View itemView) {
            super(itemView);
            horizontalLayoutTitle = itemView.findViewById(R.id.horizontal_scroll_layout_title);
            horizontalViewAllButton = itemView.findViewById(R.id.horizontal_scroll_viewAll_button);
            horizontalRecyclerView = itemView.findViewById(R.id.horizontal_scroll_layout_recyclerView);
        }

        private void setHorizontalProductLayout(List<HorizontalProductScrollModel> horizontalProductScrollModelList, String title) {
            horizontalLayoutTitle.setText(title);

            if (horizontalProductScrollModelList.size() > 8){
                horizontalViewAllButton.setVisibility(View.VISIBLE);
            }else {
                horizontalViewAllButton.setVisibility(View.INVISIBLE);
            }

            HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollModelList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizontalRecyclerView.setLayoutManager(linearLayoutManager);

            horizontalRecyclerView.setAdapter(horizontalProductScrollAdapter);
            horizontalProductScrollAdapter.notifyDataSetChanged();
        }
    }

    public class GridProductViewholder extends RecyclerView.ViewHolder{
        private TextView gridLayoutTitle;
        private Button gridLayoutViewAllBtn;
        private GridView gridView;

        public GridProductViewholder(@NonNull View itemView) {
            super(itemView);
            gridLayoutTitle = itemView.findViewById(R.id.grid_product_layout_title);
            gridLayoutViewAllBtn = itemView.findViewById(R.id.grid_product_layout_viewAll);
            gridView = itemView.findViewById(R.id.grid_product_layout_gridView);
        }
        private void setGridProductLayout(List<HorizontalProductScrollModel> horizontalProductScrollModelList,String title){
            gridLayoutTitle.setText(title);
            gridView.setAdapter(new GridProductLayoutAdapter(horizontalProductScrollModelList));
        }
    }

}
