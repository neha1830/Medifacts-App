package com.example.medifacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {
    private List<SliderModel> sliderModelList;

    public SliderAdapter(List<SliderModel> sliderModelList, ViewPager2 bannerSliderViewPager) {
        this.sliderModelList = sliderModelList;
        this.bannerSliderViewPager = bannerSliderViewPager;
    }

    private ViewPager2 bannerSliderViewPager;

    @NonNull
    @Override
    public SliderAdapter.SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.slider_layout,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SliderAdapter.SliderViewHolder holder, int position) {
        holder.setBanner(sliderModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return sliderModelList.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder {
        private ImageView banner;
        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.banner_slide);
        }
        void setBanner(SliderModel sliderModel){
            banner.setImageResource(sliderModel.getBanner());
        }
    }

}
