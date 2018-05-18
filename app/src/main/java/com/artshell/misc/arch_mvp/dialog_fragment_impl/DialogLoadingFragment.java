package com.artshell.misc.arch_mvp.dialog_fragment_impl;

import android.app.Dialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.arch.mvp.BaseAnnotatedDialogFragment;
import com.arch.mvp.Viewable;
import com.artshell.misc.R;

import java.util.List;

@Viewable(presenter = DialogPresenter.class, layout = R.layout.fragment_loading_dailog)
public class DialogLoadingFragment extends BaseAnnotatedDialogFragment<DialogContract.View, DialogContract.Presenter>
        implements DialogContract.View {

    private MutableLiveData<List<String>> items = new MutableLiveData<>();

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedState) {
        return new AlertDialog.Builder(requireContext()).create();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedState) {
        super.onViewCreated(view, savedState);

        // 定制View
        getDialog().setContentView(view);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);

        // 请求数据
        presenter.requestServer();
    }

    @Override
    public void deliveryResult(List<String> result) {
        if (result == null || result.isEmpty()) return;
        items.setValue(result);
    }

    public LiveData<List<String>> fellowship() {
        return items;
    }
}
