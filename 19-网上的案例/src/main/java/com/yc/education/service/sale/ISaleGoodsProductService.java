package com.yc.education.service.sale;


import com.yc.education.model.sale.SaleGoodsProduct;
import com.yc.education.model.sale.SaleGoodsProduct;
import com.yc.education.service.IService;

import java.util.List;


/**
 * @author BlueSky
 * @Description:
 * @Date 15:23 2018-09-26
 */
public interface ISaleGoodsProductService extends IService<SaleGoodsProduct> {
    /**
     * 根据销货单id查询销货产品
     * @param orderid
     * @return
     */
    List<SaleGoodsProduct> listSaleGoodsProduct(String orderid);

    /**
     * 分页
     * 根据销货单id查询销货产品
     * @param orderid
     * @return
     */
    List<SaleGoodsProduct> listSaleGoodsProduct(String orderid, int page, int rows);

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
    List<SaleGoodsProduct> listSaleGoodsProductDiscount( String customerNo, String customerNoEnd, String productNo, String productNoEnd, String productName, String productNameEnd, String saleDateStr, String saleDateEndStr, String customerName, String customerNameEnd, String discountStr);


}
