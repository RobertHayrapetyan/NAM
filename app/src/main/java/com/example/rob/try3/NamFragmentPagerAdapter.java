package com.example.rob.try3;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class NamFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public NamFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;

    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new YerevanFragment();
        }else if (position == 1){
            return new ShushiFragment();
        }else {
            return new GalleryFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return mContext.getString(R.string.yerevan);
            case 1:
                return mContext.getString(R.string.shushi);
            case 2:
                return mContext.getString(R.string.gallery);
            default:
                return null;
        }
    }
}
