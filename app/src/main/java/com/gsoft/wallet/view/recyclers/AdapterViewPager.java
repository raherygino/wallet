package com.gsoft.wallet.view.recyclers;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.gsoft.wallet.view.tab.TabHome;
import com.gsoft.wallet.view.tab.TabProfile;
import com.gsoft.wallet.view.tab.TabProject;
import com.gsoft.wallet.view.tab.TabTransaction;

public class AdapterViewPager extends FragmentStatePagerAdapter {
    private Context context;
    public AdapterViewPager(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
    }

    // Return Fragment to display for that page
    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new TabHome(context);
            case 1:
                return new TabTransaction(context);
            case 2:
                return new TabProject(context);
            case 3:
                return new TabProfile();
            default:
                return null;
        }
    }

    // will be displayed as the tab's label
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "HOME";
            case 1:
                return "TRANSACTION";
            case 2:
                return "PROJECT";
            case 3:
                return "PROFILE";
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}

