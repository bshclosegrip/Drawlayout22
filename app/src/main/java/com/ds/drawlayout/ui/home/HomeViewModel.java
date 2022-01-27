package com.ds.drawlayout.ui.home;

import android.content.ClipData;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<ClipData.Item> selectedItem = new MutableLiveData<ClipData.Item>();
    public void selectItem(ClipData.Item item) {
        selectedItem.setValue(item);
    }

    public LiveData<ClipData.Item> getSelectedItem() {
        return selectedItem;
    }
}