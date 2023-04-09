package com.gsoft.wallet.utils;

public enum NavIcon {
    HOME("ic_home", "ic_home_rounded_32"),
    TRANSACTION("ic_wallet_fill", "ic_wallet"),
    PROJECT("ic_chart_fill", "ic_chart"),
    PROFILE("ic_account", "ic_account_outline");
    public final String active;
    public final String inactive;
    private NavIcon(String active, String inactive) {
        this.active = active;
        this.inactive = inactive;
    }
}
