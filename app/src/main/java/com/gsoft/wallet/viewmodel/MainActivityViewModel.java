package com.gsoft.wallet.viewmodel;
import com.gsoft.wallet.view.activities.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gsoft.wallet.R;
import android.view.View;
import android.view.View.OnClickListener;
import com.gsoft.wallet.view.dialog.DialogEditBalance;
import android.content.Context;
import com.gsoft.wallet.model.database.DatabaseHelper;
import com.gsoft.wallet.utils.Utils;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.gsoft.wallet.view.recyclers.AdapterRecycler;
import java.util.ArrayList;
import com.gsoft.wallet.model.models.Balance;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.gsoft.wallet.view.recyclers.AdapterRecyclerProject;
import android.widget.PopupMenu;
import android.view.MenuItem;

public class MainActivityViewModel
{
    private Context context;
    private MainActivity mActivity;
    private DatabaseHelper db;
    private Utils u;
    public onClick onClickListener;
    private RecyclerView recyclerViewTransaction;
    //recyclerView;
    private RecyclerView.LayoutManager layoutManagerTransaction, layoutManager;
    public AdapterRecycler adapterRecyclerTransaction;
    public AdapterRecyclerProject adapterRecycler;
    public ArrayList<Balance> list;
    
    public MainActivityViewModel(MainActivity mainActivity) {
        this.context = mainActivity;
        this.mActivity = mainActivity;
        this.u = new Utils(context);
        this.db = new DatabaseHelper(context);
        this.onClickListener = new onClick();
        this.refresh();
        this.setDataToView();
        this.setViewEvent();
    }
   
    
    private void setDataToView() {
        list =  db.listData();
        
      /*  recyclerView = mActivity.findViewById(R.id.recyclerview_project);
        recyclerView.setHasFixedSize(true);
        
        layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager); 
        adapterRecycler = new AdapterRecyclerProject(mActivity, list);
        recyclerView.setAdapter(adapterRecycler);*/

        recyclerViewTransaction = mActivity.findViewById(R.id.recyclerview_transaction_home);
        recyclerViewTransaction.setHasFixedSize(true);
        layoutManagerTransaction = new LinearLayoutManager(mActivity);

        recyclerViewTransaction.setLayoutManager(layoutManagerTransaction); 
        adapterRecyclerTransaction = new AdapterRecycler(mActivity, list, recyclerViewTransaction);
        recyclerViewTransaction.setAdapter(adapterRecyclerTransaction); 
    } 
    
    private void setViewEvent() {
        mActivity.btn_fab_add.setOnClickListener(new onClick());
        mActivity.home_btn_settings.setOnClickListener(new onClick());
    }
    
    public void refresh() {
        int total = db.total("Entrée") - db.total("Sortie");
        mActivity.home_balance.setText(u.numberFormat(total+""));
    }
    
    class onClick implements View.OnClickListener
    {
        public onClick() {}

        @Override
        public void onClick(View p1)
        {
            u.btnClick(p1);
            int id = p1.getId();
            
            switch(id) {
                
                case R.id.fab_add:
                    DialogEditBalance n_balance = new DialogEditBalance(context);
                    n_balance.show();
                    break;
                    
                case R.id.home_btn_settings:
                    PopupMenu popupMenu = new PopupMenu(context, p1);
                    popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new onItemClick());
                    popupMenu.show();
                    break;
                    
            }

        }
    }
    public class onItemClick implements PopupMenu.OnMenuItemClickListener
    {

        @Override
        public boolean onMenuItemClick(MenuItem p1)
        {
            switch (p1.getItemId()) {
                case R.id.import_data:
                    mActivity.showFileChooser();
                    break;
                case R.id.export_data:
                    db.saveFile();
                    break;
            }

            return true;
        }

   }
}
