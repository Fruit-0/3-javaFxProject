package com.yc.education.mapper.sale;

import com.yc.education.model.sale.SaleQuotation;
import com.yc.education.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SaleQuotationMapper extends MyMapper<SaleQuotation> {

    /**
     * 查询所有报价单
     * @return
     */
    List<SaleQuotation> listSaleQuotationAll();

    /**
     * 根据报价单号查询报价单
     * @param offerno
     * @return
     */
    SaleQuotation getSaleQuotation(@Param("offerno")String offerno);

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
    SaleQuotation getSaleQuotationByPage(@Param("page")int page, @Param("rows")int rows);

    /**
     * 统计报价单数量
     * @return
     */
    int getSaleQuotationCount();
}