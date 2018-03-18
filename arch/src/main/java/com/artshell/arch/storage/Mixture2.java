package com.artshell.arch.storage;

import android.support.annotation.NonNull;

import java.util.Map;


/**
 * @author artshell on 17/03/2018
 */

public class Mixture2 extends Mixture {
    private Map<String, String> pairs;

    public Mixture2(@NonNull String key, @NonNull String url, @NonNull Map<String, String> pairs) {
        super(key, url);
        this.pairs = pairs;
    }

    public Map<String, String> getPairs() {
        return pairs;
    }
}
