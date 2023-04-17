package com.gsoft.wallet.view.tab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gsoft.wallet.R;
import com.gsoft.wallet.model.database.DatabaseHelper;
import com.gsoft.wallet.model.models.Project;
import com.gsoft.wallet.view.recyclers.AdapterRecyclerProject;

import java.util.ArrayList;

public class TabProject extends Fragment {
    private final Context context;
    private final DatabaseHelper database;
    public TabProject(Context context) {
        this.context = context;
        this.database = new DatabaseHelper(context);
    }

    @Nullable
    @Override
    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.tab_project, container, false);
        RecyclerView recyclerViewProject = view.findViewById(R.id.recyclerview_project);
        recyclerViewProject.setHasFixedSize(true);
        ArrayList<Project> listProject = database.listProjectByStat(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.context);
        recyclerViewProject.setLayoutManager(layoutManager);
        AdapterRecyclerProject adapterRecycler = new AdapterRecyclerProject(this.context, listProject);
        recyclerViewProject.setAdapter(adapterRecycler);
        return view;
    }
}
