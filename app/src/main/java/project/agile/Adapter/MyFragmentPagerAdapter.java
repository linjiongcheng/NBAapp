package project.agile.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Oneplus on 2017/5/21.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    public final int COUNT = 4;
    private List<Fragment> list_fragment;//fragment列表
    private String[] titles = new String[]{"球员", "教练", "球队", "场馆"};
    private Context context;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list_fragment, Context context) {
        super(fm);
        this.list_fragment = list_fragment;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return list_fragment.get(position);
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
