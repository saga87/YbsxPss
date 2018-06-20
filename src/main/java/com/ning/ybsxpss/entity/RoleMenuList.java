package com.ning.ybsxpss.entity;

import java.io.Serializable;

/**
 * Created by fxn on 2017/10/20.
 */

public class RoleMenuList implements Serializable{
    private String menu_name;
    private String menu_url;

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getMenu_url() {
        return menu_url;
    }

    public void setMenu_url(String menu_url) {
        this.menu_url = menu_url;
    }
}
