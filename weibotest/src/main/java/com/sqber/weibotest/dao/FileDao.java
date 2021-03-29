package com.sqber.weibotest.dao;

import com.sqber.weibotest.db.DBHelper;
import com.sqber.weibotest.db.ParamsHelper;
import com.sqber.weibotest.model.MyFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;


@Repository
public class FileDao {

    @Autowired
    private DBHelper dbHelper;

    public int add(String name, String serverPath) throws SQLException {

        Object[] arr = new Object[]{
                name,
                serverPath
        };

        String sql = String.format(
                "insert file(name,serverPath) values(%s)",
                ParamsHelper.createStr(arr.length));

        return dbHelper.add(sql, arr);
    }

    /**
     * 获取需要处理的文件（获取10个）
     *
     * @return
     * @throws Exception
     */
    public List<MyFile> getNeedHandle() throws Exception {
        String sql = "select * from file where weiboSyncState = " + MyFile.WeiboSyncState_NOHand + " limit 10";
        return dbHelper.simpleQuery(sql, null, MyFile.class);
    }

    public void updateState(Long id, Integer state, String info, String picId) throws SQLException {
        String sql = "update file set weiboSyncState = ?,weiboSyncMsg=?,weiboId=? where id = ?";
        dbHelper.update(sql, new Object[]{state, info, picId, id});
    }
}
