package com.sqber.weibotest.controller;

import com.sqber.weibotest.config.ChromeDriverConf;
import com.sqber.weibotest.dao.FileDao;
import com.sqber.weibotest.model.MyFile;
import com.sqber.weibotest.service.FileService;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

@Controller
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private ChromeDriver driver;
    private boolean hasLogin = false;

    @Autowired
    private ChromeDriverConf chromeDriverConf;
    @Autowired
    private FileService fileService;

    @ResponseBody
    @GetMapping("/")
    public String index() {
        return "启动成功了";
    }


    @ResponseBody
    @GetMapping("/start")
    public String start() {
        System.setProperty("webdriver.chrome.driver", chromeDriverConf.getPath());

        ChromeOptions chromeOptions = new ChromeOptions();
//chromeOptions.addArguments("--headless");
//chromeOptions.addArguments("--window-size=1280,768");

        driver = new ChromeDriver(chromeOptions);
        driver.get("https://photo.weibo.com/upload/index?prel=p5_1#3500590076519405");

        return "chrome driver 初始化成功";
    }

    @ResponseBody
    @GetMapping("/haslogin")
    public String hasLogin() {
        this.hasLogin = true;
        return "ok";
    }

    @Scheduled(fixedRate = 1000 * 2)
    public void sync() {
        log.info("task1 run");
//        for(int i=0; i< 10; i++){
//            log.info("task1 count:" + i);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        try {
            if (driver == null) {
                log.info("driver 未初始化");
            } else if (!hasLogin) {
                log.info("还未登录");
            } else {
                this.db();
            }
        } catch (Exception e) {
            log.error("error:{}", e);
        }
        log.info("task1 run end");
    }

    /**
     * 将数据库中待同步的文件同步到微博相册
     *
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("/browser/db")
    public String db() throws Exception {
        List<MyFile> list = fileService.getNeedHandle();
        for (MyFile item : list) {

            try {
                String picId = this.go(item.getServerPath());
                fileService.updateState(item.getId(), MyFile.WeiboSyncState_SUCCESS, "成功", picId);
            } catch (Exception e) {
                fileService.updateState(item.getId(), MyFile.WeiboSyncState_FAIL, e.getMessage(), "");
            }
            //String pic = "https://wx4.sinaimg.cn/mw690/" + str + ".jpg";
            //String pic = "https://wx4.sinaimg.cn/large/8e2ef8f7gy1gp171tmfldj20go0l21kx.jpg";
            //return pic;
        }
        return "ok";
    }

    @ResponseBody
    @GetMapping("browser/go")
    public String go(String file) {
        driver.navigate().refresh();
        driver.get("https://photo.weibo.com/upload/index?prel=p5_1#3500590076519405");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement element = findElement("a", "action-type", "go_common_upload");
        if (element != null)
            element.click();

        // 选择本地文件
        //file = "/Users/adminqian/my/mzitu/9d52c073gw1elar8gic2vj20g42qp49v.jpg";
        driver.findElementByName("pic1").sendKeys(file);

        element = findElement("a", "action-type", "comm_upload");
        if (element != null)
            element.click();


        // 触发提交之后，还需要等待一会才能得到结果
        String result = "";
        try {
            Thread.sleep(10000);
            result = getResult();
        } catch (Exception e) {
            try {
                Thread.sleep(10000);
                result = getResult();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        System.out.println(result);
        String str = result.substring(result.indexOf("pid\":") + 6, result.indexOf("\",\"pic"));

        return str;
        //System.out.println(pic);
    }

    private String getResult() {
        Object returnVal = driver.executeScript("return document.getElementsByName('uploadIframe1')[0].contentDocument.head.innerText");
        return returnVal.toString();
    }

    private WebElement findElement(String tag, String attr, String attrVal) {
        for (WebElement webElement : driver.findElementsByTagName(tag)) {
            String theAttr = webElement.getAttribute(attr);
            if (theAttr != null && theAttr.equals(attrVal)) {
                return webElement;
            }
        }
        return null;
    }

    @GetMapping("/weibo/pic")
    public void getPic(String url, HttpServletResponse response) throws IOException {
        // 盗链被屏蔽，则只能服务器先从对方那获取文件，然后在返回给浏览器。
        // 先从微博获取，获取失败，则从本地获取

        BufferedImage bufferedImage = ImageIO.read(new URL(url));
        ImageIO.write(bufferedImage, "jpg", response.getOutputStream());
    }
}
