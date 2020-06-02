package com.xblaunguage.m999.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.xblaunguage.m999.MainActivity;
import com.xblaunguage.m999.R;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {
    public static final int CAROUSEL_TIME=5000;
    private ViewPager vpBanner;
    private ViewGroup viewGroup;
    private BannerAdapter bannerAdapter;
    private Handler handler=new Handler();
    private int currentItem=0;//当前位置
    private final Runnable mTicker=new Runnable() {
        @Override
        public void run() {
            long now= SystemClock.uptimeMillis();
            long next=now+(CAROUSEL_TIME-now%CAROUSEL_TIME);
            handler.postAtTime(mTicker,next);
            currentItem++;
            vpBanner.setCurrentItem(currentItem);
        }
    };
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final View view=inflater.inflate(R.layout.fragment_home,container,false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                MainActivity activity=(MainActivity) getActivity();
                activity.runAd();
            }
        });

        // 轮播广告
//        if (getActivity()==null) return root;
//            View view=getActivity().getLayoutInflater().inflate(R.layout.fragment_home,container,false);
//            vpBanner= (ViewPager) view.findViewById(R.id.vp_banner);
//
//            bannerAdapter=new BannerAdapter(getActivity());
//            bannerAdapter.setOnBannerClickListener(onBannerClickListener);
//            vpBanner.setOffscreenPageLimit(2);
//            vpBanner.setAdapter(bannerAdapter);
//            vpBanner.addOnPageChangeListener(onPageChangeListener);
//            viewGroup= (ViewGroup) view.findViewById(R.id.viewGroup);
//            for (int i=0;i<bannerAdapter.getBanners().length;++i){
//                ImageView imageView=new ImageView(getActivity());
//
//                imageView.setLayoutParams(new ViewGroup.LayoutParams(10,10));
//                if (i==0){
//                    imageView.setBackgroundResource(R.drawable.icon_page_select);
//                }
//                else{
//                    imageView.setBackgroundResource(R.drawable.icon_page_unselected);
//                }
//                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
//                layoutParams.leftMargin=10;
//                layoutParams.rightMargin=10;
//                viewGroup.addView(imageView,layoutParams);
//            }
//            currentItem=bannerAdapter.getBanners().length*50;
//            vpBanner.setCurrentItem(currentItem);
//            handler.postDelayed(mTicker,CAROUSEL_TIME);

        return root;
    }

    public  ViewPager.OnPageChangeListener onPageChangeListener=new ViewPager.OnPageChangeListener(){

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currentItem=position;
            setImageBackground(position%=bannerAdapter.getBanners().length);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private void setImageBackground(int selectItems){
        for (int i=0;i<bannerAdapter.getBanners().length;++i){
            ImageView imageView= (ImageView) viewGroup.getChildAt(i);
            imageView.setBackgroundDrawable(null);
            if (i==selectItems){
                imageView.setImageResource(R.drawable.icon_page_select);
            }else{
                imageView.setImageResource(R.drawable.icon_page_unselected);
            }
        }
    }
    private  View.OnClickListener onBannerClickListener=new View.OnClickListener()
    {

        @Override
        public void onClick(View v) {
            int position=(Integer)v.getTag();
//            Toast.makeText(MainActivity.this,"当前点击位置"+position,Toast.LENGTH_LONG).show();
        }
    };
    public   void onDestroy(){
        super.onDestroy();
        handler.removeCallbacks(mTicker);
    }

}
