package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.product.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface BaseManageService {
//查询一级列表
    List<BaseCategory1> getCategory1();

    //查询二级列表
    List<BaseCategory2> getCategory2(Long category1Id);
    //查询三级列表
    List<BaseCategory3> getCategory3(Long category2Id);
    /** 根据属性ID获取集合属性
     * /admin/product/attrInfoList/{category1Id}/{category2Id}/{category3Id}
     */
    List<BaseAttrInfo> attrInfoLis(Long category1, Long category2Id, Long category3Id);

   /* *
     * 根据属性Id查询属性对象进行操作
     * @param attrId
     * @return*/

    BaseAttrInfo getAttrInfo(Long attrId);
   /* *
     * 根据属性id查询属性值列表
     * @param attrId
     * @return*/

    public  List<BaseAttrValue> getAttrValueList(Long attrId);
    /**保存修改平台属性
     * /admin/product/saveAttrInfo
     */
    void saveAttrInfo(BaseAttrInfo baseAttrInfo);
    /**
     *  // http://47.93.148.192:8000/mock/203/admin/product/list/{page}/{limit}
     *  spv分页列表
     * @page page
     * @size size
     * @return
     */
    IPage<SpuInfo> getSpuListByPage(Page<SpuInfo> skuInfoPage, SkuInfo skuInfo);

    /**
     * 获取销售属性数据
     * /admin/product/baseSaleAttrList
     *
     */
    List<BaseSaleAttr> baseSaleAttrList();
    /**保存spu
     * /admin/product/saveSpuInfo
     */
    void saveSpuInfo(SpuInfo spuInfo);
    /**根据spuId 查询销售属性
     * /admin/product/spuSaleAttrList/{spuId}
     */
    List<SpuSaleAttr> spuSaleAttrList(Long spuId);
    /**
     * /admin/product/spuImageList/{spuId}
     * 根据spuId 获取spuImage 集合
     */
    List<SpuImage> spuImageList(Long spuId);

    /**
     * 保存SkuInfo
     * /admin/product/saveSkuInfo
     */
    void saveSkuInfo(SkuInfo skuInfo);
    /**
     * sku分页列表
     * /admin/product/list/{page}/{limit}
     */
    IPage<SkuInfo> getSkuInfoListByPage(Page<SkuInfo> skuInfoPage);
    /**
     * 下架
     * /admin/product/cancelSale/{skuId}
     */
    void xiajia(Long skuId);
    /**
     * 上架
     * /admin/product/onSale/{skuId}
     */
    void shangJia(Long skuId);
}
