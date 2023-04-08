package com.gsoft.wallet.model.models;

import com.gsoft.wallet.R;

public class Project {

    private final String title;
    private final String type;
    private final int priority;
    private final int target;
    private final int deposit;
    private int id;

    public Project(String title, String type, int priority, int target, int deposit) {
        this.title = title;
        this.type = type;
        this.priority = priority;
        this.target = target;
        this.deposit = deposit;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isCompleted() {
        return target == deposit;
    }

    public int getRest() {
        return target - deposit;
    }

    public int colorPriority() {
        int color = 0;
        if (priority == 1) {
            color = R.drawable.border_style_danger;
        } else if (priority == 2) {
            color = R.drawable.border_style_warning;
        } else {
            color = R.drawable.border_style_success;
        }
        return color;
    }

    public int getTarget() {
        return target;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setId(int new_id) {
        this.id = new_id;
    }

    public int getId() {
        return id;
    }
}
