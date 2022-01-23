package com.yc.education.service;

import com.yc.education.model.ProductStock;

import java.util.List;

/**
 * @ClassName ProductStockService
 * @Description TODO 产品库存
 * @Author QuZhangJing
 * @Date 2018/10/22 17:58
 * @Version 1.0
 */
public interface ProductStockService extends IService<ProductStock> {

    //产评库存查询
    List<ProductStock> findProductStock(String sisnum, String eisnum,
                                        String sproname,String eproname,
                                        String stype,String etype);

    //根据产品名称查询产品库存
    ProductStock findProductStockByProIsnum(String isnum);

}
