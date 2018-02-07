package com.mkalita.utils;

import java.util.Date;

public class DateRange {
    private Date startDate = null;
    private boolean startIncluded = false;
    private Date endDate = null;
    private boolean endIncluded = false;

    public DateRange(Date startDate, Date endDate) {
        this(startDate, false, endDate, false);
    }

    public DateRange(Date startDate, boolean startIncluded, Date endDate, boolean endIncluded) {
        this.setStartDate(startDate);
        this.startIncluded = startIncluded;
        this.setEndDate(endDate);
        this.endIncluded = endIncluded;
    }

    public Date getStartDate() {
        return new Date(startDate.getTime());
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate != null ? new Date(startDate.getTime()) : null;
    }

    public Date getEndDate() {
        return new Date(endDate.getTime());
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate != null ? new Date(endDate.getTime()) : null;
    }

    public boolean isStartIncluded() {
        return  startDate != null && startIncluded;
    }

    public void setStartIncluded(boolean startIncluded) {
        this.startIncluded = startIncluded;
    }

    public boolean isEndIncluded() {
        return endDate != null && endIncluded;
    }

    public void setEndIncluded(boolean endIncluded) {
        this.endIncluded = endIncluded;
    }
}
