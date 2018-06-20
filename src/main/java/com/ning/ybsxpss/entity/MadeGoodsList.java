package com.ning.ybsxpss.entity;

/**
 * Created by fxn on 2017/10/13.
 */

public class MadeGoodsList {
    private String madegoodsId;
    private String goodsId;
    private String classificationId;
    private String goodsName;
    private String goodsUnit;
    private double price;
    private String status;
    private String imgUrl;
    private String beSelected;
    private String pri;

    public String getPri() {
        return pri;
    }

    public void setPri(String pri) {
        this.pri = pri;
    }

    public String getMadegoodsId() {
        return madegoodsId;
    }

    public void setMadegoodsId(String madegoodsId) {
        this.madegoodsId = madegoodsId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(String classificationId) {
        this.classificationId = classificationId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getBeSelected() {
        return beSelected;
    }

    public void setBeSelected(String beSelected) {
        this.beSelected = beSelected;
    }
}
