package com.artshell.arch.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 数据来之Intent时可以直接继承此类
 * @author artshell on 28/03/2018
 */
public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedState) {
        return inflater.inflate(applyLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setProperty();
        loadData();
    }

    protected abstract int applyLayoutId();

    protected abstract void setProperty();

    protected abstract void loadData();
}
