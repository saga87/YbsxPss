package com.ning.ybsxpss.entity;

/**
 * Created by fxn on 2017/9/28.
 */

public class SourceGoodsList {
    private String classificationId;
    private String classificationName;
    private String isFillSource;
    private String goodsId;

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

    public String getClassificationName() {
        return classificationName;
    }

    public void setClassificationName(String classificationName) {
        this.classificationName = classificationName;
    }

    public String getIsFillSource() {
        return isFillSource;
    }

    public void setIsFillSource(String isFillSource) {
        this.isFillSource = isFillSource;
    }
}
