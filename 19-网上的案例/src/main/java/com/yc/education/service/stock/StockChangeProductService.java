package com.yc.education.service.stock;

import com.yc.education.model.stock.StockChangeProduct;
import com.yc.education.service.IService;

import java.util.List;

/**
 * @ClassName StockChangeProductService
 * @Description TODO 库存异动产品
 * @Author QuZhangJing
 * @Date 2018/11/5 20:37
 * @Version 1.0
 */
public interface StockChangeProductService extends IService<StockChangeProduct> {

    List<StockChangeProduct> findStockChangeProduct(long stockChangeId);

}
