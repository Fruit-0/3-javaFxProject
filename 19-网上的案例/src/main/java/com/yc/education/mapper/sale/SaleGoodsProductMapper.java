package com.yc.education.mapper.sale;

import com.yc.education.model.sale.SaleGoodsProduct;
import com.yc.education.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SaleGoodsProductMapper extends MyMapper<SaleGoodsProduct> {

    /**
     * 根据销售退货单id查询销退产品
     * @param orderid
     * @return
     */
    List<SaleGoodsProduct> listSaleGoodsProduct(@Param("orderid") String orderid);

    /**
     *  销售产品折扣查询
     * @param customerNo 客户编号 区域
     * @param customerNoEnd 客户编号结束
     * @param productNo 产品编号 区域
     * @param productNoEnd 产品编号结束
     * @param productName 产品名称区域
     * @param productNameEnd 产品名称结束
     * @param saleDateStr 销售日期 区域
     * @param saleDateEndStr 销售日期结束
     * @param customerName 客户名称 区域
     * @param customerNameEnd 客户名称 结束
     * @param discountStr 折扣
     * @return
     */
    List<SaleGoodsProduct> listSaleGoodsProductDiscount(@Param("customerNo") String customerNo,@Param("customerNoEnd") String customerNoEnd,@Param("productNo") String productNo,@Param("productNoEnd") String productNoEnd,@Param("productName") String productName,@Param("productNameEnd") String productNameEnd,@Param("saleDateStr") String saleDateStr,@Param("saleDateEndStr") String saleDateEndStr,@Param("customerName") String customerName,@Param("customerNameEnd") String customerNameEnd,@Param("discountStr") String discountStr);

}