package com.fh.shop.api.cate.controller;

import com.fh.common.ServerResponse;
import com.fh.shop.api.cate.biz.ICateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/cates")
@Slf4j
public class CateController {

    @Resource(name="cateService")
    private ICateService cateService;

    @Value("${server.port}")
    private String port;

    @GetMapping
    private ServerResponse find(){
        log.info("端口信息"+port);
        return cateService.findList();
    }


}
