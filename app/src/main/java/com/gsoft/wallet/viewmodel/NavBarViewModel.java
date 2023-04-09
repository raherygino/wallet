package com.gsoft.wallet.viewmodel;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.ImageView;
import android.widget.TextView;

import com.gsoft.wallet.R;
import com.gsoft.wallet.utils.Utils;
import com.gsoft.wallet.view.dialog.ProfileDialog;
import com.gsoft.wallet.view.navbar.BottomNav;

public class NavBarViewModel
{
    private BottomNav bottomNav;
    private Utils u;
    public onClick onClick;
    
    public NavBarViewModel(BottomNav nav) {
        this.bottomNav = nav;
        this.u = new Utils(nav.mActivity);
        this.onClick = new onClick();
    }
    
    public class onClick implements OnClickListener
    {
        public onClick(){}

        @Override
        public void onClick(View p1)
        {
            ItemClicked(p1);

        }
        private void ItemClicked(View v) {
            u.btnClick(v);
            int id = v.getId();
            if(v instanceof LinearLayout) {
                String[] icons = new String[]{
                    "ic_home_rounded_32",
                    "ic_account_outline",
                    "ic_calendar_outline",
                    "ic_bell_outline",
                };

                TableRow tblRow = (TableRow)v.getParent();
                for (int i = 0; i < icons.length; i++) {
                    View view = tblRow.getChildAt(i);
                    if (i > 1) view = tblRow.getChildAt(i+1);
                    setItem(view, R.color.grey_600,icons[i]);
                }

                int color = R.color.blue_600;
                switch(id) {
                    case 1:
                        setItem(v, color,"ic_home");
                        break;
                    case 2:
                        setItem(v, color,"ic_account");
                        break;
                    case 4:
                        setItem(v, color,"ic_calendar");
                        break;
                    case 5:
                        setItem(v, color,"ic_bell");
                        break;
                }

            }
        }

        public void setItem(View v, int color, String iconDrawable) {
            LinearLayout linLay = (LinearLayout) v;
            ImageView icon = (ImageView) linLay.getChildAt(0);
            TextView item = (TextView) linLay.getChildAt(1);
            icon.setImageDrawable(u.getDrawable(iconDrawable));
            u.setColor(item, color);
            u.setColor(icon, color);
        }


        
    }
}
