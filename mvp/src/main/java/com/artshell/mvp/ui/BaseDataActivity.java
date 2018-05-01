package com.artshell.mvp.ui;

import android.annotation.SuppressLint;
import android.support.v4.util.ArrayMap;

import java.util.Map;

import nucleus5.presenter.Presenter;

/**
 * @author artshell on 2018/4/23
 */
@SuppressLint("Registered")
public class BaseDataActivity<P extends Presenter> extends FurtherFragmentActivity<P> {

    protected Map<String, String> pairs = new ArrayMap<>();


}
