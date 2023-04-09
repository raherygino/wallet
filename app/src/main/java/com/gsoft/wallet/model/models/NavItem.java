package com.gsoft.wallet.model.models;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NavItem {
    private final TextView titleTextView;
    private final String title;
    private final ImageView imageView;
    private final LinearLayout view;
    private final String icon;

    public NavItem(LinearLayout linearLayout, String icon) {
        this.view = linearLayout;
        this.icon = icon;
        this.imageView = (ImageView) linearLayout.getChildAt(0);
        this.titleTextView = (TextView) linearLayout.getChildAt(1);
        this.title = this.titleTextView.getText().toString();
    }

    public String getIcon() {
        return icon;
    }

    public LinearLayout getView() {
        return view;
    }

    public TextView getTitleTextView() {
        return titleTextView;
    }

    public String getTitle() {
        return title;
    }

    public ImageView getImageView() {
        return imageView;
    }
}
