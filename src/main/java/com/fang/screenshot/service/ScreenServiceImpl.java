package com.fang.screenshot.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fang.screenshot.utils.BaseResult;
import com.fang.screenshot.utils.FtpBean;
import com.fang.screenshot.utils.FtpUtil;
import com.fang.screenshot.utils.HttpUtil;
import javafx.concurrent.Task;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
@Service
public class ScreenServiceImpl  implements ScreenService {
    private static final Logger logger = LoggerFactory.getLogger(ScreenService.class);




    @Override
    public String coverimgPrint(String url) {

        String photoname = "";

        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");
//        System.setProperty("webdriver.chrome.driver", "E:\\chromedriver.exe");

        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("--headless");
        chromeOptions.setHeadless(Boolean.TRUE);

        //创建Chrome driver的实例
        WebDriver driver = new ChromeDriver(chromeOptions);



//        driver.manage().window().setSize(new org.openqa.selenium.Dimension(2160,2160));
        int height = driver.manage().window().getSize().getHeight();
        int width = driver.manage().window().getSize().getWidth();

        logger.info("長" + height + ",寬" + width);


        // 最大化浏览器
        driver.manage().window().maximize();


        height = driver.manage().window().getSize().getHeight();
        width = driver.manage().window().getSize().getWidth();
        logger.info("長" + height + ",寬" + width);

        driver.manage().window().fullscreen();
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(2160,2160));

//        String url = "https://js.soufunimg.com/upload/ditu/a.html?longitude=" + longitude + "&latitude=" + latitude + "&zoom=18&" + p;
        //打开百度首页
        driver.navigate().to(url);


        height = driver.manage().window().getSize().getHeight();
        width = driver.manage().window().getSize().getWidth();
        logger.info("長" + height + ",寬" + width);

        try{
            Thread.sleep(6000);
            File screenshotAs = ((ChromeDriver) driver).getScreenshotAs(OutputType.FILE);
            photoname = this.nameFile();
            FileUtils.copyFile(screenshotAs,new File("E:/ " + photoname + ".jpg"));
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            Thread.sleep(5000);
        }catch(Exception e){
            e.printStackTrace();
        }



        //关闭浏览器窗口
        driver.quit();


        return photoname;
    }

    /**
     * 為文件命名
     * @return
     */
    private String nameFile() {
        SimpleDateFormat sdfms = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String date = sdfms.format(System.currentTimeMillis());
        //添加三位自动生成的数字，防止重复
        int i = (int) (Math.random() * 900) + 100;
        String fileName = date + i + ".jpg";
        return fileName;
    }


}

