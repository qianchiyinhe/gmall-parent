package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseTrademark;
import com.atguigu.gmall.model.product.CategoryTrademarkVo;
import com.atguigu.gmall.product.service.BaseCategoryTrademarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/product/baseCategoryTrademark")
public class BaseCategoryTradeMarkController {
    @Autowired
    private BaseCategoryTrademarkService baseCategoryTrademarkService;
    /**
     * 根据category3Id获取品牌列表
     * /admin/product/baseCategoryTrademark/findTrademarkList/{category3Id}
     */
    @GetMapping("/findTrademarkList/{category3Id}")
    public Result findTrademarkList(@PathVariable Long category3Id){
 List<BaseTrademark> baseTrademarkList = this.baseCategoryTrademarkService.findTrademarkList(category3Id);
return Result.ok(baseTrademarkList);
    }
    /**删除分类品牌关联
     * /admin/product/baseCategoryTrademark/remove/{category3Id}/{trademarkId}
     */
    @DeleteMapping("/remove/{category3Id}/{trademarkId}")
    public Result removeBaseCategoryTradeMakr(@PathVariable Long category3Id,
                                              @PathVariable Long trademarkId
    ){
this.baseCategoryTrademarkService.removeBaseCategoryTradeMakr(category3Id,trademarkId);
        return Result.ok();
    }
    /**
     * 根据category3Id获取可选品牌列表
     * /admin/product/baseCategoryTrademark/findCurrentTrademarkList/{category3Id}
     */
    @GetMapping("/findCurrentTrademarkList/{category3Id}")
    public Result findCurrentTrademarkList(@PathVariable Long category3Id){
     List<BaseTrademark> baseTrademarkList=  this.baseCategoryTrademarkService.findCurrentTrademarkList(category3Id);
   return  Result.ok(baseTrademarkList);

    }
    /**
     * 保存分类品牌关联
     * /admin/product/baseCategoryTrademark/save
     */
    @PostMapping("/save")
    public Result saveBaseCategoryTradeMark(@RequestBody CategoryTrademarkVo categoryTrademarkVo){
        this.baseCategoryTrademarkService.saveBaseCategoryTradeMark(categoryTrademarkVo);
        return  Result.ok();
    }
}
