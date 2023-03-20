package com.gsoft.wallet.view.navbar;
import android.widget.TableRow;
import android.widget.TableLayout;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gsoft.wallet.R;
import com.gsoft.wallet.utils.Utils;
import com.gsoft.wallet.view.activities.MainActivity;
import com.gsoft.wallet.viewmodel.NavBarViewModel;

public class BottomNav
{
    public int CHILD_COUNT = 0;
    public TextView[] ITEMS;
    private Utils u;
    public MainActivity mActivity;
    private NavBarViewModel viewModel;
    
    public BottomNav(Context ctx) {
        
        this.mActivity = (MainActivity) ctx;
        this.u = new Utils(ctx);
        this.viewModel = new NavBarViewModel(this);
        
        TableLayout navTableLayout = mActivity.findViewById(R.id.nav_table_layout);
        TableRow tableRow = (TableRow) navTableLayout.getChildAt(0);
        
        CHILD_COUNT = tableRow.getChildCount();
        ITEMS = new TextView[CHILD_COUNT];
        
        for (int i = 0; i < CHILD_COUNT; i++) {
            if (i != 2) {
                LinearLayout linearLay = (LinearLayout) tableRow.getChildAt(i);
                linearLay.setId(i+1);
                linearLay.setOnClickListener(viewModel.onClick);
                ITEMS[i] = (TextView) linearLay.getChildAt(1);
                u.setFont(ITEMS[i], "Light");
                if( i == 0) {
                    viewModel.onClick.setItem(linearLay, R.color.blue_600, "ic_home");
                }
            }
            else {
                TextView txt = new TextView(mActivity);
                txt.setText("Vide");
                ITEMS[i] = txt;
            }
        }
    }
    
}
