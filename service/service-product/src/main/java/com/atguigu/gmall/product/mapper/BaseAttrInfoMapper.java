package com.atguigu.gmall.product.mapper;

import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface BaseAttrInfoMapper extends BaseMapper<BaseAttrInfo>{
    /** 根据属性ID获取集合属性
     * /admin/product/attrInfoList/{category1Id}/{category2Id}/{category3Id}
     * 如果只有一个直接获取即可，如果多个用@Param
     */
    List<BaseAttrInfo> selectAttrinfoList(@Param("category1Id") Long category1Id,@Param("category2Id") Long category2Id, @Param("category3Id")Long category3Id);
}
