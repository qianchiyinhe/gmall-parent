package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseTrademark;
import com.atguigu.gmall.product.service.BaseTrademarkService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/product/baseTrademark")
public class BaseTrademarkController {

@Autowired
private BaseTrademarkService baseTrademarkService;
    /**分页查询品牌列表
     * /admin/product/baseTrademark/{page}/{limit}
     */
    @GetMapping("/{page}/{size}")
    public Result BaseTradeMarkController(
            @PathVariable Long page,
            @PathVariable Long size
    ){
        Page<BaseTrademark> baseTrademarkPage = new Page<>(page, size);
        IPage<BaseTrademark> baseTrademarkIPage =this.baseTrademarkService.getBaseTradeMarkListByPage(baseTrademarkPage);
        return Result.ok(baseTrademarkIPage);

    }
/**获取品牌路径
 * /admin/product/baseTrademark/get/{id}
 * 删除
 * /admin/product/baseTrademark/remove/{id}
 * 修改
 * /admin/product/baseTrademark/update
 * 保存
 * /admin/product/baseTrademark/save
 */
    /**
     * 根据品牌ID查询品牌对象
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public Result getById(@PathVariable Long id){
    BaseTrademark byId = this.baseTrademarkService.getById(id);
    return Result.ok(byId);

}
/**修改品牌
 * json对象类型用@RequestBody
 *  /admin/product/baseTrademark/update
 */


    @PutMapping("/update")
    public Result update(@RequestBody BaseTrademark baseTrademark){
        this.baseTrademarkService.updateById(baseTrademark);
        return Result.ok();
    }
    /**
     * 删除品牌
     * /admin/product/baseTrademark/remove/{id}
     */
@DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Long id){
    this.baseTrademarkService.removeById(id);
    return Result.ok();
}
/**
 * 保存品牌
 * /admin/product/baseTrademark/save
 */
@PostMapping("/save")
    public Result save(@RequestBody BaseTrademark baseTrademark){
  this.baseTrademarkService.save(baseTrademark);
    return Result.ok();

}


}
