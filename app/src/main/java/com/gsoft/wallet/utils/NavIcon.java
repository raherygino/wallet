package com.gsoft.wallet.utils;

public enum NavIcon {
    HOME("ic_home", "ic_home_rounded_32"),
    TRANSACTION("ic_calendar", "ic_calendar_outline"),
    PROJECT("ic_bell", "ic_bell_outline"),
    SETTINGS("ic_account", "ic_account_outline");
    public final String active;
    public final String inactive;
    private NavIcon(String active, String inactive) {
        this.active = active;
        this.inactive = inactive;
    }
}
