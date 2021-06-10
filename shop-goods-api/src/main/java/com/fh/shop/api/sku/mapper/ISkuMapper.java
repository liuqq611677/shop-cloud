package com.fh.shop.api.sku.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.sku.po.Sku;

import java.util.List;

public interface ISkuMapper extends BaseMapper<Sku> {
    List<Sku> findRecommendNewProductList();
}
