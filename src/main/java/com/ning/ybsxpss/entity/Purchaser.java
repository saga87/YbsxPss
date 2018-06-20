package com.ning.ybsxpss.entity;

/**
 * Created by fxn on 2017/9/28.
 */

public class Purchaser {
    private String purchaserId;
    private String companyName;
    private String area;
    private String addr;
    private String check_enable;

    public String getPurchaserId() {
        return purchaserId;
    }

    public void setPurchaserId(String purchaserId) {
        this.purchaserId = purchaserId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCheck_enable() {
        return check_enable;
    }

    public void setCheck_enable(String check_enable) {
        this.check_enable = check_enable;
    }
}
