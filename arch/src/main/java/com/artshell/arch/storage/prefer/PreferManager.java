package com.artshell.arch.storage.prefer;

import android.content.Context;

import com.artshell.arch.app.AppContext;
import com.artshell.arch.common.AppConstants;
import com.artshell.arch.storage.server.ApiConstants;
import com.github.pwittchen.prefser.library.rx2.Prefser;

/**
 * @author artshell on 17/03/2018
 */

public class PreferManager {
    private static final class ProfileHolder {
        static final Prefser appProfile = new Prefser(AppContext.getAppContext().getSharedPreferences(AppConstants.PROFILE_CACHE, Context.MODE_PRIVATE));
    }

    private static final class DataHolder {
        static final Prefser apiData = new Prefser(AppContext.getAppContext().getSharedPreferences(ApiConstants.DATA_CACHE, Context.MODE_PRIVATE));
    }

    public static Prefser appProfile() {
        return ProfileHolder.appProfile;
    }

    public static Prefser apiData() {
        return DataHolder.apiData;
    }
}
