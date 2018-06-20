package com.ning.ybsxpss.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fxn on 2017/9/26.
 */

public class OrderDetailsObj implements Serializable {
    private String addrId;
    private String timeRange;
    private String distributionPhone;
    private String status;
    private String addr_area;
    private List<DetailList> detailList;
    private String purchaserId;
    private String distributionName;
    private String addtime;
    private String orderNo;
    private String distributionAddr;
    private String address;
    private Purchaser purchaserInfo;
    private String addr_phone;
    private double money;
    private String distributionDate;
    private String addr_name;
    private String orderId;
    private String addUser;

    public String getAddUser() {
        return addUser;
    }

    public void setAddUser(String addUser) {
        this.addUser = addUser;
    }

    public String getAddrId() {
        return addrId;
    }

    public void setAddrId(String addrId) {
        this.addrId = addrId;
    }

    public String getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
    }

    public String getDistributionPhone() {
        return distributionPhone;
    }

    public void setDistributionPhone(String distributionPhone) {
        this.distributionPhone = distributionPhone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddr_area() {
        return addr_area;
    }

    public void setAddr_area(String addr_area) {
        this.addr_area = addr_area;
    }

    public List<DetailList> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<DetailList> detailList) {
        this.detailList = detailList;
    }

    public String getPurchaserId() {
        return purchaserId;
    }

    public void setPurchaserId(String purchaserId) {
        this.purchaserId = purchaserId;
    }

    public String getDistributionName() {
        return distributionName;
    }

    public void setDistributionName(String distributionName) {
        this.distributionName = distributionName;
    }

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

    public String getDistributionAddr() {
        return distributionAddr;
    }

    public void setDistributionAddr(String distributionAddr) {
        this.distributionAddr = distributionAddr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Purchaser getPurchaserInfo() {
        return purchaserInfo;
    }

    public void setPurchaserInfo(Purchaser purchaserInfo) {
        this.purchaserInfo = purchaserInfo;
    }

    public String getAddr_phone() {
        return addr_phone;
    }

    public void setAddr_phone(String addr_phone) {
        this.addr_phone = addr_phone;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getDistributionDate() {
        return distributionDate;
    }

    public void setDistributionDate(String distributionDate) {
        this.distributionDate = distributionDate;
    }

    public String getAddr_name() {
        return addr_name;
    }

    public void setAddr_name(String addr_name) {
        this.addr_name = addr_name;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
