package com.ning.ybsxpss.entity;

import java.util.List;

/**
 * Created by fxn on 2017/9/28.
 */

public class PickingListObj {
     private String classificationId;
    private String classificationName;
    private double sum_amount;
    private String goodsUnit;
    private String goodsName;
    private List<PickingList> goodlist;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(String classificationId) {
        this.classificationId = classificationId;
    }

    public String getClassificationName() {
        return classificationName;
    }

    public void setClassificationName(String classificationName) {
        this.classificationName = classificationName;
    }

    public double getSum_amount() {
        return sum_amount;
    }

    public void setSum_amount(double sum_amount) {
        this.sum_amount = sum_amount;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public List<PickingList> getGoodlist() {
        return goodlist;
    }

    public void setGoodlist(List<PickingList> goodlist) {
        this.goodlist = goodlist;
    }
}
