package com.yc.education.service.sale;

import com.yc.education.model.sale.SaleOnlineOrder;
import com.yc.education.model.sale.SaleOnlineOrderProduct;
import com.yc.education.service.IService;

import java.util.List;


/**
 * @author BlueSky
 * @Description:
 * @Date 15:23 2018-09-26
 */
public interface ISaleOnlineOrderProductService extends IService<SaleOnlineOrderProduct> {


    /**
     * 根据网上订单查询ta订购的产品
     * @param orderno
     * @return
     */
    List<SaleOnlineOrderProduct> listByOnlineOrderAll( String orderno);

    /**
     * 分页
     * 根据网上订单查询ta订购的产品
     * @param orderno
     * @return
     */
    List<SaleOnlineOrderProduct> listByOnlineOrderByPage( String orderno ,int page ,int rows);
}
