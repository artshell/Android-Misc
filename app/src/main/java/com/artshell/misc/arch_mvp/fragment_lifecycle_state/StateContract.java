package com.artshell.misc.arch_mvp.fragment_lifecycle_state;

import com.arch.mvp.BaseContract;

/**
 * @author artshell on 2018/5/1
 */
public interface StateContract {

    interface View extends BaseContract.View {

    }

    interface Presenter extends BaseContract.Presenter<View> {

    }
}
