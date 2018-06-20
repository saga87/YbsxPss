package com.ning.ybsxpss.entity;

/**
 * Created by fxn on 2017/10/11.
 */

public class Province {
    private String dept_id;
    private String dept_name;
    private String dept_parent_id;

    public String getDept_id() {
        return dept_id;
    }

    public void setDept_id(String dept_id) {
        this.dept_id = dept_id;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getDept_parent_id() {
        return dept_parent_id;
    }

    public void setDept_parent_id(String dept_parent_id) {
        this.dept_parent_id = dept_parent_id;
    }
}
