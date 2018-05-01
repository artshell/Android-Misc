package com.artshell.mvp.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import nucleus5.factory.PresenterFactory;
import nucleus5.factory.ReflectionPresenterFactory;
import nucleus5.presenter.Presenter;
import nucleus5.view.PresenterLifecycleDelegate;
import nucleus5.view.ViewWithPresenter;

/**
 * This class is an example of how an activity could controls it's presenter.
 * You can inherit from this class or copy/paste this class's code to
 * create your own view implementation.
 *
 * @author artshell on 2018/4/29
 * @see nucleus5.view.NucleusFragmentActivity
 */
@SuppressLint("Registered")
public class FurtherFragmentActivity<P extends Presenter> extends AppCompatActivity implements ViewWithPresenter<P> {

    private static final String PRESENTER_STATE_KEY = "presenter_state_further";

    private   Subject<ActivityEvent>        takeSubject;
    private   PresenterLifecycleDelegate<P> presenterDelegate;
    protected Handler                       handler;

    public FurtherFragmentActivity() {
        takeSubject = BehaviorSubject.<ActivityEvent>create().toSerialized();
        presenterDelegate = new PresenterLifecycleDelegate<>(ReflectionPresenterFactory.<P>fromViewClass(getClass()));
        handler = new Handler(Looper.getMainLooper());
    }

    /**
     * Returns a current presenter factory.
     */
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
    public P getPresenter() {
        return presenterDelegate.getPresenter();
    }

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        takeSubject.onNext(ActivityEvent.CREATE);
        if (savedState != null) {
            presenterDelegate.onRestoreInstanceState(savedState.getBundle(PRESENTER_STATE_KEY));
        }
        presenterDelegate.onResume(this);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(PRESENTER_STATE_KEY, presenterDelegate.onSaveInstanceState());
    }

    @Override
    protected void onStart() {
        super.onStart();
        takeSubject.onNext(ActivityEvent.START);
    }

    @Override
    protected void onResume() {
        super.onResume();
        takeSubject.onNext(ActivityEvent.RESUME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        takeSubject.onNext(ActivityEvent.PAUSE);
    }

    @Override
    protected void onStop() {
        takeSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        takeSubject.onNext(ActivityEvent.DESTROY);
        presenterDelegate.onDropView();
        presenterDelegate.onDestroy(!isChangingConfigurations());
    }

    public final <T> LifecycleTransformer<T> takeUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(takeSubject, event);
    }

    /**
     * 显示dialog等
     */
    public void onShow() {

    }

    /**
     * 显示指定提示信息的dialog
     * @param resId
     */
    public void onShow(@StringRes int resId) {

    }

    /**
     * 关闭dialog等
     */
    public void onDismiss() {

    }

    /**
     * 处理Throwable
     */
    public void onError(Throwable thr) {

    }
}
