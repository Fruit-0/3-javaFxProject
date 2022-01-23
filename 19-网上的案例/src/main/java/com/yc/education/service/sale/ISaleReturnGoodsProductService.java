package com.yc.education.service.sale;


import com.yc.education.model.sale.SaleReturnGoodsProduct;
import com.yc.education.service.IService;

import java.util.List;


/**
 * @author BlueSky
 * @Description:
 * @Date 15:23 2018-09-26
 */
public interface ISaleReturnGoodsProductService extends IService<SaleReturnGoodsProduct> {

    /**
     * 根据销售退货单id查询销退产品
     * @param orderid
     * @return
     */
    List<SaleReturnGoodsProduct> listReturnGoodsProduct(String orderid);

    /**
     * 分页
     * 根据销售退货单id查询销退产品
     * @param orderid
     * @return
     */
    List<SaleReturnGoodsProduct> listReturnGoodsProduct(String orderid, int page, int rows);

}
