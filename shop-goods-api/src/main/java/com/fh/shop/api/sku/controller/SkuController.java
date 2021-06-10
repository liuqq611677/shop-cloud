package com.fh.shop.api.sku.controller;

import com.fh.common.ServerResponse;
import com.fh.shop.api.sku.biz.ISkuService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/skus")
@CrossOrigin
public class SkuController {

    @Resource(name="skuService")
    private ISkuService skuService;

    @GetMapping("/findList")
    public ServerResponse findList(){
        return skuService.findRecommendNewProductList();
    }

}
