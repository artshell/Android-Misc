package com.artshell.arch.storage;

import android.support.annotation.StringDef;

/**
 * @author artshell on 19/03/2018
 */
@StringDef({Status.SUCCESS, Status.ERROR, Status.LOADING, Status.LOADING, Status.COMPLETE, Status.CANCEL})
public @interface Status {
    String SUCCESS  = "success";
    String ERROR    = "error";
    String LOADING  = "loading";
    String COMPLETE = "complete";
    String CANCEL   = "cancel";
}
