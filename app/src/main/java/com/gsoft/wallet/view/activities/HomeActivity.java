package com.gsoft.wallet.view.activities;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.gsoft.wallet.R;
import com.gsoft.wallet.utils.Utils;
import com.gsoft.wallet.view.navbar.BottomNav;
import com.gsoft.wallet.view.tab.TabHome;
import com.gsoft.wallet.view.tab.TabProfile;
import com.gsoft.wallet.view.tab.TabProject;
import com.gsoft.wallet.view.tab.TabTransaction;

public class HomeActivity extends AppCompatActivity {

    public Utils utils;
    private BottomNav bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        utils = new Utils(this);
        bottomNav = new BottomNav(this);
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNav.setItemActivate(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottomNav.setupWithViewPager(viewPager);
    }

    static class MyAdapter extends FragmentStatePagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        // Return Fragment to display for that page
        @NonNull
        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new TabHome();
                case 1:
                    return new TabTransaction();
                case 2:
                    return new TabProject();
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
}