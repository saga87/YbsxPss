package com.ning.ybsxpss.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fxn on 2017/9/26.
 */

public class WaitDisposeObj implements Serializable {
    private long currentPaperNo;//当前页
    private List<WaitDisposeList> list;//该页数据
    private long paper_nums;//总页数
    private long allRecordNums;//所有记录数

    public long getAllRecordNums() {
        return allRecordNums;
    }

    public void setAllRecordNums(long allRecordNums) {
        this.allRecordNums = allRecordNums;
    }

    public long getCurrentPaperNo() {
        return currentPaperNo;
    }

    public void setCurrentPaperNo(long currentPaperNo) {
        this.currentPaperNo = currentPaperNo;
    }

    public List<WaitDisposeList> getList() {
        return list;
    }

    public void setList(List<WaitDisposeList> list) {
        this.list = list;
    }

    public long getPaper_nums() {
        return paper_nums;
    }

    public void setPaper_nums(long paper_nums) {
        this.paper_nums = paper_nums;
    }
}
