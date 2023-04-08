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
import com.gsoft.wallet.view.dialog.EditProjectDialog;

public class AdapterRecyclerProject  extends RecyclerView.Adapter<AdapterRecyclerProject.MyHolder> 
{
    private final Context context;
    private ArrayList<Project> listProject;
    private Utils utils;

    public AdapterRecyclerProject(Context ctx,ArrayList<Project> aList) {
        this.context = ctx;
        this.listProject = aList;
        this.utils = new Utils(ctx);
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
        utils.setFont(myHolder.title, SEMI_BOLD);
        utils.setFont(myHolder.type, SEMI_BOLD);
        utils.setFont(myHolder.titleDeposit, SEMI_BOLD);
        utils.setFont(myHolder.titleTarget, REGULAR);
        utils.setFont(myHolder.deposit, LIGHT);
        utils.setFont(myHolder.target, LIGHT);

        String title = listProject.get(i).getTitle();
        String type = listProject.get(i).getType();
        String target = utils.numberFormat(String.valueOf(listProject.get(i).getTarget()));
        String deposit = utils.numberFormat(String.valueOf(listProject.get(i).getDeposit()));

        myHolder.title.setText(title);
        myHolder.type.setText(type);
        myHolder.target.setText(target);
        myHolder.deposit.setText(deposit);
        myHolder.color_priority.setBackgroundResource(listProject.get(i).colorPriority());
        myHolder.btn_more.setOnClickListener(new btnMoreOnClick(listProject.get(i).getId()));
    }

    @Override
    public int getItemCount()
    {
        return listProject.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder 
    {
        TextView title,type, titleDeposit, titleTarget, deposit, target;
        ImageView btn_more;
        LinearLayout color_priority;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_project_title);
            type = itemView.findViewById(R.id.item_project_type);
            titleDeposit = itemView.findViewById(R.id.item_project_title_deposit);
            titleTarget = itemView.findViewById(R.id.item_project_title_target);
            deposit = itemView.findViewById(R.id.item_project_deposit);
            target = itemView.findViewById(R.id.item_project_target);
            color_priority = itemView.findViewById(R.id.color_priority);
            btn_more = itemView.findViewById(R.id.item_project_btn_more);
        }
    }

    class btnMoreOnClick implements View.OnClickListener {
        private final int id_project;
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
                        dialog.buttonCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        dialog.buttonOk.setOnClickListener(new View.OnClickListener() {
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
                        EditProjectDialog editProject = new EditProjectDialog(context);
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
