package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.product.BaseTrademark;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface BaseTrademarkService extends IService<BaseTrademark> {
    /**分页查询品牌列表
     * /admin/product/baseTrademark/{page}/{limit}
     */
    IPage<BaseTrademark> getBaseTradeMarkListByPage(Page<BaseTrademark> baseTrademarkPage);
}
