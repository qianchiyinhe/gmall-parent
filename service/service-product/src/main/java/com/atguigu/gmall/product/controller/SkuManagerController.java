package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SpuImage;
import com.atguigu.gmall.model.product.SpuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.product.service.BaseManageService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/product")
public class SkuManagerController {
    @Autowired
    private BaseManageService baseManageService;
    /**根据spuId 查询销售属性
     * /admin/product/spuSaleAttrList/{spuId}
     */
    @GetMapping("/spuSaleAttrList/{spuId}")
    public Result spuSaleAttrList(@PathVariable Long spuId){
        List<SpuSaleAttr> spuSaleAttrList =this.baseManageService.spuSaleAttrList(spuId);
        return Result.ok(spuSaleAttrList);
    }
/**
 * /admin/product/spuImageList/{spuId}
 * 根据spuId 获取spuImage 集合
 */
@GetMapping("/spuImageList/{spuId}")
    public Result spuImageList (@PathVariable Long spuId){
    List<SpuImage> spuImageList = this.baseManageService.spuImageList(spuId);
return Result.ok(spuImageList);
}
/**
 * 保存SkuInfo
 * /admin/product/saveSkuInfo
 */
@PostMapping("/saveSkuInfo")
    public Result saveSkuInfo(@RequestBody SkuInfo skuInfo){
    //调用service
    this.baseManageService.saveSkuInfo(skuInfo);
    return Result.ok();
}
/**
 * sku分页列表
 * /admin/product/list/{page}/{limit}
 */
@GetMapping("/list/{page}/{limit}")
    public Result list(@PathVariable Long page,
                       @PathVariable Long limit)
{
    //封装查询对象
    Page<SkuInfo> skuInfoPage = new Page<>(page,limit);
    IPage<SkuInfo> skuInfoIPage =this.baseManageService.getSkuInfoListByPage(skuInfoPage);
    return Result.ok(skuInfoIPage);
}
/**
 * 下架
 * /admin/product/cancelSale/{skuId}
 */
@GetMapping("/cancelSale/{skuId}")
    public Result xiaJia(@PathVariable Long skuId){
    this.baseManageService.xiajia(skuId);
    return Result.ok();
}
/**
 * 上架
 * /admin/product/onSale/{skuId}
 */
@GetMapping("/onSale/{skuId}")
    public Result shangJia(@PathVariable Long skuId){
    this.baseManageService.shangJia(skuId);
    return Result.ok();
}
}
