package com.gsoft.wallet.model.models;

public class Deposit {
    private final int id;
    private final int idProject;
    private final int idTransaction;

    public  Deposit(int id, int idProject, int idTransaction) {
        this.id = id;
        this.idProject = idProject;
        this.idTransaction = idTransaction;
    }

    public int getId() {
        return id;
    }

    public int getIdProject() {
        return idProject;
    }

    public int getIdTransaction() {
        return idTransaction;
    }
}
