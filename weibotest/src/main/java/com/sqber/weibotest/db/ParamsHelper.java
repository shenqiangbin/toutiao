package com.sqber.weibotest.db;

import java.util.ArrayList;
import java.util.List;

public class ParamsHelper {
    public static String createStr(int size){
        List<String> params = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            params.add("?");
        }
        return String.join(",", params);
    }
}
