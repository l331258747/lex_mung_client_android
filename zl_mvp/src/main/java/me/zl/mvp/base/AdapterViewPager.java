package me.zl.mvp.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * ViewPager的adapter
 * 基类 {@link FragmentStatePagerAdapter}
 */
public class AdapterViewPager extends FragmentStatePagerAdapter {
    private List<Fragment> mList;
    private List<String> mTitles;

    public AdapterViewPager(FragmentManager fragmentManager, List<Fragment> list) {
        super(fragmentManager);
        this.mList = list;
    }

    public AdapterViewPager(FragmentManager fragmentManager, List<Fragment> list, List<String> titles) {
        super(fragmentManager);
        this.mList = list;
        this.mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles != null && position < mTitles.size()) {
            return mTitles.get(position);
        }
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
