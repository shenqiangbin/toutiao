package com.sqber.weibotest.controller;

import com.alibaba.fastjson.JSON;
import com.sqber.weibotest.base.DateUtil;
import com.sqber.weibotest.base.FileHelper;
import com.sqber.weibotest.base.SiteMapUtil;
import com.sqber.weibotest.config.ChromeDriverConf;
import com.sqber.weibotest.config.FileUploadConfig;
import com.sqber.weibotest.config.WeiboConf;
import com.sqber.weibotest.db.DBHelper;
import com.sqber.weibotest.db.IResultHandler;
import com.sqber.weibotest.model.Article;
import com.sqber.weibotest.model.MyCookie;
import com.sqber.weibotest.model.MyFile;
import com.sqber.weibotest.service.FileService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Controller
public class SiteMapController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("sqliteDB")
    private DBHelper sqliteHelper;
    @Autowired
    private FileUploadConfig fileUploadConfig;

    @Autowired
    private DBHelper dbHelper;


    @ResponseBody
    @GetMapping("/sitemap")
    public String index() throws Exception {
//        String sql2 = "select * from file";
//        List<MyFile> files = dbHelper.simpleQuery(sql2, null, MyFile.class);
//        for(MyFile file : files){
//            //System.out.println(file.getCreateTime().toString());
//            System.out.println(DateUtil.format(file.getCreateTime()));
//            String val = DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT.format(file.getCreateTime());
//            System.out.println(val);
//        }

        String sql = "select * from Article where publishStatus = 1 and enable = 1";
        List<Article> list = sqliteHelper.simpleQuery(sql, null, Article.class);

        SiteMapUtil siteMapUtil = new SiteMapUtil();
        siteMapUtil.addSitemapUrl("http://www.sqber.com", "1.0", "", SiteMapUtil.DAILY);
        siteMapUtil.addSitemapUrl("http://www.sqber.com/friendlylink", "0.9", "", SiteMapUtil.MONTHLY);

        for (Article article : list) {
            //System.out.println(article.getUrlTitle());
            //System.out.println(DateUtil.format(article.getUpdateTime()));
            String updateTime = DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT.format(article.getUpdateTime());
            siteMapUtil.addSitemapUrl("http://www.sqber.com/articles/" + article.getUrlTitle() + ".html", "0.8", updateTime, SiteMapUtil.WEEKLY);
        }

        String content = siteMapUtil.getSiteMapContent();
        siteMapUtil.writeTxtFile(content, Paths.get(fileUploadConfig.getSitemapPath(), "sitemap.xml").toString(), false, false);

        return "done";
    }

}
