package com.ning.ybsxpss.entity;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fxn on 2018/1/15.
 */

public class JsonBean implements IPickerViewData,Serializable{

    private String imgUrl;
    private String classificationId;
    private String parentId;
    private int sort;
    private String classificationName;
    private List<ChildrenBeanX> children;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(String classificationId) {
        this.classificationId = classificationId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getClassificationName() {
        return classificationName;
    }

    public void setClassificationName(String classificationName) {
        this.classificationName = classificationName;
    }

    public List<ChildrenBeanX> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBeanX> children) {
        this.children = children;
    }


    public static class ChildrenBeanX {

        private String imgUrl;
        private String classificationId;
        private String parentId;
        private int sort;
        private String classificationName;
        private List<ChildrenBean> children;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getClassificationId() {
            return classificationId;
        }

        public void setClassificationId(String classificationId) {
            this.classificationId = classificationId;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getClassificationName() {
            return classificationName;
        }

        public void setClassificationName(String classificationName) {
            this.classificationName = classificationName;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean {

            private String imgUrl;
            private String classificationId;
            private String parentId;
            private int sort;
            private String classificationName;
            private boolean isextend;

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getClassificationId() {
                return classificationId;
            }

            public void setClassificationId(String classificationId) {
                this.classificationId = classificationId;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public String getClassificationName() {
                return classificationName;
            }

            public void setClassificationName(String classificationName) {
                this.classificationName = classificationName;
            }

            public boolean isIsextend() {
                return isextend;
            }

            public void setIsextend(boolean isextend) {
                this.isextend = isextend;
            }
        }
    }

    @Override
    public String getPickerViewText() {
        return classificationName;
    }
}
