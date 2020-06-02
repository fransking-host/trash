package com.xblaunguage.m999.ui.home;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.PagerAdapter;

import com.xblaunguage.m999.R;


public class BannerAdapter extends PagerAdapter {
    private Context context;
    private View.OnClickListener onBannerClickListener;
    private int[] banners=new int[]{R.mipmap.banner1,R.mipmap.banner2,R.mipmap.banner3};
    public BannerAdapter(Context context){
        this.context= context;
    }
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
    public  void destroyItem(ViewGroup container,int position,Object object){
        container.removeView((View) object);
    }

    @NonNull


    public  Object instantiateItem(ViewGroup container, int position){
        position%=banners.length;
       ImageView imageView=new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(banners[position]);
        imageView.setTag(position);
        imageView.setOnClickListener(onClickListener);
        container.addView(imageView);
        return imageView;
    }
    private View.OnClickListener onClickListener=new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if (onBannerClickListener!=null){
                onBannerClickListener.onClick(v);
            }
        }
    };
    public void setOnBannerClickListener(View.OnClickListener onBannerClickListener){
        this.onBannerClickListener = onBannerClickListener;
    }
    public int[] getBanners(){
        return banners;
    }
}
