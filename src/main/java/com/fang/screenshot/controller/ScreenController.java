package com.fang.screenshot.controller;

import com.fang.screenshot.service.ScreenService;
import com.fang.screenshot.utils.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/screen")
@Slf4j
public class ScreenController {

    @Autowired
    ScreenService screenService;


    @GetMapping("/coverimg")
    public BaseResult coverimg(@RequestParam(value = "url", required = true) String url){
        String photoname = screenService.coverimgPrint(url);
        String photourl = new String();

        if(StringUtils.isEmpty(photourl)){
            return BaseResult.error("獲取截圖地址失敗");
        }

        return BaseResult.success();
    }


}
