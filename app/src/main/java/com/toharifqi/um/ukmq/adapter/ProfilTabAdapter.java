package com.toharifqi.um.ukmq.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.toharifqi.um.ukmq.fragment.tabProduct;
import com.toharifqi.um.ukmq.fragment.tabProfile;
import com.toharifqi.um.ukmq.fragment.tabProject;

public class ProfilTabAdapter extends FragmentPagerAdapter {
    private int numOfTabs;

    public ProfilTabAdapter(@NonNull FragmentManager fm, int numOfTabs){
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        switch (position){
            case 0:
                fragment = new tabProfile();
                fragment.setArguments(bundle);
                break;
            case 1:
                fragment = new tabProduct();
                fragment.setArguments(bundle);
                break;
            case 2:
                fragment = new tabProject();
                fragment.setArguments(bundle);
                break;
            default:
                fragment = null;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
