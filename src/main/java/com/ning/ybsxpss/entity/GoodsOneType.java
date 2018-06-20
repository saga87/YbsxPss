package com.ning.ybsxpss.entity;

import java.util.List;

/**
 * Created by fxn on 2017/10/9.
 */

public class GoodsOneType {
    private String id;
    private String name;
    private String imgUrl;
    private List<GoodsTwoType> childlist;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<GoodsTwoType> getChildlist() {
        return childlist;
    }

    public void setChildlist(List<GoodsTwoType> childlist) {
        this.childlist = childlist;
    }
}
