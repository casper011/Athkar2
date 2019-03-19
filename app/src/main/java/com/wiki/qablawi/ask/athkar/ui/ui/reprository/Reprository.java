package com.wiki.qablawi.ask.athkar.ui.ui.reprository;

import com.wiki.qablawi.ask.athkar.ui.ui.itemmanegar.Manegar;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class Reprository {
    public LiveData<ArrayList<String>> getData() {
        final MutableLiveData<ArrayList<String>> data = new MutableLiveData<>();
        data.setValue(Manegar.getItems());
        return data;
    }
}
