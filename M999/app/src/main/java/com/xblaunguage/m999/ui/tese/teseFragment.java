package com.xblaunguage.m999.ui.tese;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.xblaunguage.m999.MainActivity;
import com.xblaunguage.m999.R;


public class teseFragment extends Fragment {
    private teseViewModel tesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tesViewModel =
                ViewModelProviders.of(this).get(teseViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        tesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                MainActivity activity=(MainActivity) getActivity();
                activity.removeAd();
            }
        });
        return root;
    }
}
