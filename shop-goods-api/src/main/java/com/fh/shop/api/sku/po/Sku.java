package com.fh.shop.api.sku.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel
public class Sku implements Serializable {
    @ApiModelProperty(hidden = true)
    private Long id;
    @ApiModelProperty(value = "商品id")
    private Long spuId;
    @ApiModelProperty(value = "商品名称")
    private String skuName;
    @ApiModelProperty(value = "商品价格")
    private BigDecimal price;
    @ApiModelProperty(value = "商品排序")
    private Integer stock;
    @ApiModelProperty(value = "商品信息")
    private String specInfo;
    @ApiModelProperty(value = "颜色id")
    private Long colorId;
    @ApiModelProperty(value = "图片信息")
    private String Image;
    //是否上架
    private Integer ifGrounding;

    //是否新品
    private Integer ifNewProduct;

    //是否推荐
    private Integer ifRecommend;

    private Integer sales;
}
