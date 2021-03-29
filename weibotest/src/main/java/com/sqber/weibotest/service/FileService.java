package com.sqber.weibotest.service;

import com.sqber.weibotest.dao.FileDao;
import com.sqber.weibotest.model.MyFile;
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


}
