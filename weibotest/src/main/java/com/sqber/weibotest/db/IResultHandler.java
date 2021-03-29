package com.sqber.weibotest.db;

import java.sql.ResultSet;

public interface IResultHandler {
    void handle(ResultSet resultSet) throws Exception;

    boolean isSuccess();
}

