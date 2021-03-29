package com.sqber.weibotest.db;

import java.util.List;

public class PagedResponse<T> {
    public List<T> list;
    public int currentPage;
    public int totalPage;
    public long totalCount;
    public int pageSize;

    public PagedResponse() {
    }

//    public PagedResponse(PageInfo pageInfo,List list) {
//        this.list = list;
//        this.currentPage = pageInfo.getPageNum();
//        this.pageSize = pageInfo.getPageSize();
//        this.totalCount = pageInfo.getTotal();
//        this.totalPage = pageInfo.getPages();
//    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}
