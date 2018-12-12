package com.iustu.common.pojo;

import java.util.List;

public class MyResult {
    private long total;
    private List<?> rows;

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
