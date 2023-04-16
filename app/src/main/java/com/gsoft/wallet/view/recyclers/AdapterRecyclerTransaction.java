package com.gsoft.wallet.view.recyclers;

import static com.gsoft.wallet.utils.Utils.LIGHT;
import static com.gsoft.wallet.utils.Utils.SEMI_BOLD;

import android.annotation.SuppressLint;
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
import com.gsoft.wallet.model.database.DatabaseHelper;
import com.gsoft.wallet.model.models.Transaction;
import com.gsoft.wallet.utils.Utils;
import com.gsoft.wallet.view.activities.HomeActivity;
import com.gsoft.wallet.view.activities.MainActivity;
import com.gsoft.wallet.view.dialog.ConfirmDialog;
import com.gsoft.wallet.view.dialog.EditTransactionDialog;
import java.util.ArrayList;

public class AdapterRecyclerTransaction extends RecyclerView.Adapter<AdapterRecyclerTransaction.MyHolder> {
    
    private final Context context;
    private final Utils utils;
    private final ArrayList<Transaction> transaction;
    private ConfirmDialog confirm_dial;
    private final RecyclerView recyclerView;
    
    public AdapterRecyclerTransaction(Context context, ArrayList<Transaction> nTransaction, RecyclerView recyclerView) {
        this.context = context;
        this.transaction = nTransaction;
        this.utils = new Utils(context);
        this.recyclerView = recyclerView;
    }
    
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction, viewGroup, false);
        return new MyHolder(view);
    }
    
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {   
    
        utils.setFont(myHolder.title, SEMI_BOLD);
        utils.setFont(myHolder.amount, LIGHT);
        utils.setFont(myHolder.unity, LIGHT);
        utils.setFont(myHolder.date, LIGHT);
        
        Transaction item = transaction.get(i);
        myHolder.title.setText(item.getTitle());
        myHolder.date.setText(utils.formatDate(item.getFormatedDate())+" "+item.getTime());
        
        if (transaction.get(i).getType().contains(utils.getString(R.string.out))) {
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
        transaction.remove(i);
        recyclerView.removeAllViews();
    }
    
    public class onClick implements View.OnClickListener
    {
        private final int position;
        
        public onClick(int pos) {
            this.position = pos;
        }
        
        @Override
        public void onClick(View view)
        {
            String del = utils.getString(R.string.delete);
            String edit = utils.getString(R.string.edit);
            String title = transaction.get(position).getTitle();
            String amount = transaction.get(position).getFormatedAmount(utils);
            utils.btnClick(view);
            
            confirm_dial = new ConfirmDialog(context, title, amount);
            confirm_dial.buttonOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View p) {
                    confirm_dial.dismiss();
                    EditTransactionDialog dlg_edt = new EditTransactionDialog(context);
                    dlg_edt.setPosition(position);
                    dlg_edt.viewModel.setData();
                    dlg_edt.show();
                }
            });
            
            confirm_dial.buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View p) {
                   if (context instanceof HomeActivity) {
                       HomeActivity homeActivity = (HomeActivity)context;
                       int transactionId = transaction.get(position).getId();
                       new DatabaseHelper(context).deleteTransaction(transactionId);
                       homeActivity.refreshFragement();
                   }
                   confirm_dial.dismiss();
                }
            });
            confirm_dial.buttonCancel.setText(del);
            confirm_dial.buttonOk.setText(edit);
            confirm_dial.show();
            
        }
    }
    
    @Override
    public int getItemCount() {
        return transaction.size();
    }
    
    public static class MyHolder extends RecyclerView.ViewHolder 
    {
       TextView title, amount, date, unity;
       ImageView image;
       
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
