package com.artshell.mvp.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.android.FragmentEvent;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import nucleus5.factory.PresenterFactory;
import nucleus5.factory.ReflectionPresenterFactory;
import nucleus5.presenter.Presenter;
import nucleus5.view.PresenterLifecycleDelegate;
import nucleus5.view.ViewWithPresenter;

/**
 * This view is an example of how a view should control it's presenter.
 * You can inherit from this class or copy/paste this class's code to
 * create your own view implementation.
 * @author artshell on 2018/4/29
 * @see nucleus5.view.NucleusSupportFragment
 */
public class FurtherSupportFragment<P extends Presenter> extends Fragment implements ViewWithPresenter<P> {

    private static final String PRESENTER_STATE_KEY = "presenter_state_further";

    private   Subject<FragmentEvent>        takeSubject;
    private   PresenterLifecycleDelegate<P> presenterDelegate;
    protected Handler                       handler;

    public FurtherSupportFragment() {
        takeSubject = BehaviorSubject.<FragmentEvent>create().toSerialized();
        presenterDelegate = new PresenterLifecycleDelegate<>(ReflectionPresenterFactory.<P>fromViewClass(getClass()));
        handler = new Handler(Looper.getMainLooper());
    }

    /**
     * Returns a current presenter factory.
     */
    @Override
    public PresenterFactory<P> getPresenterFactory() {
        return presenterDelegate.getPresenterFactory();
    }

    /**
     * Sets a presenter factory.
     * Call this method before onCreate/onFinishInflate to override default {@link ReflectionPresenterFactory} presenter factory.
     * Use this method for presenter dependency injection.
     */
    @Override
    public void setPresenterFactory(PresenterFactory<P> presenterFactory) {
        presenterDelegate.setPresenterFactory(presenterFactory);
    }

    /**
     * Returns a current attached presenter.
     * This method is guaranteed to return a non-null value between
     * onResume/onPause and onAttachedToWindow/onDetachedFromWindow calls
     * if the presenter factory returns a non-null value.
     * @return a currently attached presenter or null.
     */
    @Override
    public P getPresenter() {
        return presenterDelegate.getPresenter();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        takeSubject.onNext(FragmentEvent.ATTACH);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        takeSubject.onNext(FragmentEvent.CREATE);
        if (bundle != null) {
            presenterDelegate.onRestoreInstanceState(bundle.getBundle(PRESENTER_STATE_KEY));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        takeSubject.onNext(FragmentEvent.CREATE_VIEW);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedState) {
        super.onViewCreated(view, savedState);
        presenterDelegate.onResume(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        takeSubject.onNext(FragmentEvent.START);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBundle(PRESENTER_STATE_KEY, presenterDelegate.onSaveInstanceState());
    }

    @Override
    public void onResume() {
        super.onResume();
        takeSubject.onNext(FragmentEvent.RESUME);
    }

    @Override
    public void onPause() {
        takeSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
    }

    @Override
    public void onStop() {
        takeSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        takeSubject.onNext(FragmentEvent.DESTROY_VIEW);
        presenterDelegate.onDropView();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        takeSubject.onNext(FragmentEvent.DESTROY);
        presenterDelegate.onDestroy(!getActivity().isChangingConfigurations());
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        takeSubject.onNext(FragmentEvent.DETACH);
        super.onDetach();
    }
}
