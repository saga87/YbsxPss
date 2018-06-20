package com.ning.ybsxpss.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fxn on 2017/9/26.
 */

public class WaitDisposeList implements Serializable{
    private String addtime;
    private String orderNo;
    private String timeRange;
    private String status;
    private String address;
    private double  money;
    private List<FoodStyle> detaillist;
    private String distributionDate;
    private String companyName;
    private String orderId;

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public List<FoodStyle> getDetaillist() {
        return detaillist;
    }

    public void setDetaillist(List<FoodStyle> detaillist) {
        this.detaillist = detaillist;
    }

    public String getDistributionDate() {
        return distributionDate;
    }

    public void setDistributionDate(String distributionDate) {
        this.distributionDate = distributionDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
