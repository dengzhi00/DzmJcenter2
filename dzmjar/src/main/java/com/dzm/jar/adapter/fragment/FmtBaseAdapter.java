package com.dzm.jar.adapter.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;


/**
 * @author dzm
 *         data 2017/11/28 am 11:42
 */

public class FmtBaseAdapter extends FragmentPagerAdapter {

    public static final String DATA = "fmr_data";

    private FmtModel[] fmtModels;

    public FmtBaseAdapter(FragmentManager fm, FmtModel[] fmtModels) {
        super(fm);
        this.fmtModels = fmtModels;
    }

    @Override
    public Fragment getItem(int position) {
        FmtModel fmtModel = fmtModels[position%fmtModels.length];
        Fragment fragment = fmtModel.fragment;
        Bundle bundle = fragment.getArguments();
        if(null == bundle){
            bundle = new Bundle();
        }
        String bdStr = fmtModel.bundleString;
        if(!TextUtils.isEmpty(bdStr)){
            bundle.putString(DATA,bdStr);
        }
        if(null != fmtModel.bundle){
            bundle.putAll(fmtModel.bundle);
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return fmtModels.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fmtModels[position%fmtModels.length].title;
    }
}
