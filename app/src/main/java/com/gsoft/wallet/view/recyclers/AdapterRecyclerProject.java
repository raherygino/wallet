package com.gsoft.wallet.view.recyclers;

import static com.gsoft.wallet.utils.Utils.LIGHT;
import static com.gsoft.wallet.utils.Utils.REGULAR;
import static com.gsoft.wallet.utils.Utils.SEMI_BOLD;

import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.View;
import androidx.annotation.NonNull;
import android.content.Context;
import android.view.LayoutInflater;
import java.util.ArrayList;

import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.ImageView;

import com.gsoft.wallet.R;
import com.gsoft.wallet.model.database.DatabaseHelper;
import com.gsoft.wallet.model.models.Project;
import com.gsoft.wallet.utils.Utils;
import com.gsoft.wallet.view.activities.MainActivity;
import com.gsoft.wallet.view.dialog.ConfirmDialog;
import com.gsoft.wallet.view.dialog.DialogEditProject;

public class AdapterRecyclerProject  extends RecyclerView.Adapter<AdapterRecyclerProject.MyHolder> 
{
    private final Context context;
    private ArrayList<Project> list;
    private Utils u;

    public AdapterRecyclerProject(Context ctx,ArrayList<Project> aList) {
        this.context = ctx;
        this.list = aList;
        this.u = new Utils(ctx);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup p1, int p2)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.item_project, p1, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder myHolder, int i)
    {
        u.setFont(myHolder.title, SEMI_BOLD);
        u.setFont(myHolder.type, SEMI_BOLD);
        u.setFont(myHolder.title_dep, SEMI_BOLD);
        u.setFont(myHolder.title_targ, REGULAR);
        u.setFont(myHolder.deposit, LIGHT);
        u.setFont(myHolder.target, LIGHT);
        myHolder.title.setText(list.get(i).getTitle());
        myHolder.type.setText(list.get(i).getType());
        myHolder.target.setText(u.numberFormat(u.intToString(list.get(i).getTarget())));
        myHolder.deposit.setText(u.numberFormat(u.intToString(list.get(i).getDeposit())));
        myHolder.color_priority.setBackgroundResource(list.get(i).colorPriority());
        myHolder.btn_more.setOnClickListener(new btnMoreOnClick(list.get(i).getId()));
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder 
    {
        TextView title,type, title_dep, title_targ, deposit, target;
        ImageView btn_more;
        LinearLayout color_priority;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_project_title);
            type = itemView.findViewById(R.id.item_project_type);
            title_dep = itemView.findViewById(R.id.item_project_title_deposit);
            title_targ = itemView.findViewById(R.id.item_project_title_target);
            deposit = itemView.findViewById(R.id.item_project_deposit);
            target = itemView.findViewById(R.id.item_project_target);
            color_priority = itemView.findViewById(R.id.color_priority);
            btn_more = itemView.findViewById(R.id.item_project_btn_more);
        }
    }

    class btnMoreOnClick implements View.OnClickListener {
        private int id_project;
        public  btnMoreOnClick(int id) {
            id_project = id;
        }
        @Override
        public void onClick(View view) {
            PopupMenu popupMenu = new PopupMenu(context, view);
            popupMenu.getMenuInflater().inflate(R.menu.project_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onMenuItemClick(MenuItem item)
                {
                    int id = item.getItemId();
                    if (id == R.id.delete_project) {
                        ConfirmDialog dialog = new ConfirmDialog(context, "Supprimer","Voulez vous supprimer vraimenent?");
                        dialog.show();
                        dialog.btn_1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        dialog.btn_2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DatabaseHelper db = new DatabaseHelper(context);
                                db.deleteProject(id_project);
                                MainActivity mainActivity = (MainActivity) context;
                                mainActivity.viewModel.refreshProject();
                                dialog.dismiss();
                            }
                        });
                    } else if (id == R.id.edit_project) {
                        DialogEditProject editProject = new DialogEditProject(context);
                        editProject.viewModel.setData(id_project);
                        editProject.show();
                    }
                    return true;
                }
            });
            popupMenu.show();
        }
    }
}
