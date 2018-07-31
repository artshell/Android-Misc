package com.artshell.arch.ui;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.artshell.arch.view_model.HttpNotificationViewModel;

import io.reactivex.Notification;

/**
 * Note: require jdk 8
 *
 * @param <T>
 * @see HttpNotificationViewModel
 *
 * @author artshell on 16/03/2018
 */
public interface NotificationObserver<T> extends
        Observer<Notification<T>>, NextConsumer<T>, ErrorConsumer, CompleteConsumer {

    @Override
    default void onChanged(@Nullable Notification<T> notify) {
        if (notify == null) return;
        if (notify.isOnNext()) {
            onNext(notify.getValue());
        } else if (notify.isOnError()) {
            accept(notify.getError());
        } else if (notify.isOnComplete()) {
            onComplete();
        }
    }
}
