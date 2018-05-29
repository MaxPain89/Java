package com.hystax.demo.wire;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class CalculateResponse implements Serializable {
    private List<WireBook> books;
    private boolean isCacheUsed;
    private long duration;
    private String sqlRequest;

    @JsonCreator
    public CalculateResponse(@JsonProperty("books") List<WireBook> books,
                             @JsonProperty("isCacheUsed") Boolean isCacheUsed,
                             @JsonProperty("duration") Long duration,
                             @JsonProperty("sqlRequest") String sqlRequest) {
        this.books = books;
        this.isCacheUsed = isCacheUsed;
        this.duration = duration;
        this.sqlRequest = sqlRequest;
    }

    public List<WireBook> getBooks() {
        return books;
    }

    public void setBooks(List<WireBook> books) {
        this.books = books;
    }

    public boolean isCacheUsed() {
        return isCacheUsed;
    }

    public void setCacheUsed(boolean cacheUsed) {
        isCacheUsed = cacheUsed;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getSqlRequest() {
        return sqlRequest;
    }

    public void setSqlRequest(String sqlRequest) {
        this.sqlRequest = sqlRequest;
    }
}
