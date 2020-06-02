package com.xblaunguage.m999.ui.tese;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class teseViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public teseViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("特色功能");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
