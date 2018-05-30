package com.artshell.misc.arch_mvp.fragment_lifecycle_state;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arch.mvp.BaseAnnotatedV4Fragment;
import com.arch.mvp.Viewable;
import com.artshell.misc.R;

/**
 * @author artshell on 2018/5/29
 */
@Viewable(presenter = StatePresenter.class, layout = R.layout.fragment_state_one)
public class SubOne extends BaseAnnotatedV4Fragment<StateContract.View, StateContract.Presenter>
        implements StateContract.View {

    private static final String TAG = "SubOne";

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i(TAG, "setUserVisibleHint: => " + isVisibleToUser);
        Log.i(TAG, "setUserVisibleHint: isVisible => " + isVisible());
        Log.i(TAG, "setUserVisibleHint: isResumed => " + isResumed());
        Log.i(TAG, "setUserVisibleHint: isHidden => " + isHidden());
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.i(TAG, "onHiddenChanged: => " + hidden);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach: ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedState) {
        super.onCreate(savedState);
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedState) {
        super.onActivityCreated(savedState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedState) {
        Log.i(TAG, "onCreateView: ");
        return super.onCreateView(inflater, container, savedState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedState) {
        Log.i(TAG, "onViewCreated: ");
        Log.i(TAG, "setUserVisibleHint: isVisible => " + isVisible());
        Log.i(TAG, "setUserVisibleHint: isResumed => " + isResumed());
        Log.i(TAG, "setUserVisibleHint: isHidden => " + isHidden());
        super.onViewCreated(view, savedState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach: ");
    }
}
