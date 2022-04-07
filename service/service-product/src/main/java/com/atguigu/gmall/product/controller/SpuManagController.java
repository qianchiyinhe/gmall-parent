package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseSaleAttr;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SpuInfo;
import com.atguigu.gmall.product.service.BaseManageService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/product")
public class SpuManagController {
    @Autowired
    private BaseManageService baseManageService;
    /**
     *  // http://47.93.148.192:8000/mock/203/admin/product/list/{page}/{limit}
     *  spv分页列表
     * @page page
     * @size size
     * @return
     */
    @GetMapping("/{page}/{size}")
    public Result getSpvListPage(
            @PathVariable Long page,
            @PathVariable Long size,
            SkuInfo skuInfo
    ){
        //SkuINfo skuInfo用Long category3Id也可
        Page<SpuInfo> spuInfoPage = new Page<>(page, size);
      IPage<SpuInfo> spuInfoIpage = this.baseManageService.getSpuListByPage(spuInfoPage,skuInfo);
return Result.ok(spuInfoIpage);
    }
/**
 * 获取销售属性数据
 * /admin/product/baseSaleAttrList
 *
 */
@GetMapping("/baseSaleAttrList")
    public Result baseSaleAttrList(){
    List<BaseSaleAttr> baseSaleAttrList = this.baseManageService.baseSaleAttrList();
    return Result.ok(baseSaleAttrList);
}
/**保存spu
 * /admin/product/saveSpuInfo
 */
@PostMapping("/saveSpuInfo")
    public Result saveSpuInfo(@RequestBody SpuInfo spuInfo){
this.baseManageService.saveSpuInfo(spuInfo);
return Result.ok();
}
}
