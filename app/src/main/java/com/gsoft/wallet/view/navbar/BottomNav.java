package com.gsoft.wallet.view.navbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TableLayout;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewpager.widget.ViewPager;

import com.gsoft.wallet.utils.NavIcon;
import com.gsoft.wallet.view.activities.HomeActivity;
import com.gsoft.wallet.R;
import com.gsoft.wallet.model.models.NavItem;
import com.gsoft.wallet.utils.Utils;
import java.util.ArrayList;

public class BottomNav
{
    public HomeActivity mActivity;
    private final TableLayout tableLayout;
    private final ArrayList<NavItem> listItems;

    private final Utils utils;
    private String[] listIconItemsActive;
    private String[] listIconItems;
    public BottomNav(Context context) {
        this.initIcon();
        this.mActivity = (HomeActivity) context;
        this.tableLayout = mActivity.findViewById(R.id.nav_table_layout);
        this.listItems = getAllItems();
        this.utils = new Utils(context);
    }

    private void initIcon() {
        listIconItemsActive = new String[NavIcon.values().length];
        listIconItems = new String[NavIcon.values().length];
        for (int i = 0 ; i < NavIcon.values().length; i++) {
            listIconItemsActive[i] = NavIcon.values()[i].active;
            listIconItems[i] = NavIcon.values()[i].inactive;
        }
    }

    public ArrayList<NavItem> getAllItems() {

        ArrayList<NavItem> allItems = new ArrayList<>();
        TableRow tableRow = (TableRow) tableLayout.getChildAt(0);
        for (int i = 0; i < tableRow.getChildCount(); i++) {
            int index = i;
            if (i != 2 ) {
                if (i > 2) {
                    index = i-1;
                }
                LinearLayout linearLayout = (LinearLayout) tableRow.getChildAt(i);
                NavItem item = new NavItem(linearLayout, listIconItems[index]);
                allItems.add(item);
            }
        }
        return allItems;
    }

    public void setItemActivate(int position) {
        for (NavItem navItem : listItems) {
            setActiveColor(navItem, R.color.grey_600, navItem.getIcon());
        }
        setActiveColor(listItems.get(position), R.color.blue_600, listIconItemsActive[position]);
    }

    private void setActiveColor(NavItem navItem, int color, String icon) {
        ImageView itemIcon = navItem.getImageView();
        TextView item = navItem.getTitleTextView();
        itemIcon.setImageDrawable(utils.getDrawable(icon));
        utils.setColor(item, color);
        utils.setColor(itemIcon, color);
    }

    public void setupWithViewPager(ViewPager viewPager) {
        setItemActivate(0);
        for (int i = 0 ; i < getAllItems().size(); i++) {
            final int index = i;
            NavItem item = getAllItems().get(i);
            item.getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    utils.btnClick(view);
                    viewPager.setCurrentItem(index, true);
                }
            });
        }
    }
}
