package com.sqber.weibotest.model;

import java.util.Date;

public class MyFile {

    public static final Integer WeiboSyncState_NOHand = 0;
    public static final Integer WeiboSyncState_SUCCESS = 1;
    public static final Integer WeiboSyncState_FAIL = 2;

    private Long id;
    private String name;
    private String serverPath;
    private Integer weiboSyncState;
    private String weiboSyncMsg;
    private String weiboId;
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServerPath() {
        return serverPath;
    }

    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
    }

    public Integer getWeiboSyncState() {
        return weiboSyncState;
    }

    public void setWeiboSyncState(Integer weiboSyncState) {
        this.weiboSyncState = weiboSyncState;
    }

    public String getWeiboSyncMsg() {
        return weiboSyncMsg;
    }

    public void setWeiboSyncMsg(String weiboSyncMsg) {
        this.weiboSyncMsg = weiboSyncMsg;
    }

    public String getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(String weiboId) {
        this.weiboId = weiboId;
    }
}
