package com.artshell.arch.storage.server;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * @author artshell on 17/03/2018
 */

public class Mixture2 implements Key {

    private String url;
    private String path;
    private Map<String, String> pairs;
    private static StringBuffer buffer = new StringBuffer();

    public Mixture2(@NonNull String path, @NonNull Map<String, String> pairs) {
        this.path = path;
        this.pairs = pairs;
        buffer.setLength(0);
        buffer.append(ApiConstants.ENDPOINT).append("/").append(path);
        if (pairs.size() > 0) {
            List<String> keys = new ArrayList<>(pairs.keySet());
            Collections.sort(keys);
            Iterator<String> iter = keys.iterator();
            String first = iter.next();
            buffer.append("?").append(first).append("=").append(pairs.get(first));
            while (iter.hasNext()) {
                String next = iter.next();
                buffer.append("&").append(next).append("=").append(pairs.get(next));
            }
        }
        url = buffer.toString();
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
        return url != null ? url.hashCode() : 0;
    }
}
