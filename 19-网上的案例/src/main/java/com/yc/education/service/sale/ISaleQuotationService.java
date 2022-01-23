package com.yc.education.service.sale;

import com.yc.education.model.sale.SaleQuotation;
import com.yc.education.service.IService;

import java.util.List;

/**
 * @author BlueSky
 * @Description:
 * @Date 15:23 2018-09-26
 */
public interface ISaleQuotationService extends IService<SaleQuotation> {

    /**
     * 查询所有报价单
     * @return
     */
    List<SaleQuotation> listSaleQuotationAll(int page,int rows);

    /**
     * 根据报价单号查询报价单
     * @param offerno
     * @return
     */
    SaleQuotation getSaleQuotation(String offerno);

    /**
     * 最后一条数据
     * @return
     */
    SaleQuotation getLastSaleQuotation();

    /**
     * 第一条数据
     * @return
     */
    SaleQuotation getFirstSaleQuotation();

    /**
     * 获取上下页数据
     * @return
     */
    SaleQuotation getSaleQuotationByPage(int page, int rows);

    /**
     * 统计报价单数量
     * @return
     */
    int getSaleQuotationCount();
}
