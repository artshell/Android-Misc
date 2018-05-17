package com.arch.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Chatikyan on 29.06.2017.
 */

public class BaseAnnotatedV4Fragment<V extends BaseContract.View, P extends BaseContract.Presenter<V>>
        extends BaseV4Fragment<V, P> {

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
