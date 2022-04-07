package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.product.BaseTrademark;
import com.atguigu.gmall.product.mapper.BaseTradeMarkMapper;
import com.atguigu.gmall.product.service.BaseTrademarkService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.config.QuerydslWebConfiguration;
import org.springframework.stereotype.Service;

@Service
public class BaseTrademarkServiceimpl extends ServiceImpl<BaseTradeMarkMapper, BaseTrademark> implements BaseTrademarkService {
    /**分页查询品牌列表
     * /admin/product/baseTrademark/{page}/{limit}
     */

    @Override
    public IPage<BaseTrademark> getBaseTradeMarkListByPage(Page<BaseTrademark> baseTrademarkPage) {
        QueryWrapper<BaseTrademark> queryWrapper = new QueryWrapper<>();
queryWrapper.orderByAsc("id");
        return this.baseMapper.selectPage(baseTrademarkPage,queryWrapper
        );
    }
}

