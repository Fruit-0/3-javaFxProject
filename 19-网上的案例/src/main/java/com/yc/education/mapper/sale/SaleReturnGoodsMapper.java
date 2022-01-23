package com.yc.education.mapper.sale;

import com.yc.education.model.sale.SaleReturnGoods;
import com.yc.education.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SaleReturnGoodsMapper  extends MyMapper<SaleReturnGoods> {

    /**
     * 查询全部订单
     * @return
     */
    List<SaleReturnGoods> listSaleReturnGoodsAll();

    /**
     * 根据订货单号查询
     * @param orderno
     * @return
     */
    SaleReturnGoods getSaleReturnGoods(@Param("orderno") String orderno);

    /**
     * 最后一条数据
     * @return
     */
    SaleReturnGoods getLastSaleReturnGoods();

    /**
     * 第一条数据
     * @return
     */
    SaleReturnGoods getFirstSaleReturnGoods();

    /**
     * 获取上下页数据
     * @return
     */
    SaleReturnGoods getSaleReturnGoodsByPage(@Param("page")int page, @Param("rows")int rows);

    /**
     * 统计订单数量
     * @return
     */
    int getSaleReturnGoodsCount();
}