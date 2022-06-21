package com.fang.screenshot.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;

@Slf4j
@Service
public class ScreenServiceImpl  implements ScreenService {
    private static final Logger logger = LoggerFactory.getLogger(ScreenService.class);




    @Override
    public String coverimgPrint(String screenurl) {

        if(screenurl.contains("?")){
            screenurl.replace("?","%3F");
        }
        if(screenurl.contains("&")){
            screenurl.replace("&","%26");
        }
        String url = screenurl ;
        String photoname = "";

        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
//        chromeOptions.setHeadless(Boolean.TRUE);
//
//        chromeOptions.addArguments("--enable-gpu-memory-buffer-compositor-resources");
//        chromeOptions.addArguments("--no-sandbox");
//        chromeOptions.addArguments("--disable-gpu");
//        chromeOptions.addArguments("--incognito");
//        chromeOptions.addArguments("--allow-running-insecure-content");

        //创建Chrome driver的实例
        WebDriver driver = new ChromeDriver(chromeOptions);



        int height = driver.manage().window().getSize().getHeight();
        int width = driver.manage().window().getSize().getWidth();

        logger.info("長" + height + ",寬" + width);


        // 最大化浏览器
        driver.manage().window().maximize();

        driver.manage().window().setSize(new org.openqa.selenium.Dimension(1500,1000));

        //打开百度首页
        driver.navigate().to(url);

        height = driver.manage().window().getSize().getHeight();
        width = driver.manage().window().getSize().getWidth();
        logger.info("長" + height + ",寬" + width);


        try{
            Thread.sleep(2000);
            File screenshotAs = ((ChromeDriver) driver).getScreenshotAs(OutputType.FILE);
            photoname = this.nameFile();
            FileUtils.copyFile(screenshotAs,new File("E:/" + photoname ));
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            Thread.sleep(2000);
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

