package com.yc.education.mapper.sale;


import com.yc.education.model.sale.SaleOfferProduct;
import com.yc.education.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SaleOfferProductMapper extends MyMapper<SaleOfferProduct> {


    /**
     * 根据报价单id查询产品报价商品
     * @param quotationid 报价单id
     * @return
     */
    List<SaleOfferProduct> listSaleOfferProduct(@Param("quotationid") Long quotationid);

    /**
     * 根据报价单ID删除
     * @param quotationid 报价单id
     * @return
     */
    int deleteSaleOfferProduct(@Param("quotationid") Long quotationid);
}