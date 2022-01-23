package com.yc.education.service.sale;


import com.yc.education.model.sale.ReportRemark;
import com.yc.education.model.sale.SaleOfferProduct;
import com.yc.education.service.IService;

import java.util.List;

/**
 *  报价产品
 * @author BlueSky
 * @Description:
 * @Date 15:16 2018-08-24
 */
public interface ISaleOfferProductService extends IService<SaleOfferProduct>{



    /**
     * 根据报价单id查询产品报价商品
     * @param quotationid 报价单id
     * @return
     */
    List<SaleOfferProduct> listSaleOfferProduct( Long quotationid);

    /**
     * 根据报价单ID删除
     * @param quotationid 报价单id
     * @return
     */
    int deleteSaleOfferProduct( Long quotationid);
}
