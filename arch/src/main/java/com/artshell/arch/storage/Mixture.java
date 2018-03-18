package com.artshell.arch.storage;

import android.support.annotation.NonNull;

/**
 * @author artshell on 17/03/2018
 */

public class Mixture implements Key {

    private String key;
    private String url;

    public Mixture(@NonNull String key, @NonNull String url) {
        this.key = key;
        this.url = url;
    }

    @Override
    public String getKey() {
        return key;
    }

    public String getUrl() {
        return url;
    }
}
