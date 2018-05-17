package com.artshell.misc.arch_mvp.annotation_impl;

import com.arch.mvp.BaseContract;
import com.arch.mvp.BaseLoadingContract;

/**
 * @author artshell on 2018/5/1
 */
public interface MasterContract {
    interface View extends BaseLoadingContract.View {
        void openSubFragment();
    }

    interface Presenter extends BaseContract.Presenter<View> {

    }
}
