package com.gsoft.wallet.view.recyclers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;

import com.gsoft.wallet.R;
import com.gsoft.wallet.model.models.Balance;
import com.gsoft.wallet.utils.Utils;
import com.gsoft.wallet.view.activities.MainActivity;
import com.gsoft.wallet.view.dialog.ConfirmDialog;
import com.gsoft.wallet.view.dialog.DialogEditBalance;

import java.util.ArrayList;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.MyHolder> {
    
    Context context;
    Utils utils;
    ArrayList<Balance> balance;
    ConfirmDialog confirm_dial;
    RecyclerView rView;
    
    public AdapterRecycler(Context context, ArrayList<Balance> nBalance, RecyclerView recyclerView) {
        this.context = context;
        this.balance = nBalance;
        this.utils = new Utils(context);
        this.rView = recyclerView;
    }
    
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction, viewGroup, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }
    
    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {   
    
        utils.setFont(myHolder.title, "SemiBold");
        utils.setFont(myHolder.amount, "Light");
        utils.setFont(myHolder.unity, "Light");
        utils.setFont(myHolder.date, "Light");
        
        Balance item = balance.get(i);
        myHolder.title.setText(item.getTitle());
        myHolder.date.setText(utils.formatDate(item.getFormatedDate())+" "+item.getTime());
        
        if (balance.get(i).getType().indexOf("Sortie") != -1) {
            myHolder.image.setImageDrawable(utils.getDrawable("ic_orbit_money_transfer_out"));
            setColorItem(myHolder, R.color.red_400);
            myHolder.amount.setText("-"+item.getFormatedAmount(utils));
        }
        else {
            setColorItem(myHolder, R.color.green_400);
            myHolder.amount.setText("+"+item.getFormatedAmount(utils));
        }
        myHolder.itemView.setOnClickListener(new onClick(i));
        
    }
    
    private void setColorItem(MyHolder mHolder, int color) 
    {
        mHolder.amount.setTextColor(ContextCompat.getColor(context, color));
        mHolder.unity.setTextColor(ContextCompat.getColor(context, color));
        mHolder.image.setColorFilter(ContextCompat.getColor(context, color), android.graphics.PorterDuff.Mode.SRC_IN);   
    }
    
    public void remove(int i) 
    {
        balance.remove(i);
        rView.removeAllViews();
    }
    
    public class onClick implements View.OnClickListener
    {
        private int position;
        
        public onClick(int pos) {
            this.position = pos;
        }
        
        @Override
        public void onClick(View view)
        {
            String del = "Supprimer";
            String edit = "Modifier";
            String title = balance.get(position).getTitle();
            String amount = balance.get(position).getFormatedAmount(utils);
            utils.btnClick(view);
            
            confirm_dial = new ConfirmDialog(context, title, amount);
            confirm_dial.btn_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View p) {
                    confirm_dial.dismiss();
                    DialogEditBalance dlg_edt = new DialogEditBalance(context);
                    dlg_edt.setPosition(position);
                    dlg_edt.viewModel.setData();
                    dlg_edt.show();
                }
            });
            
            confirm_dial.btn_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View p) {
                   if (context instanceof MainActivity) {
                       MainActivity mAct = (MainActivity)context;
                       mAct.viewModel.delete(balance.get(position).getId());
                       mAct.viewModel.refresh();
                   }
                    
                   confirm_dial.dismiss();
                   remove(position);
                }
            });
            confirm_dial.btn_2.setText(del);
            confirm_dial.btn_1.setText(edit);
            confirm_dial.show();
            
        }
    }
    
    @Override
    public int getItemCount() {
        return balance.size();
    }
    
    public static class MyHolder extends RecyclerView.ViewHolder 
    {
       TextView title, amount, date, unity;
       ImageView image, edit, del;
       
       public MyHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_transaction);
            amount = itemView.findViewById(R.id.amount_transaction);
            date = itemView.findViewById(R.id.date_transaction);
            unity = itemView.findViewById(R.id.unity);
            image = itemView.findViewById(R.id.img_transaction);
       }
    }
}
