package com.artshell.misc.arch_mvp.fragment_lifecycle_state;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * @author artshell on 2018/5/29
 */
public class StatePager extends FragmentStatePagerAdapter {
    String[] titles = {"One", "Two", "Three", "Four"};

    public StatePager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new SubOne();
            case 1:
                return new SubTwo();
            case 2:
                return new SubThree();
            default:
                return new SubFour();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
