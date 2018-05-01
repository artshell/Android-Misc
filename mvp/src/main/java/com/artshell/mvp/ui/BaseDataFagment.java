package com.artshell.mvp.ui;

import android.support.v4.util.ArrayMap;

import java.util.Map;

import nucleus5.presenter.Presenter;

/**
 * @author artshell on 2018/4/29
 */
public class BaseDataFagment<P extends Presenter> extends FurtherSupportFragment<P> {

    protected Map<String, String> pairs = new ArrayMap<>();
}
