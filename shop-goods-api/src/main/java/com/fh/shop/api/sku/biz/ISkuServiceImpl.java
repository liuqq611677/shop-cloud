package com.fh.shop.api.sku.biz;

import com.alibaba.fastjson.JSON;
import com.fh.common.ServerResponse;
import com.fh.shop.api.sku.mapper.ISkuMapper;
import com.fh.shop.api.sku.po.Sku;
import com.fh.shop.api.sku.vo.SkuVo;
import com.fh.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("skuService")
@Transactional(rollbackFor = Exception.class)
public class ISkuServiceImpl implements ISkuService{

    @Autowired
    private ISkuMapper skuMapper;

    @Override
    @Transactional(readOnly = true)
    public ServerResponse findRecommendNewProductList() {

        String skuListJson = RedisUtil.get("skuList");
        if (StringUtils.isNotEmpty(skuListJson)){
            List<SkuVo> skuVos = JSON.parseArray(skuListJson, SkuVo.class);
            return ServerResponse.success(skuVos);
        }
        List<Sku> skuList = skuMapper.findRecommendNewProductList();
        List<SkuVo> skuVoList = skuList.stream().map(x -> {
            SkuVo skuVo = new SkuVo();
            skuVo.setId(x.getId());
            skuVo.setSkuName(x.getSkuName());
            skuVo.setPrice(x.getPrice().toString());
            skuVo.setImage(x.getImage());
            return skuVo;
        }).collect(Collectors.toList());
        RedisUtil.setex("skuList",JSON.toJSONString(skuVoList),30);
        return ServerResponse.success(skuVoList);
    }

}
