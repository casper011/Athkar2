package com.wiki.qablawi.ask.athkar.ui.ui.viewmodel;

import com.wiki.qablawi.ask.athkar.ui.ui.reprository.Reprository;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;

public class ViewModel extends androidx.lifecycle.ViewModel {
    private Reprository reprository;

    public ViewModel() {
        this.reprository = new Reprository();
    }

    public LiveData<ArrayList<String>> getDataList() {
        return reprository.getData();
    }

}
