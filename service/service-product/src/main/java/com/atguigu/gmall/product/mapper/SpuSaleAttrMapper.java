package com.atguigu.gmall.product.mapper;

import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface SpuSaleAttrMapper extends BaseMapper<SpuSaleAttr> {
    /**根据spuId 查询销售属性
     * /admin/product/spuSaleAttrList/{spuId}
     */
    List<SpuSaleAttr> selectSpuSaleAttrList(Long spuId);
}
