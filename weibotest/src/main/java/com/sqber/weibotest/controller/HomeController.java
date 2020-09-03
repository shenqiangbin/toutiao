package com.sqber.weibotest.controller;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class HomeController {


    private ChromeDriver driver;

    @ResponseBody
    @GetMapping("/")
    public String index() {
        return "启动成功了";
    }

    @ResponseBody
    @GetMapping("/start")
    public void start() {
        System.setProperty("webdriver.chrome.driver", "/Users/adminqian/chromedriver");

        ChromeOptions chromeOptions = new ChromeOptions();
//chromeOptions.addArguments("--headless");
//chromeOptions.addArguments("--window-size=1280,768");

        driver = new ChromeDriver(chromeOptions);
        driver.get("https://photo.weibo.com/upload/index?prel=p5_1#3500590076519405");
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
        file = "/Users/adminqian/my/mzitu/9d52c073gw1egv717pw8uj20jg0t6tcv.jpg";
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

        //return str;
        String pic = "https://wx4.sinaimg.cn/mw690/" + str + ".jpg";
        return pic;
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

}
