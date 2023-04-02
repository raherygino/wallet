package com.gsoft.wallet.model.models;

public class Column {
    public String name;
    public String type;

    public Column(String name,String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
