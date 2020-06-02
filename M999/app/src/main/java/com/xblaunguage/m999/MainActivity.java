package com.xblaunguage.m999;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.xblaunguage.m999.ui.home.BannerAdapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_tese)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



//
    }
    public void runAd(){
        vpBanner= (ViewPager) findViewById(R.id.vp_banner);
            bannerAdapter=new BannerAdapter(this);
            bannerAdapter.setOnBannerClickListener(onBannerClickListener);
            vpBanner.setOffscreenPageLimit(2);
            vpBanner.setAdapter(bannerAdapter);
            vpBanner.addOnPageChangeListener(onPageChangeListener);
            viewGroup= (ViewGroup) findViewById(R.id.viewGroup);
            for (int i=0;i<bannerAdapter.getBanners().length;++i){
                ImageView imageView=new ImageView(this);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(10,10));
                if (i==0){
                    imageView.setBackgroundResource(R.drawable.icon_page_select);
                }
                else{
                    imageView.setBackgroundResource(R.drawable.icon_page_unselected);
                }
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                layoutParams.leftMargin=10;
                layoutParams.rightMargin=10;
                viewGroup.addView(imageView,layoutParams);
            }
            currentItem=bannerAdapter.getBanners().length*50;
            vpBanner.setCurrentItem(currentItem);
            handler.postDelayed(mTicker,CAROUSEL_TIME);
    }
public void removeAd(){

    handler.removeCallbacks(mTicker);
    vpBanner.removeAllViews();
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
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
