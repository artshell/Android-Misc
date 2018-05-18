package com.arch.mvp;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author artshell on 2018/5/17
 */
public class BaseAnnotatedDialogFragment<V extends BaseContract.View, P extends BaseContract.Presenter<V>>
        extends BaseDialogFragment<V, P> {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedState) {
        return super.onCreateDialog(savedState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedState) {
        int layoutResId = getClass().getAnnotation(Viewable.class).layout();
        if (layoutResId != Viewable.LAYOUT_NOT_DEFINED) {
            return inflater.inflate(layoutResId, container, false);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected P initPresenter() {
        return (P) AnnotationHelper.createPresenter(getClass());
    }
}
