package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.product.*;
import com.atguigu.gmall.product.mapper.*;
import com.atguigu.gmall.product.service.BaseManageService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@SuppressWarnings("all") //抑制警告
public class BaseManageServiceImpl implements BaseManageService {
    @Autowired

    private BaseCategory1Mapper baseCategory1Mapper;
    @Autowired
    private BaseCategory2Mapper baseCategory2Mapper;
    @Autowired
    private BaseCategory3Mapper baseCategory3Mapper;
    @Autowired
    private BaseAttrInfoMapper baseAttrInfoMapper;
    @Autowired
    private BaseAttrValueMapper baseAttrValueMapper;
    @Autowired
    private SpuInfoMapper spuInfoMapper;
    @Autowired
    private BaseSaleAttrMapper baseSaleAttrMapper;
    @Autowired
    private SpuSaleAttrMapper spuSaleAttrMapper;
    @Autowired
    private SpuImageMapper spuImageMapper;
    @Autowired
    private SpuSaleAttrValueMapper spuSaleAttrValueMapper;
    @Autowired
    private SpuPosterMapper spuPosterMapper;
    @Autowired
    private SkuInfoMapper skuInfoMapper;
    @Autowired
    private SkuImageMapper skuImageMapper;
    @Autowired
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;
    @Autowired
    private SkuAttrValueMapper skuAttrValueMapper;

    @Override
    public List<BaseCategory1> getCategory1() {

        List<BaseCategory1> baseCategory1List = baseCategory1Mapper.selectList(null);
        return baseCategory1List;
    }

    @Override
    public List<BaseCategory2> getCategory2(Long category1Id) {
        // select *from base_category2 where category2_is
        QueryWrapper<BaseCategory2> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category1_id", category1Id);

        return this.baseCategory2Mapper.selectList(queryWrapper);
    }

    @Override
    public List<BaseCategory3> getCategory3(Long category2Id) {
        QueryWrapper<BaseCategory3> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category2_id", category2Id);

        return this.baseCategory3Mapper.selectList(queryWrapper);
    }

    /**
     * 根据属性ID获取集合属性
     * /admin/product/attrInfoList/{category1Id}/{category2Id}/{category3Id}
     */
    @Override
    public List<BaseAttrInfo> attrInfoLis(Long category1Id, Long category2Id, Long category3Id) {
        return this.baseAttrInfoMapper.selectAttrinfoList(category1Id, category2Id, category3Id);
    }

    /**
     * 根据属性值查询属性对象
     *
     * @param attrId
     * @return
     */
    @Override
    public BaseAttrInfo getAttrInfo(Long attrId) {
        BaseAttrInfo baseAttrInfo = this.baseAttrInfoMapper.selectById(attrId);
        //判断
        if (baseAttrInfo != null) {
            //查询属性列表
            List<BaseAttrValue> baseAttrValueList = this.getAttrValueList(attrId);
            baseAttrInfo.setAttrValueList(baseAttrValueList);
        }


        return baseAttrInfo;
    }

    /**
     * 根据属性ID查询属性值列表
     * * @param attrId
     */
    @Override
    public List<BaseAttrValue> getAttrValueList(Long attrId) {
        QueryWrapper<BaseAttrValue> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("attr_id", attrId);
        queryWrapper.orderByAsc("attr_id");


        List<BaseAttrValue> baseAttrValueList = this.baseAttrValueMapper.selectList(queryWrapper);
        return baseAttrValueList;
    }

    /**
     * 根据属性ID查询属性值列表
     * * @param attrId
     */
    @Override
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {
        //判断是新增还是修改
        if (baseAttrInfo != null && baseAttrInfo.getId() != null) {
            //修改
            this.baseAttrInfoMapper.updateById(baseAttrInfo);
        } else {
            //新增
            this.baseAttrInfoMapper.insert(baseAttrInfo);
        }
        //删除对应平台属性值
        QueryWrapper<BaseAttrValue> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("attr_id", baseAttrInfo.getId());
        this.baseAttrValueMapper.delete(queryWrapper);
        //重新添加属性值
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
        //判断是否有属性值
        if (!CollectionUtils.isEmpty(attrValueList)) {
            for (BaseAttrValue baseAttrValue : attrValueList) {
                //添加属性关系
                baseAttrValue.setAttrId(baseAttrInfo.getId());
                //新增属性值
                this.baseAttrValueMapper.insert(baseAttrValue);

            }
        }


    }

    @Override
    public IPage<SpuInfo> getSpuListByPage(Page<SpuInfo> spuInfoPage, SkuInfo skuInfo) {
        //封装条件对象 SpuInfoMapper
        QueryWrapper<SpuInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category3_id", skuInfo.getCategory3Id());
        //根据ID排序
        queryWrapper.orderByAsc("id");

        return this.spuInfoMapper.selectPage(spuInfoPage, queryWrapper);

    }

    /**
     * 获取销售属性数据
     * /admin/product/baseSaleAttrList
     */
    @Override
    public List<BaseSaleAttr> baseSaleAttrList() {
        return this.baseSaleAttrMapper.selectList(null);
    }

