package com.artshell.misc.arch_mvp.dialog_fragment_impl;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

/**
 * @author artshell on 2018/5/19
 */
public class FellowshipViewModel extends ViewModel {

    private MutableLiveData<List<String>> items = new MutableLiveData<>();

    public void setElement(List<String> result) {
        items.setValue(result);
    }

    public MutableLiveData<List<String>> getItems() {
        return items;
    }
}
