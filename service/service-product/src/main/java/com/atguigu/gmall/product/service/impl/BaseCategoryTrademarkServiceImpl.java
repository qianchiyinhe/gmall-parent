package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseCategoryTrademark;
import com.atguigu.gmall.model.product.BaseTrademark;
import com.atguigu.gmall.model.product.CategoryTrademarkVo;
import com.atguigu.gmall.product.mapper.BaseCategoryTrademarkMapper;
import com.atguigu.gmall.product.mapper.BaseTradeMarkMapper;
import com.atguigu.gmall.product.service.BaseCategoryTrademarkService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jodd.util.CollectionUtil;
import net.bytebuddy.asm.Advice;
import org.checkerframework.framework.qual.RequiresQualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("all")
public class BaseCategoryTrademarkServiceImpl extends ServiceImpl<BaseCategoryTrademarkMapper,BaseCategoryTrademark>implements  BaseCategoryTrademarkService {
   @Autowired
   private BaseCategoryTrademarkMapper baseCategoryTrademarkMapper;
   @Autowired
   private BaseTradeMarkMapper baseTradeMarkMapper;

    /**
     * 根据category3Id获取品牌列表
     * /admin/product/baseCategoryTrademark/findTrademarkList/{category3Id}
     */
   @Override
    public List<BaseTrademark> findTrademarkList(Long category3Id) {
       //根据分类ID查询分类品牌关联表查询关联的品牌ID
       QueryWrapper<BaseCategoryTrademark> queryWrapper = new QueryWrapper<>();
queryWrapper.eq("category3_id",category3Id);
       List<BaseCategoryTrademark> baseCategoryTrademarks = baseCategoryTrademarkMapper.selectList(queryWrapper);
       //获取关联id
if (!CollectionUtils.isEmpty(baseCategoryTrademarks )){
 List<Long>  baseTradeMarkIdList = baseCategoryTrademarks.stream().map(baseCategoryTrademark -> {
        return baseCategoryTrademark.getTrademarkId();
    }).collect(Collectors.toList());
    //根据品牌id查询查询
    List<BaseTrademark> baseTrademarkList = this.baseTradeMarkMapper.selectBatchIds(baseTradeMarkIdList);

    return baseTrademarkList;
}
      return null;

    }
    /**删除分类品牌关联
     * /admin/product/baseCategoryTrademark/remove/{category3Id}/{trademarkId}
     */
    @Override
    public void removeBaseCategoryTradeMakr(Long category3Id, Long trademarkId) {
        QueryWrapper<BaseCategoryTrademark> queryWrapper = new QueryWrapper<>();
queryWrapper.eq("category3_id", category3Id);
queryWrapper.eq("trademark_id", trademarkId);
queryWrapper.orderByAsc("id");

        this.baseCategoryTrademarkMapper.delete(queryWrapper);
    }
    /**
     * 根据category3Id获取可选品牌列表
     * /admin/product/baseCategoryTrademark/findCurrentTrademarkList/{category3Id}
     */
    @Override
    public List<BaseTrademark> findCurrentTrademarkList(Long category3Id) {
        // 根据category3Id 查询关联的品牌Id列表
        //根据分类id查询分类品牌关联的品牌ID
        QueryWrapper<BaseCategoryTrademark> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category3_id", category3Id);
        List<BaseCategoryTrademark> baseCategoryTrademarks = baseCategoryTrademarkMapper.selectList(queryWrapper);
        //获取已经关联的id
        if (!CollectionUtils.isEmpty(baseCategoryTrademarks)){
            baseCategoryTrademarks.stream().map(baseCategoryTrademark -> {
                return baseCategoryTrademark.getBaseTrademark();
            }).collect(Collectors.toList());
            //遍历
            //获取品牌列表
            /**
             * /**
             *              *  获取所有的品牌，选中未选中的都有baseTrademarkList
             *              *  只要未选中
             *              *  filter
             *              *  baseTradeMarkIdList:选中的id集合
             *              *  判断选中的id集合不包含的contains
             *              *
             *              *  结果： 更当前category3Id未进行关联的品牌集合
             *              *   queryWrapper.notIn()
             *              */
            //方式一：
//            List<BaseTrademark> baseTrademarkList = this.baseTradeMarkMapper.selectList(null);
//
//            List<BaseTrademark> trademarkList = baseTrademarkList.stream().filter(baseTradeMark -> {
//                //根据已经关联的品牌id进行过滤
//                return !baseTradeMarkIdList.contains(baseTradeMark.getId());
//            }).collect(Collectors.toList());
            //通过条件查询

            //方式二：
            QueryWrapper<BaseTrademark> queryWrapper1=new QueryWrapper<>();
            queryWrapper1.notIn("id",baseCategoryTrademarks);
            List<BaseTrademark> trademarkList = this.baseTradeMarkMapper.selectList(queryWrapper1);


            return trademarkList;

        }
        return null;
    }
    /**
     * 保存分类品牌关联
     * /admin/product/baseCategoryTrademark/save
     */
    @Override
    public void saveBaseCategoryTradeMark(CategoryTrademarkVo categoryTrademarkVo) {
        //获取所有关联的品牌对象
        List<Long> trademarkIdList = categoryTrademarkVo.getTrademarkIdList();
        //处理创建对象进行添加
        if (!CollectionUtils.isEmpty(trademarkIdList)){
List<BaseCategoryTrademark> baseCategoryTrademarkList = trademarkIdList.stream().map(trademarkId->{
    //创建分类品牌对象
    BaseCategoryTrademark baseCategoryTrademark = new BaseCategoryTrademark();
    baseCategoryTrademark.setCategory3Id(categoryTrademarkVo.getCategory3Id());
    baseCategoryTrademark.setTrademarkId(trademarkId);
    return baseCategoryTrademark;
}).collect(Collectors.toList());
        // 批量请添加
        this.saveBatch(baseCategoryTrademarkList);
    }
    }


}
