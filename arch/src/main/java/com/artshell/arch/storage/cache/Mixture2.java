package com.artshell.arch.storage.cache;

import android.support.annotation.NonNull;

import com.artshell.arch.storage.server.ApiConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import okhttp3.HttpUrl;


/**
 * @author artshell on 17/03/2018
 */

public class Mixture2 implements Key {

    private String url;
    private String path;
    private Map<String, String> pairs;

    public Mixture2(@NonNull String urlPath, @NonNull Map<String, String> pairs) {
        this.path = urlPath;
        this.pairs = pairs;

        HttpUrl.Builder builder = HttpUrl.parse(ApiConstants.ENDPOINT).newBuilder();
        builder.addPathSegments(urlPath);
        List<String> keys = new ArrayList<>(pairs.keySet());
        Collections.sort(keys);
        for (String key : keys) {
            builder.addQueryParameter(key, pairs.get(key));
        }
        url = builder.build().toString();
    }

    public Map<String, String> getPairs() {
        return pairs;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String getKey() {
        return String.valueOf(hashCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mixture2 mixture2 = (Mixture2) o;

        return url != null ? url.equals(mixture2.url) : mixture2.url == null;
    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : ApiConstants.ENDPOINT.hashCode() + Long.valueOf(System.currentTimeMillis()).hashCode();
    }
}
