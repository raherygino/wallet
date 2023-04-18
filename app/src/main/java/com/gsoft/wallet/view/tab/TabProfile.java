package com.gsoft.wallet.view.tab;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.gsoft.wallet.R;
import com.gsoft.wallet.model.database.DatabaseHelper;
import com.gsoft.wallet.utils.Utils;
import com.gsoft.wallet.view.activities.AdminActivity;
import com.gsoft.wallet.view.activities.HomeActivity;
import com.gsoft.wallet.view.activities.SplashActivity;
import com.gsoft.wallet.view.dialog.ConfirmDialog;
import com.gsoft.wallet.view.dialog.ExportDialog;

public class TabProfile extends Fragment {

    int[] images = {
            R.drawable.ic_account,
            R.drawable.ic_appbar_arrow_right,
            R.drawable.ic_appbar_arrow_left,
            R.drawable.ic_orbit_close_circle };
    String[] texts;

    private final Context context;
    private final DatabaseHelper database;
    private final Utils utils;

    public TabProfile(Context context) {
        this.context = context;
        this.utils = new Utils(context);
        this.texts = new String[]{
                utils.getString(R.string.administrator),
                utils.getString(R.string.export),
                utils.getString(R.string.importData),
                utils.getString(R.string.wipe) };
        this.database = new DatabaseHelper(context);
    }

    @Override
    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.tab_profile, container, false);
        ListView menuList = view.findViewById(R.id.list_menu);
        ListAdapter listAdapter = new ListAdapter(context, images, texts);
        menuList.setAdapter(listAdapter);
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                HomeActivity homeActivity = (HomeActivity) context;
                switch (position) {
                    case 0:
                        homeActivity.startActivity(new Intent(homeActivity, AdminActivity.class));
                        break;

                    case 1:
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            ExportDialog dialog = new ExportDialog(context);
                            dialog.show();
                        } else {
                            ActivityCompat.requestPermissions(((Activity)context), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, getTargetRequestCode());
                        }
                        break;

                    case 2:
                        homeActivity.showFileChooser();
                        break;

                    case 3:
                        ConfirmDialog dialog = new ConfirmDialog(context, utils.getString(R.string.wipe_data), utils.getString(R.string.confirmation_wipe));
                        dialog.show();
                        dialog.onCancel(dialog.buttonCancel.getId());

                        dialog.buttonOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                database.wipeData();
                                Activity activity = (Activity) context;
                                startActivity(new Intent(activity, SplashActivity.class));
                                activity.finish();
                            }
                        });
                        break;
                }
            }
        });
        return view;
    }

    static class ListAdapter extends BaseAdapter {
        Context context;
        int[] images;
        String[] texts;
        LayoutInflater inflater;

        public ListAdapter(Context context, int[] images, String[] texts) {
            this.context = context;
            this.images = images;
            this.texts = texts;
            this.inflater = (LayoutInflater.from(context));
        }

        @Override
        public int getCount() {
            return texts.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            view = inflater.inflate(R.layout.item_menu, null);
            ImageView imageView = view.findViewById(R.id.item_icon);
            imageView.setImageResource(images[position]);
            TextView title = view.findViewById(R.id.title);
            title.setText(texts[position]);
            return view;
        }
    }
}