package com.gsoft.wallet.view.recyclers;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;
import java.util.List;
import android.view.View;
import androidx.annotation.NonNull;
import android.content.Context;
import android.view.LayoutInflater;
import com.gsoft.wallet.R;
import java.util.ArrayList;
import android.widget.TextView;
import android.widget.ImageView;
import com.gsoft.wallet.utils.Utils;

public class AdapterRecyclerProject  extends RecyclerView.Adapter<AdapterRecyclerProject.MyHolder> 
{
    private Context context;
    private ArrayList list;
    private Utils u;
    
    public AdapterRecyclerProject(Context ctx,ArrayList aList) {
        this.context = ctx;
        this.list = aList;
        this.u = new Utils(ctx);
    }

    @Override
    public AdapterRecyclerProject.MyHolder onCreateViewHolder(ViewGroup p1, int p2)
    {
        View view = LayoutInflater.from(context).inflate(R .layout.item_project, p1, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(AdapterRecyclerProject.MyHolder p1, int p2)
    {
        u.setFont(p1.title, "SemiBold");
        u.setFont(p1.title2, "SemiBold");
        u.setFont(p1.title_dep, "Regular");
        u.setFont(p1.title_targ, "Regular");
        u.setFont(p1.deposit, "Light");
        u.setFont(p1.target, "Light");
        p1.title.setText("Budget for BMW");
        p1.title2.setText("Vehicules");
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder 
    {
        TextView title, title2, title_dep, title_targ, deposit, target;
        ImageView btn_more;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_project_title);
            title2 = itemView.findViewById(R.id.item_project_title2);
            title_dep = itemView.findViewById(R.id.item_project_title_deposit);
            title_targ = itemView.findViewById(R.id.item_project_title_target);
            deposit = itemView.findViewById(R.id.item_project_deposit);
            target = itemView.findViewById(R.id.item_project_target);
            btn_more = itemView.findViewById(R.id.item_project_btn_more);
        }
    }

    
    

    
}
