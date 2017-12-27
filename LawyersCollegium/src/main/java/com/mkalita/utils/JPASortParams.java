package com.mkalita.utils;

public class JPASortParams {
    private String orderField;
    private boolean ascending;
    private int maxResult;
    private int offset;

    public JPASortParams(final String orderField, final boolean ascending, final int maxResult, final int offset) {
        this.orderField = orderField;
        this.ascending = ascending;
        this.maxResult = maxResult;
        this.offset = offset;
    }

    public JPASortParams(final String orderField, final boolean ascending, final int maxResult) {
        this.orderField = orderField;
        this.ascending = ascending;
        this.maxResult = maxResult;
        this.offset = 0;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
