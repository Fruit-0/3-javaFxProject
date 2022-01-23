package com.yc.education.service.sale;


import com.yc.education.model.sale.SalePurchaseOrderProduct;
import com.yc.education.service.IService;

import java.util.List;


/**
 * @author BlueSky
 * @Description:
 * @Date 15:23 2018-09-26
 */
public interface ISalePurchaseOrderProductService extends IService<SalePurchaseOrderProduct> {

    /**
     * 根据订货单id查询订货产品
     * @param orderid
     * @return
     */
    List<SalePurchaseOrderProduct> listPurchaseOrderProduct(String orderid );

    /**
     * 分页
     * 根据订货单id查询订货产品
     * @param orderid
     * @return
     */
    List<SalePurchaseOrderProduct> listPurchaseOrderProduct(String orderid ,int page,int rows);

}
