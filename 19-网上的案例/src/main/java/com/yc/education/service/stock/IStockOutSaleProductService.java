package com.yc.education.service.stock;


import com.yc.education.model.stock.StockOutSaleProduct;
import com.yc.education.service.IService;

import java.util.List;


/**
 * @author BlueSky
 * @Description:
 * @Date 15:23 2018-09-26
 */
public interface IStockOutSaleProductService extends IService<StockOutSaleProduct> {

    /**
     * 根据销货出库单id查询出库产品
     * @param orderid
     * @return
     */
    List<StockOutSaleProduct> listStockOutSaleProduct( String orderid);

}
