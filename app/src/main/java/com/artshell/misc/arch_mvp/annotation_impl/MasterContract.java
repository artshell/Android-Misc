package com.artshell.misc.arch_mvp.annotation_impl;

import com.luseen.arch.BaseContract;
import com.luseen.arch.BaseLoadingContract;

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
