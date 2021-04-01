package com.sqber.weibotest.service;

import com.sqber.weibotest.dao.FileDao;
import com.sqber.weibotest.db.PagedResponse;
import com.sqber.weibotest.model.MyFile;
import com.sqber.weibotest.query.person.FileQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileDao fileDao;

    // 增
    public int add(String name, String serverPath) throws SQLException {
        return fileDao.add(name, serverPath);
    }

    // 删

    // 改

    public void updateState(Long id, Integer state, String info, String picId) throws SQLException {
        fileDao.updateState(id,state,info,picId);
    }

    // 查
    public List<MyFile> getNeedHandle() throws Exception {
        return fileDao.getNeedHandle();
    }

    public PagedResponse<MyFile> get(FileQuery query) throws Exception {
        return fileDao.get(query);
    }


}