    /**
     * 保存spu
     * /admin/product/saveSpuInfo
     *
     * @Transactional(rollbackFor = Exception.class)
     * *
     * *@Transactional:出现RuntimeException 运行时异常才会回滚
     * * rollbackFor = Exception.class：
     * *     回滚范围扩大到Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSpuInfo(SpuInfo spuInfo) {
        //添加spuInfo
        this.spuInfoMapper.insert(spuInfo);
        //添加spuSaleAttr
        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
        //添加
        if (!CollectionUtils.isEmpty(spuSaleAttrList)) {
            spuSaleAttrList.forEach(spuSaleAttr -> {
                //设置spuId
                spuSaleAttr.setSpuId(spuInfo.getId());
                //添加
                this.spuSaleAttrMapper.insert(spuSaleAttr);
                //获取销售属性值
                List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();
                //添加spuSaleAttrVlaue
                if (!CollectionUtils.isEmpty(spuSaleAttrValueList)) {
                    spuSaleAttrValueList.forEach(spuSaleAttrValue -> {
                        //设置spu_id
                        spuSaleAttrValue.setSpuId(spuInfo.getId());
                        //设置销售属性名称
                        spuSaleAttrValue.setSaleAttrName(spuSaleAttr.getSaleAttrName());

                        //添加
                        this.spuSaleAttrValueMapper.insert(spuSaleAttrValue);

                    });
                }
            });
        }
        //添加spu_image
        List<SpuImage> spuImageList = spuInfo.getSpuImageList();
        //判断是否为空
        if (!CollectionUtils.isEmpty(spuImageList)) {
            spuImageList.forEach(spuImage -> {
                //设置spuId
                spuImage.setSpuId(spuInfo.getId());
                //添加
                this.spuImageMapper.insert(spuImage);

            });

        }

        //添加spu_poster
        List<SpuPoster> spuPosterList = spuInfo.getSpuPosterList();
        if (!CollectionUtils.isEmpty(spuPosterList)) {
            spuPosterList.forEach(spuPoster -> {
                //设置spuId
                spuPoster.setSpuId(spuInfo.getId());
                //添加
                this.spuPosterMapper.insert(spuPoster);

            });

        }


    }

    /**
     * 根据spuId 查询销售属性
     * /admin/product/spuSaleAttrList/{spuId}
     */
    @Override
    public List<SpuSaleAttr> spuSaleAttrList(Long spuId) {
        return this.spuSaleAttrMapper.selectSpuSaleAttrList(spuId);
    }

    /**
     * /admin/product/spuImageList/{spuId}
     * 根据spuId 获取spuImage 集合
     */
    @Override
    public List<SpuImage> spuImageList(Long spuId) {
        QueryWrapper<SpuImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("spu_id", spuId);
        return this.spuImageMapper.selectList(queryWrapper);
    }
    /**
     * 保存SkuInfo
     * /admin/product/saveSkuInfo
     */
    @Override
    public void saveSkuInfo(SkuInfo skuInfo) {
//skuInfo信息
        this.skuInfoMapper.insert(skuInfo);
        // skuimage
        List<SkuImage> skuImageList = skuInfo.getSkuImageList();
        if (!CollectionUtils.isEmpty(skuImageList)){
            skuImageList.forEach(skuImage -> {
//设置skuid
                skuImage.setSkuId(skuInfo.getId());
                //执行保存图片
            this.skuImageMapper.insert(skuImage);

            });
        }

        // skusaleattrvallue
        List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
        if (!CollectionUtils.isEmpty(skuSaleAttrValueList)){
            skuSaleAttrValueList.forEach(skuSaleAttrValue -> {
                //设置skuId
                skuSaleAttrValue.setSkuId(skuInfo.getId());
                //设置spuId
                skuSaleAttrValue.setSkuId(skuInfo.getSpuId());
                //保存销售属性
                this.skuSaleAttrValueMapper.insert(skuSaleAttrValue);
            });
        }
        // skuattrvalue
        List<SkuAttrValue> skuAttrValueList = skuInfo.getSkuAttrValueList();
        if (!CollectionUtils.isEmpty(skuAttrValueList)){
            skuAttrValueList.forEach(skuAttrValue -> {
                //设置skuid
                skuAttrValue.setSkuId(skuInfo.getSpuId());
                this.skuAttrValueMapper.insert(skuAttrValue);
            });
        }
    }
    /**
     * sku分页列表
     * /admin/product/list/{page}/{limit}
     */
    @Override
    public IPage<SkuInfo> getSkuInfoListByPage(Page<SkuInfo> skuInfoPage) {
        QueryWrapper<SkuInfo> queryWrapper = new QueryWrapper<>();
        // 倒叙
        queryWrapper.orderByDesc("id");
        return this.skuInfoMapper.selectPage(skuInfoPage, queryWrapper);
    }

    @Override
    public void xiajia(Long skuId) {
        // 封装
        SkuInfo skuInfo = this.skuInfoMapper.selectById(skuId);
        if (skuInfo!=null){
            skuInfo.setIsSale(0);
        }
        this.skuInfoMapper.updateById(skuInfo);
    }

    @Override
    public void shangJia(Long skuId) {
        // 封装
        SkuInfo skuInfo = this.skuInfoMapper.selectById(skuId);
        if (skuInfo!=null){
            skuInfo.setIsSale(1);
        }
        this.skuInfoMapper.updateById(skuInfo);
    }


}
