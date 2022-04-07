package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.atguigu.gmall.model.product.BaseCategory1;
import com.atguigu.gmall.model.product.BaseCategory2;
import com.atguigu.gmall.model.product.BaseCategory3;
import com.atguigu.gmall.product.service.BaseManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.List;

@RequestMapping("/admin/product")
@RestController
// @CrossOrigin  跨域
public class BaseManageController {
    @Autowired
    private BaseManageService baseManageService;
    /*
    * 获取一级目录
    * */
    @GetMapping("/getCategory1")
    public Result getCategory1(){
        //查询所有一级分类
      List<BaseCategory1>  baseCategory1List = baseManageService.getCategory1();
        return Result.ok(baseCategory1List);
    }
    /*根据一级分类/admin/product/getCategory2/{category1Id}
    * 查询二级分类
    * 路径传至
    * */

    @GetMapping("/getCategory2/{category1Id}")
    public Result getCategory2(@PathVariable("category1Id") Long category1Id){
    List<BaseCategory2> baseCategory2List =  this.baseManageService.getCategory2(category1Id);
        return Result.ok(baseCategory2List);
    }


    /**根据二级查三级
     *  // /admin/product/getCategory3/{category2Id}
     * @param category2Id
     * @return
     */
  @GetMapping("/getCategory3/{category2Id}")
  public Result getCategory3(@PathVariable("category2Id") Long category2Id){
      List<BaseCategory3> baseCategory3List =  this.baseManageService.getCategory3(category2Id);
      return Result.ok(baseCategory3List);
  }

    /** 根据属性ID获取集合属性
     * /admin/product/attrInfoList/{category1Id}/{category2Id}/{category3Id}
     */
    @GetMapping("/attrInfoList/{category1Id}/{category2Id}/{category3Id}")
    public Result attrInfoList(@PathVariable Long category1Id,
                               @PathVariable Long category2Id,
                               @PathVariable Long category3Id){


       List<BaseAttrInfo> infoList= this.baseManageService.attrInfoLis( category1Id, category2Id,
                category3Id);
return Result.ok(infoList);
    }

    /**
     * 根据属性Id查询属性值集合
     * @param attrId
     * @return
     * 问题：查询属性值时属性值是否存在
     *
     */
    @GetMapping("/getAttrValueList/{attrId}")
    public Result getAttrValueList(@PathVariable Long attrId){
       BaseAttrInfo baseAttrInfo = this.baseManageService.getAttrInfo(attrId);
       return Result.ok(baseAttrInfo.getAttrValueList());


}
    /**保存修改平台属性
     * /admin/product/saveAttrInfo
     */
    @PostMapping("/saveAttrInfo")
    public Result saveSkuInfo(@RequestBody BaseAttrInfo baseAttrInfo){
        this.baseManageService.saveAttrInfo(baseAttrInfo);
        return Result.ok();
        // 为什么不考虑失败,因为有全局异常处理;

    }

}
